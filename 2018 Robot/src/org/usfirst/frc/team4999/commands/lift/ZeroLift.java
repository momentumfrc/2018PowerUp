package org.usfirst.frc.team4999.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroLift extends Command {
	
	private Lift lift = Robot.lift;
	
	private static final double ZERO_SPEED = 0.2;
	
	private static final double TIMEOUT = 5;
	
	private static final double CUTOFF_CURRENT = 30;
	private static final double CUTOFF_TIME = 0.5;
	
	private PowerDistributionPanel pdp = RobotMap.pdp;
	
	private Timer timeout = new Timer();
	private Timer overCurrent = new Timer();

    public ZeroLift() {
        requires(lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lift.shiftLow();
    	lift.releaseBrake();
    	timeout.start();
    	overCurrent.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lift.set(-ZERO_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(pdp.getCurrent(RobotMap.LIFT_MOTOR1_PDP) > CUTOFF_CURRENT || pdp.getCurrent(RobotMap.LIFT_MOTOR2_PDP) > CUTOFF_CURRENT) {
    		if(overCurrent.hasPeriodPassed(CUTOFF_TIME)) {
    			return true;
    		}
    	} else {
    		overCurrent.reset();
    	}
        return RobotMap.liftZeroSwitch.get() || timeout.hasPeriodPassed(TIMEOUT);
    }

    // Called once after isFinished returns true
    protected void end() {
    	lift.set(0);
    	lift.setHome();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
