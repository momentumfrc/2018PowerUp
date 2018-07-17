package org.usfirst.frc.team4999.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.Lift;
import org.usfirst.frc.team4999.utils.PDPWrapper;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroLift extends Command {
	
	private Lift lift = Robot.lift;
	
	private static final double ZERO_SPEED = 0.2;
	
	private static final double TIMEOUT = 5; // seconds
	
	private static final double CUTOFF_CURRENT = 10; // amperes
	private static final int CUTOFF_TIME = 500; // ms
	
	int[] liftmotors = {RobotMap.LIFT_MOTOR1_PDP,  RobotMap.LIFT_MOTOR2_PDP};
	
	private PDPWrapper pdp = new PDPWrapper();
	
	private Timer timeout = new Timer();

    public ZeroLift() {
        requires(lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lift.shiftLow();
    	timeout.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lift.setNoLimit(-ZERO_SPEED);
    	System.out.format("Current1:%.2f Currrent2:%.2f\n", RobotMap.pdp.getCurrent(RobotMap.LIFT_MOTOR1_PDP), RobotMap.pdp.getCurrent(RobotMap.LIFT_MOTOR2_PDP));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pdp.checkOvercurrent(liftmotors, CUTOFF_CURRENT, CUTOFF_TIME) || RobotMap.liftZeroSwitch.get() || timeout.hasPeriodPassed(TIMEOUT);
    }

    // Called once after isFinished returns true
    protected void end() {
    	lift.set(0);
    	lift.zero();
    	System.out.println("Lift Zeroed");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	lift.set(0);
    }
}
