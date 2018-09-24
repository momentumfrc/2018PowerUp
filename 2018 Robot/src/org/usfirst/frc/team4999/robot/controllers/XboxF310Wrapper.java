package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class XboxF310Wrapper extends DriveController {

	private XboxController xbox = RobotMap.xbox;
	private LogitechF310 logitech = RobotMap.f310;
	
	private static final double MOVE_CURVE = 2.5;
	private static final double TURN_CURVE = 2.5;
	
	private static final double DEADZONE = 0.1; 
	private static final double MAX_CLAW_SPEED = 0.8;
	
	private static final double[] SPEEDS = {0.2, 0.4, 0.6, 0.8, 1};
	private int currentSpeed = SPEEDS.length - 1;
	
	private static final double LIFT_SPEED = 0.8;
	
	private Timer climbTimer = new Timer();
	
	public XboxF310Wrapper() {
		climbTimer.start();
	}
	
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
	public boolean getFailsafeDrive() {
		return xbox.getStartButton();
	}

	@Override
	public boolean getFailsafeElbow() {
		return logitech.getBackButton();
	}

	@Override
	public boolean getIntake() {
		return logitech.getBumper(Hand.kLeft);
	}

	@Override
	public boolean getShoot() {
		return logitech.getBumper(Hand.kRight);
	}

	@Override
	public double getElbowSpeed() {
		double val = Utils.clip(logitech.getY(Hand.kLeft) + logitech.getY(Hand.kRight), -1, 1);
		return Utils.map(val, -1, 1, -MAX_CLAW_SPEED, MAX_CLAW_SPEED);
	}

	
	@Override
	public double getLiftSpeed() {
		int pov = xbox.getPOV();
		if(pov == 315 || pov == 0 || pov == 45)
			return LIFT_SPEED;
		else if(Utils.deadzone(xbox.getTriggerAxis(Hand.kRight), 0.1) > 0)
			return Utils.map(-Utils.deadzone(xbox.getTriggerAxis(Hand.kRight), 0.1), -1, 0, -0.2, 0);
		else if(pov == 135 || pov == 180 || pov == 225)
			return -LIFT_SPEED;
		else
			return 0;
	}

	
	@Override
	public void vibrate(double intensity) {
		xbox.setRumble(RumbleType.kLeftRumble, intensity);
		xbox.setRumble(RumbleType.kRightRumble, intensity);
	}
	
	
	@Override
	public boolean shiftLift() {
		return xbox.getBumperPressed(Hand.kLeft) || xbox.getBumperPressed(Hand.kRight);
	}

	@Override
	public boolean getFailsafeLift() {
		return xbox.getBackButton();
	}
	
	@Override
	public boolean getZeroLift() {
		return xbox.getAButton();
	}

}
