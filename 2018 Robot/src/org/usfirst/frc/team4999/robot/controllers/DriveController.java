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
	
	abstract public double getLiftPosition();
	
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
	abstract public boolean getHunt();
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
	
	
    
    public double deadzone(double val, double deadzone) {
    	if(val < -deadzone)
    		return Utils.map(val, -1, -deadzone, -1, 0);
    	else if(val > deadzone) 
    		return Utils.map(val, deadzone, 1, 0, 1);
    	else
    		return 0;
    }
    
    public double curve(double val, double curve) {
    	if(curve == 0)
			return val;
		double powed = Math.pow(Math.abs(val), curve);
		if(val * powed > 0)
			return powed;
		else
			return -powed;
    }
    
}
