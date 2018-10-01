package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.PDPWrapper;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DriveOvercurrent extends Trigger {
	
	private int[] MOTORS = {RobotMap.RF_DRIVE_MOTOR_PDP, RobotMap.RB_DRIVE_MOTOR_PDP, RobotMap.LF_DRIVE_MOTOR_PDP, RobotMap.LB_DRIVE_MOTOR_PDP};
	
	public static final double DRIVE_CUTOFF_CURRENT = 30;
	public static final int DRIVE_CUTOFF_TIME = 1000;
	
	
	private PDPWrapper pdp = new PDPWrapper();
	
	public DriveOvercurrent() {
		super();
	}

    public boolean get() {
    	return pdp.checkOvercurrent(MOTORS, DRIVE_CUTOFF_CURRENT, DRIVE_CUTOFF_TIME) /*|| RobotController.isBrownedOut()*/;
    }
    
}
