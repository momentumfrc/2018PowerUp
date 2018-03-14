package org.usfirst.frc.team4999.commands;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.choosers.ControlChooser;
import org.usfirst.frc.team4999.robot.controllers.DriveController;
import org.usfirst.frc.team4999.robot.subsystems.DriveSystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTiltPID extends Command {

	private DriveSystem drive = Robot.driveSystem;
	private ControlChooser chooser = Robot.controlChooser;
	
	private boolean reversed;
	
    public DriveTiltPID() {
        requires(drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive.pitchPID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DriveController controller = chooser.getSelected();
    	if(controller.getReverseDirection())
    		reversed = !reversed;
    	drive.arcadeDriveTilt((reversed)?-controller.getMoveRequest():controller.getMoveRequest(), controller.getTurnRequest(), controller.getSpeedLimiter());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
