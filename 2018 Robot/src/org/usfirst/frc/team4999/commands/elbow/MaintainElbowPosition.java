package org.usfirst.frc.team4999.commands.elbow;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.Elbow;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MaintainElbowPosition extends Command {

	private Elbow elbow = Robot.elbow;
	
    public MaintainElbowPosition() {
        requires(elbow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elbow.pid.enable();
    	elbow.pid.setSetpointRelative(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elbow.drivePID();
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
