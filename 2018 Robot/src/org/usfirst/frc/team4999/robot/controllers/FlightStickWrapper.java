package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class FlightStickWrapper extends DriveController {
	
	private Joystick flightStick = RobotMap.flightStick;
	
	private static final double MOVE_CURVE = 2;
	private static final double TURN_CURVE = 1;
	
	private static final double DEADZONE = 0.1;
	
	private static final double LIFT_SPEED = 0.8;
	
	private static final double ELBOW_SPEED = 0.4;
	
	private int currentPos = 0;
	private boolean povHeld = false;

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
	public boolean getFailsafeCubes() {
		return flightStick.getRawButton(8);
	}

	@Override
	public int getCubeManagerButton() {
		if(flightStick.getRawButtonPressed(3))
			return 1;
		else if(flightStick.getRawButtonReleased(3))
			return 2;
		
		switch(flightStick.getPOV()) {
		case 0:
			return 3;
		case 180:
			return 4;
		}
		
		if(flightStick.getRawButtonPressed(4))
			return 5;
		
		return 0;
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
	public boolean climb() {
		return flightStick.getRawButton(12);
	}

	
	
	
	
}
