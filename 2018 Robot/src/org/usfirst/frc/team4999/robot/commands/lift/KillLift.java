package org.usfirst.frc.team4999.robot.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Kill the lift, safety stop
 */
public class KillLift extends Command {

    public KillLift() {
        requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("KILLING LIFT MOTORS");
    	Robot.lift.disable();
    	Robot.lift.set(0);
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
