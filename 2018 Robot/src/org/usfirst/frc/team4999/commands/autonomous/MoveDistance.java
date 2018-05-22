package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.DriveSystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveDistance extends Command {
	
	private double distance;
	
	private DriveSystem drive = Robot.driveSystem;
	
	private static final double TIMEOUT = 5;
	private Timer timer = new Timer();
	
    public MoveDistance(double distance) {
    	requires(drive);
    	this.distance = distance;
    	System.out.format("Beginning move using\n    Move P:%.2d I:%.2d D:%.2d\n    Turn: P:%.2d I:%.2d D:%.2d\n", 
    			drive.movePID.getP(), drive.movePID.getI(), drive.movePID.getD(),
    			drive.turnPID.getP(), drive.turnPID.getI(), drive.turnPID.getD()
    			);
    }

    

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive.movePID.setSetpointRelative(distance);
    	drive.movePID.enable();
    	drive.turnPID.setSetpointRelative(0);
    	drive.turnPID.enable();
    	drive.pitchPID.enable();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.driveDisplacementPID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return drive.movePID.onTargetForTime() || timer.hasPeriodPassed(TIMEOUT);
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
