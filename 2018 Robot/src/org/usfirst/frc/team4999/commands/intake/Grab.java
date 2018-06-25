package org.usfirst.frc.team4999.commands.intake;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.Intake;
import org.usfirst.frc.team4999.utils.PDPWrapper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Grab extends Command {
	
	Intake intake = Robot.intake;
	PDPWrapper currentChecker = new PDPWrapper();

	private static final double CUTOFF_CURRENT = 7.5;
	private static final int CUTOFF_TIME = 100;

    public Grab() {
       requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.grab();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return currentChecker.checkOvercurrent(new int[] {RobotMap.LEFT_INTAKE_PDP, RobotMap.RIGHT_INTAKE_PDP}, CUTOFF_CURRENT, CUTOFF_TIME);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
