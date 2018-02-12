package org.usfirst.frc.team4999.commands;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.choosers.ControlChooser;
import org.usfirst.frc.team4999.robot.controllers.DriveController;
import org.usfirst.frc.team4999.robot.subsystems.DriveSystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopNoPID extends Command {
	
	private DriveSystem drive = Robot.driveSystem;
	private ControlChooser chooser = Robot.controlChooser;
	
    public TeleopNoPID() {
        requires(drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DriveController controller = chooser.getSelected();
    	drive.arcadeDrive(controller.getMoveRequest(), controller.getTurnRequest(), controller.getSpeedLimiter());
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
