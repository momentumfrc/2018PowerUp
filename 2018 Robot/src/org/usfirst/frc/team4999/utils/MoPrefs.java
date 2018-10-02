package org.usfirst.frc.team4999.utils;

import edu.wpi.first.wpilibj.Preferences;

public class MoPrefs {
	
	private static Preferences prefs = Preferences.getInstance();
	
	private static final double AUTO_SPEED = 0.5; 				// Maximum speed the robot will travel at in autonomous
	private static final double DRIVE_ENC_TICKS = 562.666378; 	// Number of encoder ticks per meter of travel
	private static final double LIFT_ENC_TICKS = 500; 			// Number of lift encoder ticks per meter of travel
	private static final double FALLBACK_AUTO_TIME = 3; 		// Time, in seconds, the robot will drive for when in fallback auto mode
	private static final double FALLBACK_AUTO_DISTANCE = 3.5; 	// Distance the robot will drive for, in meters, in fallback auto mode
	private static final double SWITCH_ELBOW_ANGLE = 20; 		// Angle the elbow is set to when placing a cube on the switch in autonomous
	private static final double MAX_LIFT_HEIGHT = 3; 			// Maximum height of the lift in meters
	private static final int 	MAX_ELBOW_ROTATION = 700;		// Maximum encoder ticks the elbow can displace
	private static final double ELBOW_ENC_TICKS = 0.0194;		// Encoder ticks per degree of elbow rotation
	
	
	private static double getDouble(String key, double def) {
		if(!prefs.containsKey(key)) {
			prefs.putDouble(key, def);
		}
		return prefs.getDouble(key,  def);
	}
	
	private static int getInt(String key, int def) {
		if(!prefs.containsKey(key)) {
			prefs.putInt(key, def);
		}
		return prefs.getInt(key, def);
	}
	
	/**
	 * Gets the maximum speed the robot will travel at in autonomous
	 */
	public static double getAutoSpeed() {
		return getDouble("AUTO_SPEED", AUTO_SPEED);
	}
	/**
	 * Gets the number of encoder ticks per meter of travel
	 */
	public static double getDriveEncTicks() {
		return getDouble("DRIVE_ENC_TICKS", DRIVE_ENC_TICKS);
	}
	/**
	 * Gets the number of lift encoder ticks per meter of travel
	 */
	public static double getLiftEncTicks() {
		return getDouble("LIFT_ENC_TICKS",LIFT_ENC_TICKS);
	}
	/**
	 * Gets the time, in seconds, the robot will drive for when in fallback auto mode
	 */
	public static double getFallbackAutoTime() {
		return getDouble("FALLBACK_AUTO_TIME",FALLBACK_AUTO_TIME);
	}
	/**
	 * Gets the distance the robot will drive for, in meters, in fallback auto mode
	 */
	public static double getFallbackAutoDistance() {
		return getDouble("FALLBACK_AUTO_DISTANCE", FALLBACK_AUTO_DISTANCE);
	}
	/**
	 * Gets the angle the elbow is set to when placing a cube on the switch in autonomous
	 */
	public static double getSwitchElbowAngle() {
		return getDouble("SWITCH_ELBOW_ANGLE", SWITCH_ELBOW_ANGLE);
	}
	/**
	 * Gets the maximum height of the lift in meters
	 */
	public static double getMaxLiftHeight() {
		return getDouble("MAX_LIFT_HEIGHT", MAX_LIFT_HEIGHT);
	}
	/**
	 * Gets the maximum encoder ticks the elbow can displace
	 */
	public static int getMaxElbowRotation() {
		return getInt("MAX_ELBOW_ROTATION", MAX_ELBOW_ROTATION);
	}
	/**
	 * Gets the encoder ticks per degree of elbow rotation
	 */
	public static double getElbowEncTicks() {
		return getDouble("ELBOW_ENC_TICKS", ELBOW_ENC_TICKS);
	}
	

}
