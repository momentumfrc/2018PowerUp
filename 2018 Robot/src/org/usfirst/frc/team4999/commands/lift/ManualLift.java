package org.usfirst.frc.team4999.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualLift extends Command {

	private Lift lift = Robot.lift;
	
    public ManualLift() {
        requires(lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lift.set(Robot.controlChooser.getSelected().getLiftSpeed());
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
