package org.usfirst.frc.team4999.commands.intake;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootIntake extends Command {
	
	private Intake intake = Robot.intake;
	
	private static final double SHOOT_SPEED = 1;
	private static final double SHOOT_TIME = 2;
	
	private Timer time = new Timer();

    public ShootIntake() {
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.setIntake(-SHOOT_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return time.hasPeriodPassed(SHOOT_TIME);
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setHolding(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
