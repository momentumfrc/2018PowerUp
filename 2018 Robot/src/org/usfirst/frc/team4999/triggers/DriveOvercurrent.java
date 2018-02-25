package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DriveOvercurrent extends Trigger {
	
	private int[] MOTORS = {RobotMap.RF_DRIVE_MOTOR_PDP, RobotMap.RB_DRIVE_MOTOR_PDP, RobotMap.LF_DRIVE_MOTOR_PDP, RobotMap.LB_DRIVE_MOTOR_PDP};
	
	private static final double CUTOFF_CURRENT = 30;
	private static final double CUTOFF_TIME = 1;
	
	private PowerDistributionPanel pdp = RobotMap.pdp;
	
	private Timer time = new Timer();
	
	public DriveOvercurrent() {
		super();
		time.start();
	}

    public boolean get() {
    	// Return true if any of the motors is above the cutoff current for the cutoff time
        for(int i : MOTORS) { // for each motor
        	if(pdp.getCurrent(i) > CUTOFF_CURRENT) { // if its above the cutoff current
        		return time.hasPeriodPassed(CUTOFF_TIME); // return true if any of the motors has been above the cutoff current for the cutoff time
        	}
        }
        time.reset();
        return false;
    }
}
