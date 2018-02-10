package org.usfirst.frc.team4999.robot;

import edu.wpi.first.wpilibj.Preferences;

public class MoPrefs {
	
	private static Preferences prefs = Preferences.getInstance();
	
	private static final double MOVE_P = 0.2;
	private static final double MOVE_I = 0;
	private static final double MOVE_D = 0.05;
	private static final double TURN_P = 0.2;
	private static final double TURN_I = 0;
	private static final double TURN_D = 0.05;
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
	
	public static double getMoveP() {
		checkDouble("MOVE_P", MOVE_P);
		return prefs.getDouble("MOVE_P", MOVE_P);
	}
	public static double getMoveI() {
		checkDouble("MOVE_I", MOVE_I);
		return prefs.getDouble("MOVE_I", MOVE_I);
	}
	public static double getMoveD() {
		checkDouble("MOVE_D", MOVE_D);
		return prefs.getDouble("MOVE_D", MOVE_D);
	}
	public static double getTurnP() {
		checkDouble("TURN_P", TURN_P);
		return prefs.getDouble("TURN_P", TURN_P);
	}
	public static double getTurnI() {
		checkDouble("TURN_I", TURN_I);
		return prefs.getDouble("TURN_I", TURN_I);
	}
	public static double getTurnD() {
		checkDouble("TURN_D", TURN_D);
		return prefs.getDouble("TURN_D", TURN_D);
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
