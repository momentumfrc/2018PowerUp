package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class XboxFlightstickWrapper extends DriveController {
	
	private XboxController xbox = RobotMap.xbox;
	private Joystick flightStick = RobotMap.flightStick;
	
	private static final double MOVE_CURVE = 2.5;
	private static final double TURN_CURVE = 2.5;
	
	private static final double DEADZONE = 0.1;
	private static final double ELBOW_SPEED = 0.4;
	
	private static final double[] SPEEDS = {0.2, 0.4, 0.6, 0.8, 1};
	private int currentSpeed = SPEEDS.length - 1;
	
	private int currentPos = 0;
	private boolean povHeld = false;

	@Override
	public double getMoveRequest() {
		double moveRequest = xbox.getY(XboxController.Hand.kLeft);
    	moveRequest = Utils.deadzone(moveRequest, DEADZONE);
    	moveRequest = Utils.curve(moveRequest, MOVE_CURVE);
    	return moveRequest;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = RobotMap.xbox.getX(XboxController.Hand.kRight);
    	turnRequest = Utils.deadzone(turnRequest, DEADZONE);
    	turnRequest = Utils.curve(turnRequest, TURN_CURVE);
    	return turnRequest;
	}

	@Override
	public double getSpeedLimiter() {
		if(xbox.getYButtonPressed() && currentSpeed < SPEEDS.length - 1) {
			currentSpeed++;
		} else if(xbox.getXButtonPressed() && currentSpeed > 0) {
			currentSpeed--;
		}
		
		return SPEEDS[currentSpeed];
	}

	@Override
	public boolean getReverseDirection() {
		return xbox.getBButtonPressed();
	}

	@Override
	public double getLiftPosition() {
		double[] values = LiftPosition.values();
		int pov = xbox.getPOV();
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
	public boolean getFailsafeDrive() {
		return xbox.getStartButton();
	}

	@Override
	public boolean getFailsafeCubes() {
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
	public int getCubeManagerButton() {
		return 0;
	}

	@Override
	public boolean climb() {
		return xbox.getBumper(Hand.kRight);
	}

}