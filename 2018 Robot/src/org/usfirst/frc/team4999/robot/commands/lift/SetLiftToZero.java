package org.usfirst.frc.team4999.robot.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the setpoint down until the limit switch is activated. Does not disengage or engage the brake
 */
public class SetLiftToZero extends Command {
	
	private final double speed = 0.1;
	private DigitalInput limit = RobotMap.liftZeroSwitch;

    public SetLiftToZero() {
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.lift.setSetpointRelative(-speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return limit.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.liftEncoder.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
