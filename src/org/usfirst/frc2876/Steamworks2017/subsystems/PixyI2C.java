package org.usfirst.frc2876.Steamworks2017.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Read data from a pixy over i2c.
 * 
 * Some basic terms:
 * 
 * frame: a picture taken by pixy
 * 
 * signature - a unique color pixy can recognize. Pixy can recognize up to 7
 * signatures.
 * 
 * block - object pixy recognizes.
 * 
 * Pixy takes a picture which is called a frame. Pixy searches the frame for
 * objects that match signatures it is configured to look for. If a frame
 * contains multiple objects that match a signature pixy will find them all. So
 * for a single frame you can get many blocks from pixy.
 *
 * readBlocks method will look for all blocks pixy is configured to find in a
 * single frame.
 */
public class PixyI2C {
	// Name of pixy camera. Helps when print info out to id which one is being
	// referred to.
	String name;

	// The i2c connection to camera.
	I2C pixy;

	// These are used in original pixy i2c reading code. It is not used anymore
	// so could remove that code.
	PixyPacket[] packets;
	PixyException pExc;

	public PixyI2C(String id, I2C argPixy, PixyPacket[] argPixyPacket, PixyException argPixyException,
			PixyPacket argValues) {
		pixy = argPixy;
		packets = argPixyPacket;
		pExc = argPixyException;
		name = "Pixy_" + id;
	}

	// This method parses raw data from the pixy into readable integers
	public int bytesToInt(byte upper, byte lower) {
		return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	}

	// This method reads a chunk of data from pixy and tries to parse the data
	// into blocks. It will only find one block per signature. So it's
	// usefulness is limited. This was our original code to read pixy that we
	// found on github. It is not used anymore so we could remove this.
	public PixyPacket readPacket(int Signature) throws PixyException {
		int Checksum;
		int Sig;
		byte[] rawData = new byte[32];
		// SmartDashboard.putString("rawData", rawData[0] + " " + rawData[1] + "
		// " + rawData[15] + " " + rawData[31]);
		try {
			pixy.readOnly(rawData, 32);
		} catch (RuntimeException e) {
			SmartDashboard.putString(name + "Status", e.toString());
			System.out.println(name + "  " + e);
		}
		if (rawData.length < 32) {
			SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
			System.out.println("byte array length is broken length=" + rawData.length);
			return null;
		}
		for (int i = 0; i <= 16; i++) {
			int syncWord = bytesToInt(rawData[i + 1], rawData[i + 0]); // Parse first 2
																// bytes
			if (syncWord == 0xaa55) { // Check is first 2 bytes equal a "sync
										// word", which indicates the start of a
										// packet of valid data
				syncWord = bytesToInt(rawData[i + 3], rawData[i + 2]); // Parse the
																// next 2 bytes
				if (syncWord != 0xaa55) { // Shifts everything in the case that
											// one syncword is sent
					i -= 2;
				}
				// This next block parses the rest of the data
				Checksum = bytesToInt(rawData[i + 5], rawData[i + 4]);
				Sig = bytesToInt(rawData[i + 7], rawData[i + 6]);
				if (Sig <= 0 || Sig > packets.length) {
					break;
				}

				packets[Sig - 1] = new PixyPacket();
				packets[Sig - 1].X = bytesToInt(rawData[i + 9], rawData[i + 8]);
				packets[Sig - 1].Y = bytesToInt(rawData[i + 11], rawData[i + 10]);
				packets[Sig - 1].Width = bytesToInt(rawData[i + 13], rawData[i + 12]);
				packets[Sig - 1].Height = bytesToInt(rawData[i + 15], rawData[i + 14]);
				// Checks whether the data is valid using the checksum *This if
				// block should never be entered*
				if (Checksum != Sig + packets[Sig - 1].X + packets[Sig - 1].Y + packets[Sig - 1].Width
						+ packets[Sig - 1].Height) {
					packets[Sig - 1] = null;
					throw pExc;
				}
				break;
			} else
				SmartDashboard.putNumber("syncword: ", syncWord);
		}
		// Assigns our packet to a temp packet, then deletes data so that we
		// dont return old data
		PixyPacket pkt = packets[Signature - 1];
		packets[Signature - 1] = null;
		return pkt;
	}

	/**
	 * Read len data from pixy. If any exception happens or less data than
	 * requested is read return null.
	 * 
	 * @param len
	 *            amount of data to read
	 * @return byte array containing data.
	 */
	private byte[] readData(int len) {
		byte[] rawData = new byte[len];
		try {
			pixy.readOnly(rawData, len);
		} catch (RuntimeException e) {
			SmartDashboard.putString(name + "Status", e.toString());
			System.out.println(name + "  " + e);
			return null;
		}
		if (rawData.length < len) {
			SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
			System.out.println("byte array length is broken length=" + rawData.length);
			return null;
		}
		return rawData;
	}

