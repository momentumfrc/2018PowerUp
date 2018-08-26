package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.DriveSystem;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnDegrees extends Command {

	private double angle;
	
	private DriveSystem drive = Robot.driveSystem;
	
	private static final double TIMEOUT = 5;
	private Timer timer = new Timer();
	
	// Clockwise
    public TurnDegrees(double angle) {
    	requires(drive);
    	this.angle = angle;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	drive.turnPID.setSetpointRelative(angle);
    	drive.turnPID.enable();
    	System.out.format("Beginning turn using P:%.2f I:%.2f D:%.2f\n", drive.turnPID.getP(), drive.turnPID.getI(), drive.turnPID.getD());
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.format("Current:%.2f Setpoint:%.2f Output:%.2f\n", RobotMap.vmx.getAngle(), drive.turnPID.getSetpoint(), drive.turnPID.get());
    	drive.driveDisplacementPID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return drive.turnPID.onTargetForTime() || timer.hasPeriodPassed(TIMEOUT);
    }

    // Called once after isFinished returns true
    protected void end() {
    	drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}
