package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class RealFlightWrapper extends DriveController {

	private static final double TURN_DEADZONE = 0.05;	
	private static final double[] THROTTLES = {0.2, 0.4, 0.6, 0.8, 1};
	private static final double LIFT_SPEED = 0.8;
	
	private Joystick Tx; 
	private int currentIdx = THROTTLES.length-1;
	
	public RealFlightWrapper() {
			Tx = RobotMap.Tx;
			
		}

	@Override
	public double getMoveRequest() {
		double moveRequest = Tx.getRawAxis(2);
		return moveRequest;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = Tx.getRawAxis(4);
		return 0;
	}

	@Override
	public double getSpeedLimiter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getReverseDirection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFailsafeDrive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFailsafeElbow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getIntake() {
		return Tx.getRawButton(5);
	}

	@Override
	public boolean getShoot() {
		return Tx.getRawButton(4);
	}

	@Override
	public double getElbowSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLiftSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean shiftLift() {
		// TODO Auto-generated method stub
		return false;
	}

}
