package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class F310Wrapper extends DriveController {
	
	private Joystick logitech = RobotMap.f310;
	
	private static final double MOVE_CURVE = 2;
	private static final double TURN_CURVE = 1;
	
	private static final double DEADZONE = 0.05;
	
	

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

}
