package org.usfirst.frc.team4999.lights;


public class BrightnessFilter {
	
	private static double brightness = 1;
	
	private static BrightnessFilter instance;
	
	/**
	 * Registers the brightness class to listen to changes in the robot preferences
	 */
	public static void register() {
		if(instance == null)
			instance = new BrightnessFilter();
	}
	
	public static int dimValue(int value) {
		return (int)(value * brightness);
	}
	
	public static Color dimColor(Color in) {
		return new Color(dimValue(in.getRed()), dimValue(in.getGreen()), dimValue(in.getBlue()));
	}

}
