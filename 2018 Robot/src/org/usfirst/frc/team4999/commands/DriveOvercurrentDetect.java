package org.usfirst.frc.team4999.commands;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveOvercurrentDetect extends Command {
	
	private static final double TIME = 2;
	private Timer timer = new Timer();
	
	private Trigger cancel;

    public DriveOvercurrentDetect(Trigger cancel) {
    	this.cancel = cancel;
    }
    
    private boolean exit = false;

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(DriverStation.getInstance().isAutonomous()) {
    		(new KillDrive()).start();
    		exit = true;
    	} else {
    		timer.start();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timer.hasPeriodPassed(TIME)) {
    		Robot.controlChooser.getSelected().vibrate(0.8);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return exit || !cancel.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.controlChooser.getSelected().vibrate(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
