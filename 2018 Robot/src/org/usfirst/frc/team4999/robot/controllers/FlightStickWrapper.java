package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class FlightStickWrapper extends DriveController {
	
	private Joystick flightStick = RobotMap.flightStick;
	
	private static final double MOVE_CURVE = 2;
	private static final double TURN_CURVE = 1;
	
	private static final double DEADZONE = 0.1;

	@Override
	public double getMoveRequest() {
		double moveRequest = -flightStick.getY();
    	moveRequest = deadzone(moveRequest, DEADZONE);
    	moveRequest = curve(moveRequest, MOVE_CURVE);
    	return moveRequest;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = flightStick.getTwist();
    	turnRequest = deadzone(turnRequest, DEADZONE);
    	turnRequest = curve(turnRequest, TURN_CURVE);
    	return turnRequest;
	}

	@Override
	public double getSpeedLimiter() {
		return map(-flightStick.getThrottle(), -1, 1, 0, 1);
	}

	@Override
	public boolean getReverseDirection() {
		return flightStick.getRawButtonPressed(2);
	}

	@Override
	public boolean getKillPID() {
		return flightStick.getRawButton(7);
	}

	@Override
	public double getLiftSpeed() {
		double speed = 0;
		if(flightStick.getPOV() == 0)
			speed = getSpeedLimiter();
		else if(flightStick.getPOV() == 180)
			speed = -getSpeedLimiter();
		return speed;
	}
	
}
