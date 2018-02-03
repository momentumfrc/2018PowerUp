package org.usfirst.frc.team4999.robot.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Enable or disable the lift brake
 */
public class SetLiftBrake extends Command {
	
	private double currentCutoff = 15; // Amps
	private double timerCutoff = 1.0; // Seconds
	
	private Timer timer = new Timer();
	
	private Spark motor = RobotMap.liftBrake;
	private int motorPower = RobotMap.liftBrakePDP;
	private PowerDistributionPanel pdp = RobotMap.pdp;
	
	private final double speed = 0.5;
	
	private boolean forward;

    public SetLiftBrake(boolean on) {
        requires(Robot.lift);
        this.forward = on;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	if(!forward) {
    		Robot.lift.setSetpoint(Robot.lift.getPosition());
    		Robot.lift.braked = false;
    		Robot.lift.enable();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	motor.set(forward ? speed : -speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pdp.getCurrent(motorPower) >= currentCutoff || timer.hasPeriodPassed(timerCutoff);
    }

    // Called once after isFinished returns true
    protected void end() {
    	motor.set(0);
    	if(forward) {
    		Robot.lift.disable();
    		Robot.lift.set(0);
    		Robot.lift.braked = true;
    	}
    	timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
