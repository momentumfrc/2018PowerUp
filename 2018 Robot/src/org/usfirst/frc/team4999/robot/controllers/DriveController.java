package org.usfirst.frc.team4999.robot.controllers;

public abstract class DriveController {
	abstract public double getMoveRequest();
	abstract public double getTurnRequest();
	abstract public double getSpeedLimiter();
	
	protected double map(double val, double inmin, double inmax, double outmin, double outmax) {
    	return (((val - inmin) / (inmax - inmin)) * (outmax - outmin)) + outmin;
    }
    
    protected double deadzone(double val, double deadzone) {
    	if(val < -deadzone)
    		return map(val, -1, -deadzone, -1, 0);
    	else if(val > deadzone) 
    		return map(val, deadzone, 1, 0, 1);
    	else
    		return 0;
    }
    
    protected double curve(double val, double curve) {
    	if(curve == 0)
			return val;
		double powed = Math.pow(Math.abs(val), curve);
		if(val * powed > 0)
			return powed;
		else
			return -powed;
    }
    
    protected double clip(double val, double min, double max) {
    	return Math.max(Math.min(val, max), min);
    }
}
