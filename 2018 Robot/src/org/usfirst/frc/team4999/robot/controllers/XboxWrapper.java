package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

public class XboxWrapper extends DriveController {
	
	private XboxController xbox = RobotMap.xbox;
	
	private static final double MOVE_CURVE = 2.5;
	private static final double TURN_CURVE = 2.5;
	
	private static final double DEADZONE = 0.1;
	private static final double MAX_LIFT_SPEED = 1;
	private static final int MAX_CLAW_SPEED = 10;
	
	private double speedLimit = 1;

	@Override
	public double getMoveRequest() {
		double moveRequest = xbox.getY(XboxController.Hand.kLeft);
    	moveRequest = deadzone(moveRequest, DEADZONE);
    	moveRequest = curve(moveRequest, MOVE_CURVE);
    	return moveRequest;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = RobotMap.xbox.getX(XboxController.Hand.kRight);
    	turnRequest = deadzone(turnRequest, DEADZONE);
    	turnRequest = curve(turnRequest, TURN_CURVE);
    	return turnRequest;
	}

	@Override
	public double getSpeedLimiter() {
		return speedLimit;
	}
	
	public void setSpeedLimit(double speed) {
		speedLimit = clip(speed, 0, 1);
	}

	@Override
	public boolean getReverseDirection() {
		return xbox.getXButtonPressed();
	}

	@Override
	public boolean getKillPID() {
		return xbox.getStartButton();
	}
	
	@Override
	public double getLiftSpeed() {
		double speed = xbox.getY(XboxController.Hand.kRight);
		speed = deadzone(speed, DEADZONE);
		speed = map(speed, -1, 1, -MAX_LIFT_SPEED, MAX_LIFT_SPEED);
		return speed;
	}

	@Override
	public boolean getIntake() {
		return xbox.getBumper(Hand.kRight);
	}

	@Override
	public boolean getOuttake() {
		return xbox.getBumper(Hand.kLeft);
	}

	@Override
	public double getClaw() {
		double right = -deadzone(xbox.getTriggerAxis(Hand.kRight), DEADZONE);
		double left = deadzone(xbox.getTriggerAxis(Hand.kLeft), DEADZONE);
		if(right != 0) {
			return map(right, -1, 0, -MAX_CLAW_SPEED, 0);
		} else if(left != 0) {
			return map(left, 0, 1, 0, MAX_CLAW_SPEED);
		} else {
			return 0;
		}
	}

}
