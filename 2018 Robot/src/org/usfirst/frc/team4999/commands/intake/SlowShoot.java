package org.usfirst.frc.team4999.commands.intake;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SlowShoot extends Command {

	Intake intake = Robot.intake;
	
	private static final double INTAKE_TIME = 1;
	
	Timer time = new Timer();

    public SlowShoot() {
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time.start();
    	time.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.shoot();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return time.hasPeriodPassed(INTAKE_TIME);
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setDefaultCommand(new StopIntake());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("interrupted shoot");
    	end();
    }
}
