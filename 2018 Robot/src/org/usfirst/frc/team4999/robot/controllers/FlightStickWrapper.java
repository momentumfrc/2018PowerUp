package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;

public class FlightStickWrapper extends DriveController {
	
	private Joystick flightStick = RobotMap.flightStick;
	
	private static final double MOVE_CURVE = 2;
	private static final double TURN_CURVE = 1;
	
	private static final double DEADZONE = 0.1;
	
	private static final double LIFT_SPEED = 0.8;
	
	private static final double ELBOW_SPEED = 0.4;
	
	
	@Override
	public double getMoveRequest() {
		double moveRequest = -flightStick.getY();
    	moveRequest = Utils.deadzone(moveRequest, DEADZONE);
    	moveRequest = Utils.curve(moveRequest, MOVE_CURVE);
    	return moveRequest;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = flightStick.getTwist();
    	turnRequest = Utils.deadzone(turnRequest, DEADZONE);
    	turnRequest = Utils.curve(turnRequest, TURN_CURVE);
    	return turnRequest;
	}

	@Override
	public double getSpeedLimiter() {
		return Utils.map(-flightStick.getThrottle(), -1, 1, 0, 1);
	}

	@Override
	public boolean getReverseDirection() {
		return flightStick.getRawButtonPressed(2);
	}

	@Override
	public boolean getFailsafeDrive() {
		return flightStick.getRawButton(7);
	}
	
	@Override
	public boolean getFailsafeElbow() {
		return flightStick.getRawButton(8);
	}

	@Override
	public boolean getIntake() {
		return flightStick.getRawButton(3);
	}

	@Override
	public boolean getShoot() {
		return flightStick.getRawButton(4);
	}

	@Override
	public double getElbowSpeed() {
		if(flightStick.getRawButton(5))
			return ELBOW_SPEED;
		else if(flightStick.getRawButton(6))
			return -ELBOW_SPEED;
		else
			return 0;
	}
	
	@Override
	public double getLiftSpeed() {
		int pov = flightStick.getPOV();
		if(pov == 315 || pov == 0 || pov == 45)
			return LIFT_SPEED;
		else if(pov == 135 || pov == 180 || pov == 225)
			return -LIFT_SPEED;
		else
			return 0;
	}

	@Override
	public boolean shiftLift() {
		return flightStick.getRawButtonPressed(12);
	}
	
}
