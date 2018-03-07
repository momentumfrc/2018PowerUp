package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class F310Wrapper extends DriveController {
	
	private Joystick logitech = RobotMap.f310;
	
	private static final double MOVE_CURVE = 2;
	private static final double TURN_CURVE = 1;
	
	private static final double DEADZONE = 0.05;
	
	private int currentPos = 0;
	private boolean povHeld = false;

	@Override
	public double getMoveRequest() {
		double moveRequest = logitech.getRawAxis(1);
    	moveRequest = deadzone(moveRequest, DEADZONE);
    	moveRequest = curve(moveRequest, MOVE_CURVE);
    	return moveRequest;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = logitech.getRawAxis(4);
    	turnRequest = deadzone(turnRequest, DEADZONE);
    	turnRequest = curve(turnRequest, TURN_CURVE);
    	return turnRequest;
	}

	@Override
	public double getSpeedLimiter() {
		return 1;
	}

	@Override
	public boolean getReverseDirection() {
		return logitech.getRawButtonPressed(1);
	}

	@Override
	public boolean getKillPID() {
		return logitech.getRawButton(3);
	}

	@Override
	public double getLiftPosition() {
		double[] values = LiftPosition.values();
		int pov = logitech.getPOV();
		if(pov == -1) {
			povHeld = false;
		} else if(!povHeld) {
			povHeld = true;
			if(pov == 0 && currentPos < values.length-1) {
				currentPos++;
			} else if(pov == 180 && currentPos > 0) {
				currentPos--;
			}
		}
		return values[currentPos];
	}

	@Override
	public boolean getHunt() {
		return false;
	}

	@Override
	public boolean getShoot() {
		return false;
	}

	@Override
	public double getElbowSpeed() {
		return 0;
	}

}
