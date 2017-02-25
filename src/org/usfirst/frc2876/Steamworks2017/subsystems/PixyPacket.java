package org.usfirst.frc2876.Steamworks2017.subsystems;

// TODO: rename this class to PixyBlock.  
// We originally used some code found on github to read pixy. This class came with it but name is a little misleading.

/**
 * Represents a pixy block. The data format for a block can be found
 * http://www.cmucam.org/projects/cmucam5/wiki/Porting_Guide in the 'Object
 * Block Format' section.
 *
 */
public class PixyPacket {
	public int Signature;
	public int X;
	public int Y;
	public int Width;
	public int Height;

	public String toString() {
		return "" + " S:" + Signature + " X:" + X + " Y:" + Y + " W:" + Width + " H:" + Height;
	}
}
