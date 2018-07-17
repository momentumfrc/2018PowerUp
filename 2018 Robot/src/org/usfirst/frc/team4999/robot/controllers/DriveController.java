package org.usfirst.frc.team4999.robot.controllers;


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
	abstract public boolean getFailsafeElbow();
	
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
	 * Get the desired speed of the elbow
	 * @return Elbow's speed
	 */
	abstract public double getElbowSpeed();
	
	/**
	 * Get the desired speed of the lift
	 * @return Lift's speed
	 */
	abstract public double getLiftSpeed();
	
	/**
	 * Get whether to toggle the lift to high speed / low speed
	 * @return If the lift speed should be toggled
	 */
	abstract public boolean shiftLift();
	
	/**
	 * Vibrate the controller
	 * @param intensity How intensely to vibrate the controller
	 */
	public void vibrate(double intensity) {
	}
	
}
