package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class F310Wrapper extends DriveController {
	
	private LogitechF310 logitech = RobotMap.f310;
	
	private static final double MOVE_CURVE = 2;
	private static final double TURN_CURVE = 1;
	
	private static final double DEADZONE = 0.05;
	
	private static final double MAX_CLAW_SPEED = 0.4;
	
	private static final double[] SPEEDS = {0.2, 0.4, 0.6, 0.8, 1};
	private int currentSpeed = SPEEDS.length - 1;
	
	private int currentPos = 0;
	private boolean povHeld = false;

	@Override
	public double getMoveRequest() {
		double moveRequest = logitech.getY(Hand.kLeft);
    	moveRequest = Utils.deadzone(moveRequest, DEADZONE);
    	moveRequest = Utils.curve(moveRequest, MOVE_CURVE);
    	return moveRequest;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = logitech.getX(Hand.kRight);
    	turnRequest = Utils.deadzone(turnRequest, DEADZONE);
    	turnRequest = Utils.curve(turnRequest, TURN_CURVE);
    	return turnRequest;
	}

	@Override
	public double getSpeedLimiter() {
		if(logitech.getYButtonPressed() && currentSpeed < SPEEDS.length - 1) {
			currentSpeed++;
		} else if(logitech.getXButtonPressed() && currentSpeed > 0) {
			currentSpeed--;
		}
		
		return SPEEDS[currentSpeed];
	}

	@Override
	public boolean getReverseDirection() {
		return logitech.getBButtonPressed();
	}

	@Override
	public boolean getFailsafeDrive() {
		return logitech.getStartButton();
	}
	
	@Override
	public boolean getFailsafeCubes() {
		return logitech.getBackButton();
	}

	@Override
	public int getCubeManagerButton() {
		if(logitech.getBumperPressed(Hand.kRight))
			return 1;
		else if(logitech.getBumperReleased(Hand.kRight))
			return 2;
		
		switch(logitech.getPOV()) {
		case 0:
			return 3;
		case 180:
			return 4;
		}
		
		if(logitech.getBumperPressed(Hand.kLeft))
			return 5;
		
		return 0;
	}

	@Override
	public boolean getIntake() {
		return false;
	}

	@Override
	public boolean getShoot() {
		return false;
	}

	@Override
	public double getElbowSpeed() {
		double val = -Utils.deadzone(logitech.getTriggerAxis(Hand.kRight), DEADZONE) + Utils.deadzone(logitech.getTriggerAxis(Hand.kLeft), DEADZONE);
		return Utils.map(val, -1, 1, -MAX_CLAW_SPEED, MAX_CLAW_SPEED);
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

}
