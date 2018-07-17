package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;

public class XboxWrapper extends DriveController {
	
	private XboxController xbox = RobotMap.xbox;
	
	private static final double MOVE_CURVE = 2.5;
	private static final double TURN_CURVE = 2.5;
	
	private static final double DEADZONE = 0.1;
	private static final double MAX_CLAW_SPEED = 0.4;
	
	private static final double[] SPEEDS = {0.2, 0.4, 0.6, 0.8, 1};
	private int currentSpeed = SPEEDS.length - 1;
	
	private static final double LIFT_SPEED = 0.8;

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
	public boolean getFailsafeElbow() {
		return xbox.getBackButton();
	}

	@Override
	public boolean getFailsafeDrive() {
		return xbox.getStartButton();
	}

	@Override
	public boolean getIntake() {
		return xbox.getBumper(Hand.kRight);
	}

	@Override
	public boolean getShoot() {
		return xbox.getBumper(Hand.kLeft);
	}

	@Override
	public double getElbowSpeed() {
		double val = -Utils.deadzone(xbox.getTriggerAxis(Hand.kRight), DEADZONE) + Utils.deadzone(xbox.getTriggerAxis(Hand.kLeft), DEADZONE);
		return Utils.map(val, -1, 1, -MAX_CLAW_SPEED, MAX_CLAW_SPEED);
	}
	
	@Override
	public double getLiftSpeed() {
		int pov = xbox.getPOV();
		if(pov == 315 || pov == 0 || pov == 45)
			return LIFT_SPEED;
		else if(pov == 135 || pov == 180 || pov == 225)
			return -LIFT_SPEED;
		else
			return 0;
	}
	
	@Override
	public void vibrate(double intensity) {
		xbox.setRumble(RumbleType.kLeftRumble, intensity);
	}

	@Override
	public boolean shiftLift() {
		return false;
	}

}
