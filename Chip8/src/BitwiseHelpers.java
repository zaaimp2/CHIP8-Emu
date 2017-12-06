
public class BitwiseHelpers {
	
	public static byte getSecondDigit(short address){
		return (byte) ((address & 0x0F00) >> 8);
	}
	
	public static byte getThirdDigit(short address){
		return (byte) ((address & 0x00F0) >> 4);
	}
	
	public static byte getLastDigit(short address){
		return (byte) (address & 0x000F);
	}
	
	public static short getLastThreeDigits(short address){
		return (short)(address & 0x0FFF);
	}
	
	public static byte getLastTwoDigits(short address){
		return (byte)(address & 0x00FF);
	}

}
