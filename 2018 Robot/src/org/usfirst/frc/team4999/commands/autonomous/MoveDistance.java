package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.utils.MoPrefs;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class MoveDistance extends Command {
	
	private double distance;
	
	private Timer onTargetTime;
	
	
    public MoveDistance(double distance) {
    	requires(Robot.driveSystem);
    	this.distance = distance;
    	onTargetTime = new Timer();
    	System.out.format("Beginning move using\n    Move P:%.2d I:%.2d D:%.2d\n    Turn: P:%.2d I:%.2d D:%.2d\n", 
    			Robot.driveSystem.movePID.getP(), Robot.driveSystem.movePID.getI(), Robot.driveSystem.movePID.getD(),
    			Robot.driveSystem.turnPID.getP(), Robot.driveSystem.turnPID.getI(), Robot.driveSystem.turnPID.getD()
    			);
    }

    

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSystem.movePID.setSetpointRelative(distance);
    	Robot.driveSystem.movePID.enable();
    	Robot.driveSystem.turnPID.setSetpointRelative(0);
    	Robot.driveSystem.turnPID.enable();
    	onTargetTime.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSystem.arcadeDrive(Robot.driveSystem.movePID.get(), Robot.driveSystem.turnPID.get(), MoPrefs.getAutoSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.driveSystem.movePID.onTarget()) {
        	if(onTargetTime.hasPeriodPassed(MoPrefs.getTargetTime())) {
        		return true;
        	}
        } else {
        	onTargetTime.reset();
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