	/**
	 * Read one word from pixy. If an error or no data read return 0.
	 * 
	 * @return integer containing data on success or 0 on error.
	 */
	private int readWord() {
		byte[] data = readData(2);
		if (data == null) {
			return 0;
		}
		return bytesToInt(data[1], data[0]);
	}

	/**
	 * Read and parse a block from pixy. This method assumes 2 bytes for
	 * checksum have already been read. We just read rest of data here and parse
	 * it into a PixyPacket object.
	 * 
	 * See Object block format section in
	 * http://www.cmucam.org/projects/cmucam5/wiki/Porting_Guide#Object-block-format
	 * 
	 * @param checksum
	 *            checksum for a pixy block. Used to check if block is valid
	 * @return A PixyPacket representing a block on success. null on error.
	 */
	private PixyPacket readBlock(int checksum) {
		// See Object block format section in
		// http://www.cmucam.org/projects/cmucam5/wiki/Porting_Guide#Object-block-format
		// Each block is 14 bytes, but we already read 2 bytes for checksum in
		// caller so now we need to read rest.

		byte[] data = readData(12);
		if (data == null) {
			return null;
		}
		PixyPacket block = new PixyPacket();
		block.Signature = bytesToInt(data[1], data[0]);
		if (block.Signature <= 0 || block.Signature > 7) {
			return null;
		}
		block.X = bytesToInt(data[3], data[2]);
		block.Y = bytesToInt(data[5], data[4]);
		block.Width = bytesToInt(data[7], data[6]);
		block.Height = bytesToInt(data[9], data[8]);

		int sum = block.Signature + block.X + block.Y + block.Width + block.Height;
		if (sum != checksum) {
			return null;
		}
		return block;
	}

	// Max number of signatures a pixy can see.
	private final int MAX_SIGNATURES = 7;
	// Number of bytes that represent an object.
	private final int OBJECT_SIZE = 14;
	// Byte sequence that represents start of a block. Two of these(or one of
	// these and one color code word) in a row
	// indicates the start of a new frame.
	private final int START_WORD = 0xaa55;
	// Byte sequence that represents the start of color code block.
	private final int START_WORD_CC = 0xaa56;
	// If we see this sequence of bytes we are reading data off by one.
	private final int START_WORD_X = 0x55aa;

	/**
	 * Look for start words. Two start words in a row indicates beginning of new
	 * frame. Single start word indicates beginning of an object. Limit the
	 * number of bytes to read so we don't loop endlessly looking for frame.
	 * 
	 * @return true if start word is found. false if not.
	 */
	public boolean findFrameStart() {
		int numBytesRead = 0;
		int lastWord = 0xffff;
		// This while condition was originally true.. may not be a good idea if
		// something goes wrong robot will be stuck in this loop forever. So
		// let's read some number of bytes and give up so other stuff on robot
		// can have a chance to run. What number of bytes to choose? Maybe size
		// of a block * max number of signatures that can be detected? Or how
		// about size of block and max number of blocks we are looking for?
		while (numBytesRead < (OBJECT_SIZE * MAX_SIGNATURES)) {
			int word = readWord();
			numBytesRead += 2;
			if (word == 0 && lastWord == 0) {
				return false;
			} else if (word == START_WORD && lastWord == START_WORD) {
				// Beginning of a frame
				return true;
			} else if (word == START_WORD_CC && lastWord == START_WORD) {
				// Beginning of a frame
				return true;
			} else if (word == START_WORD_X) {
				// We found one byte that indicates end of start word and one
				// byte that indicates next start word. That means we are
				// shifted/offset by one byte. So try to read one byte only so
				// we can get back on track.
				byte[] data = readData(1);
				numBytesRead += 1;
			}
			lastWord = word;
		}
		return false;
	}

	private boolean skipStart = false;

	/**
	 * Read all blocks in a frame.
	 * 
	 * @return array of PixyPackets on success. The array may contain null
	 *         entries if there was an error reading a block.
	 */
	public PixyPacket[] readBlocks() {
		// This has to match the max block setting in pixymon?
		// TODO: can we configure pixy via i2c to look for certain number of
		// blocks?
		int maxBlocks = 2;
		PixyPacket[] blocks = new PixyPacket[maxBlocks];

		// Look for start of a frame. If we already found it don't bother
		// looking again.
		if (!skipStart) {
			if (findFrameStart() == false) {
				return null;
			}
		} else {
			skipStart = false;
		}
		// Read data from pixy and parse it looking for blocks.
		for (int i = 0; i < maxBlocks; i++) {
			// TODO: Should we set to empty PixyPacket? To avoid having to check
			// for null in callers?
			blocks[i] = null;
			int checksum = readWord();
			if (checksum == START_WORD) {
				// we've reached the beginning of the next frame
				skipStart = true;
				return blocks;
			} else if (checksum == START_WORD_CC) {
				// we've reached the beginning of the next frame
				skipStart = true;
				return blocks;
			} else if (checksum == 0) {
				return blocks;
			}
			blocks[i] = readBlock(checksum);
		}
		return blocks;
	}
}
