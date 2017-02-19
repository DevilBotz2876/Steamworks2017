package org.usfirst.frc2876.Steamworks2017.subsystems;

public class PixyPacket {
	public int X;
	public int Y;
	public int Width;
	public int Height;
	public int checksumError;
	
	public String toString() {
		return "" + 
	" X:" + X + 
	" Y:" + Y +
	" W:" + Width + 
	" H:" + Height;
	}
}
