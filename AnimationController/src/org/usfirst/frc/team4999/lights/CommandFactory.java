package org.usfirst.frc.team4999.lights;

public class CommandFactory {
	
	// All according to specification
	private static final byte DISPLAY_FRAME = 0x01;
	private static final byte SET_SINGLE = 0x02;
	private static final byte SET_RUN = 0x03;
	private static final byte SET_STRIDE = 0x04;
	
	private static byte[] setSizeByte(byte[] data) {
		byte[] out = new byte[data.length + 1];
		out[0] = (byte) (data.length);
		for(int i = 0; i < data.length; i++) {
			out[i+1] = data[i];
		}
		return out;
	}
	
	/**
	 * Makes a packet to set a single LED
	 * @param address LED to set
	 * @param color desired color
	 * @return the packet
	 */
	public static Command makeSingle(int address, Color color) {
		byte[] data = {
				SET_SINGLE,
				(byte) address, 
				(byte) color.getRed(),
				(byte) color.getGreen(),
				(byte) color.getBlue(),
		};
		return new Command(setSizeByte(data));
	}
	
	/**
	 * Makes a packet to set a run of LEDs
	 * @param address LED to set
	 * @param color desired color
	 * @param length number of LEDs to set
	 * @return the packet
	 */
	public static Command makeRun(int address, Color color, int length) {
		byte[] data = {
				SET_RUN,
				(byte) address, 
				(byte) color.getRed(),
				(byte) color.getGreen(),
				(byte) color.getBlue(),
				(byte) length
		};
		return new Command(setSizeByte(data));
	}
	
	/**
	 * Makes a packet to set a run of LEDs and repeat that run every stride LEDs
	 * @param address LED to set
	 * @param color desired color
	 * @param length number of LEDs to set
	 * @param stride how often to repeat the run
	 * @return the packet
	 */
	public static Command makeStride(int address, Color color, int length, int stride) {
		byte[] data = {
				SET_STRIDE,
				(byte) address, 
				(byte) color.getRed(),
				(byte) color.getGreen(),
				(byte) color.getBlue(),
				(byte) length,
				(byte) stride
		};
		return new Command(setSizeByte(data));
	}
	
	/**
	 * Makes a packet to display the current frame
	 * @return the packet
	 */
	public static Command makeShowPacket() {
		byte[] data = {DISPLAY_FRAME};
		return new Command(setSizeByte(data));
	}
	
}
