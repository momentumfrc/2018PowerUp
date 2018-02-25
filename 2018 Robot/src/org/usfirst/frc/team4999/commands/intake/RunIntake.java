package org.usfirst.frc.team4999.commands.intake;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunIntake extends Command {
	
	private Intake intake = Robot.intake;
	
	private static final double CUTOFF_CURRENT = 20;
	private static final double CUTOFF_TIME = 0.5;
	
	private static final double INTAKE_SPEED = 1;
	
	private Timer timer = new Timer();
	private PowerDistributionPanel pdp = RobotMap.pdp;

    public RunIntake() {
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.setIntake(INTAKE_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(pdp.getCurrent(RobotMap.leftIntakePDP) > CUTOFF_CURRENT || pdp.getCurrent(RobotMap.rightIntakePDP) > CUTOFF_CURRENT)
    		return timer.hasPeriodPassed(CUTOFF_TIME);
    	else
    		timer.reset();
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setHolding(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
