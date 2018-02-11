package org.usfirst.frc.team4999.utils;

import edu.wpi.first.wpilibj.Preferences;

public class MoPrefs {
	
	private static Preferences prefs = Preferences.getInstance();
	
	private static final double MOVE_ERR_ZONE = 10;
	private static final double MOVE_TARGET_ZONE = 10;
	private static final double TURN_ERR_ZONE = 10;
	private static final double TURN_TARGET_ZONE = 10;
	private static final int 	MAX_PI_DELAY = 200;
	private static final double AUTO_SPEED = 0.25;
	private static final double WHEEL_DIST = 0.61;
	private static final double TARGET_TIME = 0.5;
	private static final double ENC_TICKS = 700; // Number of encoder ticks per meter of travel
	
	
	
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
	public static double getMoveErrZone() {
		checkDouble("MOVE_ERR_ZONE", MOVE_ERR_ZONE);
		return prefs.getDouble("MOVE_ERR_ZONE", MOVE_ERR_ZONE);
	}
	public static double getMoveTargetZone() {
		checkDouble("MOVE_TARGET_ZONE", MOVE_TARGET_ZONE);
		return prefs.getDouble("MOVE_TARGET_ZONE", MOVE_TARGET_ZONE);
	}
	public static double getTurnErrZone() {
		checkDouble("TURN_ERR_ZONE", TURN_ERR_ZONE);
		return prefs.getDouble("TURN_ERR_ZONE", TURN_ERR_ZONE);
	}public static double getTurnTargetZone() {
		checkDouble("TURN_TARGET_ZONE", TURN_TARGET_ZONE);
		return prefs.getDouble("TURN_TARGET_ZONE", TURN_TARGET_ZONE);
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
	public static double getTargetTime() {
		checkDouble("TARGET_TIME", TARGET_TIME);
		return prefs.getDouble("TARGET_TIME", TARGET_TIME);	
	}
	public static double getEncTicks() {
		checkDouble("ENC_TICKS", ENC_TICKS);
		return prefs.getDouble("ENC_TICKS", ENC_TICKS);
	}

}
