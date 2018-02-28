package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;

public class FlightStickWrapper extends DriveController {
	
	private Joystick flightStick = RobotMap.flightStick;
	
	private static final double MOVE_CURVE = 2;
	private static final double TURN_CURVE = 1;
	
	private static final double DEADZONE = 0.1;
	
	private static final double CLAW_SPEED = 10;
	
	private int currentPos = 0;
	private boolean povHeld = false;

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
		return Utils.map(-flightStick.getThrottle(), -1, 1, 0, 1);
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
	public double getLiftPosition() {
		double[] values = LiftPosition.values();
		int pov = flightStick.getPOV();
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
		return flightStick.getRawButton(3);
	}

	@Override
	public boolean getShoot() {
		return flightStick.getRawButton(4);
	}

	@Override
	public double getClaw() {
		if(flightStick.getRawButton(5))
			return CLAW_SPEED * getSpeedLimiter();
		else if(flightStick.getRawButton(6))
			return -CLAW_SPEED * getSpeedLimiter();
		else
			return 0;
	}
	
	
	
}
