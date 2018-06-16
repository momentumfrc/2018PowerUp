package org.usfirst.frc.team4999.commands.elbow;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElbowNoLimit extends Command {

    public ElbowNoLimit() {
        requires(Robot.elbow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elbow.setElbowNoLimits(Robot.controlChooser.getSelected().getElbowSpeed());
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
}
