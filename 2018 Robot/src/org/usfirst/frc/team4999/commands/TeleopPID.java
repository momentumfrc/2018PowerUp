package org.usfirst.frc.team4999.commands;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.choosers.ControlChooser;
import org.usfirst.frc.team4999.robot.controllers.DriveController;
import org.usfirst.frc.team4999.robot.subsystems.DriveSystem;
import org.usfirst.frc.team4999.utils.MoPrefs;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopPID extends Command {
	
	private DriveSystem drive = Robot.driveSystem;
	private ControlChooser chooser = Robot.controlChooser;
	
    public TeleopPID() {
    	requires(drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive.moveRatePID.enable();
    	drive.turnRatePID.enable();
    	drive.pitchPID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DriveController controller = chooser.getSelected();
    	double moveRequest = controller.map(controller.getMoveRequest() * controller.getSpeedLimiter(), -1, 1, -MoPrefs.getMaxMoveSpeed(), MoPrefs.getMaxMoveSpeed());
    	double turnRequest = controller.map(controller.getTurnRequest() * controller.getSpeedLimiter(), -1, 1, -MoPrefs.getMaxTurnSpeed(), MoPrefs.getMaxTurnSpeed());
    	
    	drive.moveRatePID.setSetpoint(moveRequest);
    	drive.turnRatePID.setSetpoint(turnRequest);
    	
    	if(moveRequest == 0 && turnRequest != 0)
    		drive.driveTurnRatePID();
    	else
    		drive.driveMoveRatePID();
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
