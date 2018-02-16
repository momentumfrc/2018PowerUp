package org.usfirst.frc.team4999.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroLift extends Command {
	
	private Lift lift = Robot.lift;
	
	private static final double ZERO_SPEED = 0.2;

    public ZeroLift() {
        requires(lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lift.shiftLow();
    	lift.releaseBrake();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lift.set(-ZERO_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return RobotMap.liftZeroSwitch.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	lift.set(0);
    	RobotMap.liftEncoder.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
