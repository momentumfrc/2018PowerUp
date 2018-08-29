package org.usfirst.frc.team4999.commands.elbow;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.Elbow;
import org.usfirst.frc.team4999.utils.PDPWrapper;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroElbow extends Command {
	
	private static final double TIMEOUT = 5; // seconds
	
	private static final double ZERO_SPEED = 0.5;
	private static final double ZERO_CUTOFF_CURRENT = 2.7; // amperes
	private static final int ZERO_CUTOFF_TIME = 1000; // ms
	
	private Elbow elbow = Robot.elbow;
	
	private PDPWrapper pdp = new PDPWrapper();
	
	private Timer timeout = new Timer();

    public ZeroElbow() {
    	requires(Robot.elbow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("ZEROING!!");
    	timeout.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elbow.setElbowNoLimits(-ZERO_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pdp.checkOvercurrent(RobotMap.ELBOW_PDP, ZERO_CUTOFF_CURRENT, ZERO_CUTOFF_TIME) || timeout.hasPeriodPassed(TIMEOUT);
    }

    // Called once after isFinished returns true
    protected void end() {
    	elbow.reset();
		elbow.setElbowNoLimits(0);
    	Robot.elbow.retract();
    	Robot.elbow.setDefaultCommand(new MaintainElbowPosition());
    	System.out.println("Zeroed");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	elbow.setElbowNoLimits(0);
    }
}
