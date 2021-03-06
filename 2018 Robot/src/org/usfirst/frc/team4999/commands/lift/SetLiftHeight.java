package org.usfirst.frc.team4999.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetLiftHeight extends Command {
	
	private Lift lift = Robot.lift;
	
	private double newHeight;
	private boolean fast;

    public SetLiftHeight(double height, boolean fast) {
        requires(lift);
        newHeight = height;
        this.fast = fast;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(fast)
    		lift.shiftHigh();
    	else
    		lift.shiftLow();
    	lift.enablePID();
    	lift.setHeight(newHeight);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lift.driveLiftPID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return lift.isOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
