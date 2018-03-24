package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.utils.Utils;

public abstract class DriveController {
	/**
	 * Get the speed at which the robot should move
	 * @return Requested robot speed
	 */
	abstract public double getMoveRequest();
	/**
	 * Get the speed at which the robot should turn
	 * @return Requested robot turn speed
	 */
	abstract public double getTurnRequest();
	/**
	 * Get the current throttle
	 * @return Speed limit for the robot
	 */
	abstract public double getSpeedLimiter();
	
	/**
	 * Get whether to flip front and back on the robot
	 * @return If front and back should be flipped
	 */
	abstract public boolean getReverseDirection();
	/**
	 * Get whether the drivetrain should enter a failsafe teleop mode
	 * @return If failsafe teleop should be entered
	 */
	abstract public boolean getFailsafeDrive();
	
	/**
	 * Get whether the elbow should enter a failsafe teleop mode (disables PID)
	 * @return If the failsafe should be activated
	 */
	abstract public boolean getFailsafeCubes();
	
	/**
	 * Trigger the intake
	 * @return If the intake should be running
	 */
	abstract public boolean getIntake();
	/**
	 * Trigger releasing a held cube
	 * @return If the arms should be toggled
	 */
	abstract public boolean getShoot();
	/**
	 * Get the delta that should be applied to the claw's setpoint
	 * @return Claw's setpoint delta
	 */
	abstract public double getElbowSpeed();
	
	abstract public double getLiftPosition();
	abstract public double getLiftSpeed();
	
	/**
	 * Gets input to send to cube manager
	 * 0 - no buttons pushed
	 * 1 - intake pushed
	 * 2 - intake released
	 * 3 - Aim up
	 * 4 - Aim down
	 * 5 - Shoot
	 * @return
	 */
	abstract public int getCubeManagerButton();
	
	abstract public boolean climb();
	
	public boolean shiftLift() {
		return false;
	}
	
	public boolean useCubeManager() {
		return true;
	}
	
	public void vibrate(double intensity) {
	}
	
	public boolean zeroLift() {
		return false;
	}
	
}
