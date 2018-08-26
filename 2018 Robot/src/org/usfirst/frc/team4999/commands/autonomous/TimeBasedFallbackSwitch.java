package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.intake.Hold;
import org.usfirst.frc.team4999.commands.intake.Shoot;
import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.DriveSystem;
import org.usfirst.frc.team4999.utils.MoPrefs;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TimeBasedFallbackSwitch extends Command {
	
	private DriveSystem drive = Robot.driveSystem;
	Timer time = new Timer();
	
    public TimeBasedFallbackSwitch() {
    	requires(drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time.start();
    	time.reset();
    	new Hold().start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.arcadeDrive(-1, .15, MoPrefs.getAutoSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return time.hasPeriodPassed(MoPrefs.getFallbackAutoTime());
    }

    // Called once after isFinished returns true
    protected void end() {
    	drive.stop();
    	CommandGroup group = new CommandGroup();
    	group.addSequential(new SetElbowPosition(MoPrefs.getSwitchElbowAngle()));
    	group.addSequential(new Shoot());
    	group.start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
