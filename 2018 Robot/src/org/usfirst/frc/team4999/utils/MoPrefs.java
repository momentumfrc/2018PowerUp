package org.usfirst.frc.team4999.utils;

import edu.wpi.first.wpilibj.Preferences;

public class MoPrefs {
	
	private static Preferences prefs = Preferences.getInstance();
	
	private static final int 	MAX_PI_DELAY = 200;
	private static final double AUTO_SPEED = 0.25;
	private static final double WHEEL_DIST = 0.61;
	private static final double ENC_TICKS = 562.666378; // Number of encoder ticks per meter of travel
	private static final double MAX_MOVE_SPEED = 2; // Maximum velocity of the robot in meters/second
	private static final double MAX_TURN_SPEED = 4; // Maximum angular velocity of the robot in degrees/second
	private static final double FALLBACK_AUTO_TIME = 3; // Time, in seconds, the robot will drive for when in fallback auto mode
	private static final double FALLBACK_AUTO_DISTANCE = 3.5; // Distance the robot will drive for, in meters, in fallback auto mode
	
	
	
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
	public static int getMaxPiDelay() {
		checkInt("MAX_PI_DELAY", MAX_PI_DELAY);
		return prefs.getInt("MAX_PI_DELAY", MAX_PI_DELAY);
	}
	public static double getAutoSpeed() {
		checkDouble("AUTO_SPEED", AUTO_SPEED);
		return prefs.getDouble("AUTO_SPEED", AUTO_SPEED);
	}
	public static double getWheelDist() {
		checkDouble("WHEEL_DIST", WHEEL_DIST);
		return prefs.getDouble("WHEEL_DIST", WHEEL_DIST);
	}
	public static double getEncTicks() {
		checkDouble("ENC_TICKS", ENC_TICKS);
		return prefs.getDouble("ENC_TICKS", ENC_TICKS);
	}
	public static double getMaxMoveSpeed() {
		checkDouble("MAX_MOVE_SPEED", MAX_MOVE_SPEED);
		return prefs.getDouble("MAX_MOVE_SPEED", MAX_MOVE_SPEED);
	}
	public static double getMaxTurnSpeed() {
		checkDouble("MAX_TURN_SPEED", MAX_TURN_SPEED);
		return prefs.getDouble("MAX_TURN_SPEED", MAX_TURN_SPEED);
	}
	public static double getFallbackAutoTime() {
		checkDouble("FALLBACK_AUTO_TIME",FALLBACK_AUTO_TIME);
		return prefs.getDouble("FALLBACK_AUTO_TIME", FALLBACK_AUTO_TIME);
	}
	public static double getFallbackAutoDistance() {
		checkDouble("FALLBACK_AUTO_DISTANCE", FALLBACK_AUTO_DISTANCE);
		return prefs.getDouble("FALLBACK_AUTO_DISTANCE", FALLBACK_AUTO_DISTANCE);
	}

}
