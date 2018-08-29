package org.usfirst.frc.team4999.commands.elbow;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.Elbow;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetElbowPosition extends Command {

	private Elbow elbow = Robot.elbow;
	
	private double pos;
	
    public SetElbowPosition(double newPos) {
    	this.pos = newPos;
    	requires(elbow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elbow.pid.setSetpoint(pos);
    	elbow.pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.format("Setpoint:%.2f Current:%.2f Output:%.2f\n", pos, RobotMap.elbowEncoder.getDistance(),elbow.pid.get());
    	elbow.drivePID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return elbow.pid.onTargetForTime();
    }

    // Called once after isFinished returns true
    protected void end() {
    	elbow.setDefaultCommand(new MaintainElbowPosition());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
