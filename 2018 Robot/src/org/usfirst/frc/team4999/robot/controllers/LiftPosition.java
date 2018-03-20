package org.usfirst.frc.team4999.robot.controllers;

public class LiftPosition {
	// TODO: All placeholders for now
	public static double GROUND = 0.1;
	public static double STOW = 0.5;
	public static double CARRY = 0.75;
	public static double EXCHANGE = 0.75;
	public static double SWITCH = 1; 
	public static double SCALE_LOW = 1.5;
	public static double SCALE_HIGH = 2;
	
	public static double[] values() {
		return new double[] {GROUND, CARRY, EXCHANGE, SWITCH, SCALE_LOW, SCALE_HIGH};
	}
	
}
