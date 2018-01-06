package org.usfirst.frc.team4999.robot.commands;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import edu.wpi.first.wpilibj.XboxController;


import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class XboxDrive extends Command {

    public XboxDrive() {
    	requires(Robot.driveSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double moveRequest = RobotMap.xbox.getY(XboxController.Hand.kLeft);
    	double turnRequest = RobotMap.xbox.getX(XboxController.Hand.kRight);
    	double speedLimiter = 1;
    	Robot.driveSystem.ArcadeDrive(moveRequest, turnRequest, speedLimiter);
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
