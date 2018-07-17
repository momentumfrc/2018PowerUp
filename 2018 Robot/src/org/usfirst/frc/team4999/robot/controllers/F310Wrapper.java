package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class F310Wrapper extends DriveController {
	
	private LogitechF310 logitech = RobotMap.f310;
	
	private static final double MOVE_CURVE = 2;
	private static final double TURN_CURVE = 1;
	
	private static final double DEADZONE = 0.05;
	
	private static final double MAX_CLAW_SPEED = 0.4;
	
	private static final double LIFT_SPEED = 0.8;
	
	private static final double[] SPEEDS = {0.2, 0.4, 0.6, 0.8, 1};
	private int currentSpeed = SPEEDS.length - 1;

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
	public boolean getFailsafeElbow() {
		return logitech.getBackButton();
	}

	@Override
	public boolean getIntake() {
		return logitech.getBumper(Hand.kRight);
	}

	@Override
	public boolean getShoot() {
		return logitech.getBumper(Hand.kLeft);
	}

	@Override
	public double getElbowSpeed() {
		double val = -Utils.deadzone(logitech.getTriggerAxis(Hand.kRight), DEADZONE) + Utils.deadzone(logitech.getTriggerAxis(Hand.kLeft), DEADZONE);
		return Utils.map(val, -1, 1, -MAX_CLAW_SPEED, MAX_CLAW_SPEED);
	}
	
	@Override
	public double getLiftSpeed() {
		int pov = logitech.getPOV();
		if(pov == 315 || pov == 0 || pov == 45)
			return LIFT_SPEED;
		else if(pov == 135 || pov == 180 || pov == 225)
			return -LIFT_SPEED;
		else
			return 0;
	}

	@Override
	public boolean shiftLift() {
		return false;
	}


}
