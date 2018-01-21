package org.usfirst.frc.team4999.robot.commands.drive;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlightStickDrive extends Command {
	
	private final double curve = 2.5;
	private final double deadzone = 0.05;

    public FlightStickDrive() {
        requires(Robot.driveSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double moveRequest = -RobotMap.flightStick.getY();
    	moveRequest = deadzone(moveRequest, deadzone);
    	moveRequest = curve(moveRequest, curve);
    	
    	double turnRequest = RobotMap.flightStick.getTwist();
    	turnRequest = deadzone(turnRequest, deadzone);
    	turnRequest = curve(turnRequest, curve);
    	
    	double speedLimiter = (-RobotMap.flightStick.getThrottle() + 1) / 2;
    	Robot.driveSystem.arcadeDrive(moveRequest, turnRequest, speedLimiter);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    private double map(double val, double inmin, double inmax, double outmin, double outmax) {
    	return (((val - inmin) / (inmax - inmin)) * (outmax - outmin)) + outmin;
    }
    
    private double deadzone(double val, double deadzone) {
    	if(val < -deadzone)
    		return map(val, -1, -deadzone, -1, 0);
    	else if(val > deadzone) 
    		return map(val, deadzone, 1, 0, 1);
    	else
    		return 0;
    }
    
    private double curve(double val, double curve) {
    	if(curve == 0)
			return val;
		double powed = Math.pow(Math.abs(val), curve);
		if(val * powed > 0)
			return powed;
		else
			return -powed;
    }
}
