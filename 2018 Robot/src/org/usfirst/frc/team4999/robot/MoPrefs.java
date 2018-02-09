package org.usfirst.frc.team4999.robot;

import edu.wpi.first.wpilibj.Preferences;

public class MoPrefs {
	
	private Preferences prefs = Preferences.getInstance();
	private static MoPrefs instance;
	
	private static final double MOVE_P = 0.2;
	private static final double MOVE_I = 0;
	private static final double MOVE_D = 0.05;
	private static final double TURN_P = 0.2;
	private static final double TURN_I = 0;
	private static final double TURN_D = 0.05;
	private static final int 	MAX_PI_DELAY = 200;
	private static final double AUTO_SPEED = 0.25;
	
	
	
	private void checkDouble(String key, double def) {
		if(!prefs.containsKey(key)) {
			prefs.putDouble(key, def);
		}
	}
	
	private void checkInt(String key, int def) {
		if(!prefs.containsKey(key)) {
			prefs.putInt(key, def);
		}
	}
	
	public static MoPrefs getInstance() {
		if(instance == null) {
			instance = new MoPrefs();
		}
		return instance;
	}
	
	private MoPrefs() {
		checkDouble("MOVE_P", MOVE_P);
		checkDouble("MOVE_I", MOVE_I);
		checkDouble("MOVE_D", MOVE_D);
		checkDouble("TURN_P", TURN_P);
		checkDouble("TURN_I", TURN_I);
		checkDouble("TURN_D", TURN_D);
		checkInt("MAX_PI_DELAY", MAX_PI_DELAY);
		checkDouble("AUTO_SPEED", AUTO_SPEED);
	}
	
	public double getMoveP() {
		return prefs.getDouble("MOVE_P", MOVE_P);
	}
	public double getMoveI() {
		return prefs.getDouble("MOVE_I", MOVE_I);
	}
	public double getMoveD() {
		return prefs.getDouble("MOVE_D", MOVE_D);
	}
	public double getTurnP() {
		return prefs.getDouble("TURN_P", TURN_P);
	}
	public double getTurnI() {
		return prefs.getDouble("TURN_I", TURN_I);
	}
	public double getTurnD() {
		return prefs.getDouble("TURN_D", TURN_D);
	}
	public double getMaxPiDelay() {
		return prefs.getInt("MAX_PI_DELAY", MAX_PI_DELAY);
	}
	public double getAutoSpeed() {
		return prefs.getDouble("AUTO_SPEED", AUTO_SPEED);
	}

}
