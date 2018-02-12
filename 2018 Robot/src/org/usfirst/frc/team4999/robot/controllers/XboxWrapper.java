package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.XboxController;

public class XboxWrapper extends DriveController {
	
	private XboxController xbox = RobotMap.xbox;
	
	private static final double MOVE_CURVE = 2.5;
	private static final double TURN_CURVE = 2.5;
	
	private static final double DEADZONE = 0.1;
	
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

}
