package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class F310XboxWrapper extends DriveController {
	
	private XboxController xbox = RobotMap.xbox;
	private LogitechF310 f310 = RobotMap.f310;
	
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
		double moveRequest = f310.getY(Hand.kLeft);
    	moveRequest = Utils.deadzone(moveRequest, DEADZONE);
    	moveRequest = Utils.curve(moveRequest, MOVE_CURVE);
    	return moveRequest;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = f310.getX(Hand.kRight);
    	turnRequest = Utils.deadzone(turnRequest, DEADZONE);
    	turnRequest = Utils.curve(turnRequest, TURN_CURVE);
    	return turnRequest;
	}

	@Override
	public double getSpeedLimiter() {
		if(f310.getYButtonPressed() && currentSpeed < SPEEDS.length - 1) {
			currentSpeed++;
		} else if(f310.getXButtonPressed() && currentSpeed > 0) {
			currentSpeed--;
		}
		
		return SPEEDS[currentSpeed];
	}

	@Override
	public boolean getReverseDirection() {
		return f310.getBButtonPressed();
	}

	@Override
	public boolean getFailsafeDrive() {
		return f310.getStartButton();
	}

	@Override
	public double getLiftPosition() {
		double[] values = LiftPosition.values();
		int pov = f310.getPOV();
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
	public boolean getFailsafeCubes() {
		return xbox.getBackButton();
	}

	@Override
	public boolean getIntake() {
		return xbox.getBumper(Hand.kRight);
	}

	@Override
	public boolean getShoot() {
		return xbox.getBumper(Hand.kLeft);
	}

	@Override
	public double getElbowSpeed() {
		double val = -Utils.deadzone(xbox.getTriggerAxis(Hand.kRight), DEADZONE) + Utils.deadzone(xbox.getTriggerAxis(Hand.kLeft), DEADZONE);
		return Utils.map(val, -1, 1, -MAX_CLAW_SPEED, MAX_CLAW_SPEED);
	}

	@Override
	public int getCubeManagerButton() {
		return 0;
	}
	
	@Override
	public boolean climb() {
		return f310.getBumper(Hand.kRight);
	}

}
