package org.usfirst.frc.team4999.commands.elbow;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroElbow extends Command {
	
	private static final double ZERO_SPEED = 0.2;
	private boolean done = false;

    public ZeroElbow() {
    	requires(Robot.elbow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.elbow.zero();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elbow.retract();
    	Robot.elbow.setDefaultCommand(new MaintainElbowPosition());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
