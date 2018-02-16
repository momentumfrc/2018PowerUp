package org.usfirst.frc.team4999.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopLift extends Command {
	
	private Lift lift = Robot.lift;
	
	boolean justSetZero = true;

    public TeleopLift() {
        requires(lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lift.releaseBrake();
    	lift.enablePID();
    	lift.setHeight(lift.getCurrentHeight());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = Robot.controlChooser.getSelected().getLiftSpeed();
    	if(speed == 0) {
    		if(justSetZero) {
    			justSetZero = false;
    			lift.setHeight(lift.getCurrentHeight());
    		}
    		lift.driveLiftPID();
    	} else {
    		justSetZero = true;
    		lift.set(speed);
    	}
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
