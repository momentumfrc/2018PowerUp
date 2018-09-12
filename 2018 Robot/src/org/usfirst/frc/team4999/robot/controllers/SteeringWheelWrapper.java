package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class SteeringWheelWrapper extends DriveController {
	private static final double TURN_DEADZONE = 0.05;	
	private static final double[] THROTTLES = {0.2, 0.4, 0.6, 0.8, 1};
	private static final double LIFT_SPEED = 0.8;
	
	private Joystick wheel; 
	private int currentIdx = THROTTLES.length-1;
	
	public SteeringWheelWrapper() {
		wheel = RobotMap.wheel;
		
	}

	@Override
	public double getMoveRequest() {
		double moveRequest1 = wheel.getRawAxis(2);
		moveRequest1 = 1-Utils.map(moveRequest1, -1, 1, 0, 1);
		double moveRequest2 = wheel.getRawAxis(1);
		moveRequest2 = 1-Utils.map(moveRequest2, -1, 1, 0, 1);
		return moveRequest2 - moveRequest1;
	}

	@Override
	public double getTurnRequest() {
		double turnRequest = wheel.getRawAxis(0);
		turnRequest = Utils.deadzone(turnRequest, TURN_DEADZONE);
		return turnRequest;
	}

	@Override
	public double getSpeedLimiter() {
		if(wheel.getRawButtonPressed(5) && currentIdx > 0) {
			currentIdx--;
		}
		if(wheel.getRawButtonPressed(6) && currentIdx < THROTTLES.length-1) {
			currentIdx++;
		}
		return THROTTLES[currentIdx];
	}

	@Override
	public boolean getReverseDirection() {
		return wheel.getRawButtonPressed(3);
	}

	@Override
	public boolean getFailsafeDrive() {
		return wheel.getRawButtonPressed(13);
	}

	@Override
	public boolean getFailsafeElbow() {
		return wheel.getRawButtonPressed(10);
	}

	@Override
	public boolean getIntake() {
		return wheel.getRawButton(8);
	}

	@Override
	public boolean getShoot() {
		return wheel.getRawButtonPressed(12);
	}

	@Override
	public double getElbowSpeed() {
		if(wheel.getRawButton(7)) {
			return -1;
		}
		if(wheel.getRawButton(11)) {
			return 1;
		}
		return 0;
	}

	@Override
	public double getLiftSpeed() {
		int pov = wheel.getPOV();
		if(pov == 315 || pov == 0 || pov == 45)
			return LIFT_SPEED;
		else if(pov == 135 || pov == 180 || pov == 225)
			return -LIFT_SPEED;
		else
			return 0;
	}

	@Override
	public boolean shiftLift() {
		return wheel.getRawButtonPressed(1);
	}

}
