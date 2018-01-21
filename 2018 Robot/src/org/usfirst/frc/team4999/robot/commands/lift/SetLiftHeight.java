package org.usfirst.frc.team4999.robot.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Set the lift height. Does not change the brake's state
 */
public class SetLiftHeight extends Command {
	
	private double height;

    public SetLiftHeight(double height) {
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.setSetpoint(height);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lift.onTarget() || Robot.lift.braked;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
