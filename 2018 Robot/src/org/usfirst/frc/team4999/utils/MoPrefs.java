package org.usfirst.frc.team4999.utils;

import edu.wpi.first.wpilibj.Preferences;

public class MoPrefs {
	
	private static Preferences prefs = Preferences.getInstance();
	
	private static final double AUTO_SPEED = 0.25; 				// Maximum speed the robot will travel at in autonomous
	private static final double DRIVE_ENC_TICKS = 562.666378; 	// Number of encoder ticks per meter of travel
	private static final double LIFT_ENC_TICKS = 500; 			// Number of lift encoder ticks per meter of travel
	private static final double FALLBACK_AUTO_TIME = 3; 		// Time, in seconds, the robot will drive for when in fallback auto mode
	private static final double FALLBACK_AUTO_DISTANCE = 3.5; 	// Distance the robot will drive for, in meters, in fallback auto mode
	private static final double TILT_RANGE = 2; 				// Degrees robot will tilt off-axis before attempting to stabilize
	private static final double MAX_LIFT_HEIGHT = 2; 			// Maximum height of the lift in meters
	private static final int MAX_ELBOW_ROTATION = 100;			// Maximum encoder ticks the elbow can displace
	
	
	private static void checkDouble(String key, double def) {
		if(!prefs.containsKey(key)) {
			prefs.putDouble(key, def);
		}
	}
	
	private static void checkInt(String key, int def) {
		if(!prefs.containsKey(key)) {
			prefs.putInt(key, def);
		}
	}
	public static double getAutoSpeed() {
		checkDouble("AUTO_SPEED", AUTO_SPEED);
		return prefs.getDouble("AUTO_SPEED", AUTO_SPEED);
	}
	public static double getDriveEncTicks() {
		checkDouble("DRIVE_ENC_TICKS", DRIVE_ENC_TICKS);
		return prefs.getDouble("DRIVE_ENC_TICKS", DRIVE_ENC_TICKS);
	}
	public static double getFallbackAutoTime() {
		checkDouble("FALLBACK_AUTO_TIME",FALLBACK_AUTO_TIME);
		return prefs.getDouble("FALLBACK_AUTO_TIME", FALLBACK_AUTO_TIME);
	}
	public static double getFallbackAutoDistance() {
		checkDouble("FALLBACK_AUTO_DISTANCE", FALLBACK_AUTO_DISTANCE);
		return prefs.getDouble("FALLBACK_AUTO_DISTANCE", FALLBACK_AUTO_DISTANCE);
	}
	public static double getTiltRange() {
		checkDouble("TILT_RANGE",TILT_RANGE);
		return prefs.getDouble("TILT_RANGE", TILT_RANGE);
	}
	public static double getLiftEncTicks() {
		checkDouble("LIFT_ENC_TICKS",LIFT_ENC_TICKS);
		return prefs.getDouble("LIFT_ENC_TICKS", LIFT_ENC_TICKS);
	}
	public static double getMaxLiftHeight() {
		checkDouble("MAX_LIFT_HEIGHT", MAX_LIFT_HEIGHT);
		return prefs.getDouble("MAX_LIFT_HEIGHT", MAX_LIFT_HEIGHT);
	}
	public static int getMaxElbowRotation() {
		checkInt("MAX_ELBOW_ROTATION", MAX_ELBOW_ROTATION);
		return prefs.getInt("MAX_ELBOW_ROTATION", MAX_ELBOW_ROTATION);
	}
	

}
