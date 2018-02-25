package org.usfirst.frc.team4999.commands.elbow;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElbow extends Command {

    public MoveElbow() {
        requires(Robot.elbow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elbow.pid.setSetpointRelative(0);
    	Robot.elbow.pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elbow.pid.setSetpoint(Robot.elbow.pid.getSetpoint() + Robot.controlChooser.getSelected().getClaw());
    	Robot.elbow.drivePID();
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
