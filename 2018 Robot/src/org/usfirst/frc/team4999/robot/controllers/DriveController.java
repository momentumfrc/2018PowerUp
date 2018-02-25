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
	
	abstract public double getLiftPosition();
	
	/**
	 * Get whether to flip front and back on the robot
	 * @return If front and back should be flipped
	 */
	abstract public boolean getReverseDirection();
	/**
	 * Get whether to enter a failsafe teleop mode
	 * @return If failsafe teleop should be entered
	 */
	abstract public boolean getKillPID();
	
	/**
	 * Trigger the intake
	 * @return If the intake should be running
	 */
	abstract public boolean getIntake();
	/**
	 * Trigger releasing a held cube
	 * @return If the arms should be toggled
	 */
	abstract public boolean getArms();
	/**
	 * Get the delta that should be applied to the claw's setpoint
	 * @return Claw's setpoint delta
	 */
	abstract public double getClaw();
	
	public double map(double val, double inmin, double inmax, double outmin, double outmax) {
    	return (((val - inmin) / (inmax - inmin)) * (outmax - outmin)) + outmin;
    }
    
    public double deadzone(double val, double deadzone) {
    	if(val < -deadzone)
    		return map(val, -1, -deadzone, -1, 0);
    	else if(val > deadzone) 
    		return map(val, deadzone, 1, 0, 1);
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
    
    public double clip(double val, double min, double max) {
    	return Math.max(Math.min(val, max), min);
    }
}
