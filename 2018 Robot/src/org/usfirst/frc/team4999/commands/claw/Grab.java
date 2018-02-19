package org.usfirst.frc.team4999.commands.claw;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Grab extends Command {
	
	private Intake claw = Robot.intake;
	
	private static final double CUTOFF_CURRENT = 20;
	private static final double CUTOFF_TIME = 0.5;
	
	private static final double INTAKE_SPEED = 1;
	private static final double HOLD_SPEED = 0.2;
	
	private Timer timer = new Timer();
	
	private PowerDistributionPanel pdp = RobotMap.pdp;
	
	private boolean hold = false;

    public Grab() {
        requires(claw);
        timer.start();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	claw.grip();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	claw.setIntake(INTAKE_SPEED);
    	
    	if(hold) {
    		claw.setIntake(HOLD_SPEED);
    	} else if(pdp.getCurrent(RobotMap.leftIntakePDP) > CUTOFF_CURRENT || pdp.getCurrent(RobotMap.rightIntakePDP) > CUTOFF_CURRENT)
    		hold = timer.hasPeriodPassed(CUTOFF_TIME);
    	else
    		timer.reset();    	
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
