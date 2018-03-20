package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.PDPWrapper;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DriveOvercurrent extends Trigger {
	
	private int[] MOTORS = {RobotMap.RF_DRIVE_MOTOR_PDP, RobotMap.RB_DRIVE_MOTOR_PDP, RobotMap.LF_DRIVE_MOTOR_PDP, RobotMap.LB_DRIVE_MOTOR_PDP};
	
	
	
	private PDPWrapper pdp = new PDPWrapper();
	
	private Timer time = new Timer();
	
	public DriveOvercurrent() {
		super();
		time.start();
	}

    public boolean get() {
    	return pdp.checkOvercurrent(MOTORS, RobotMap.DRIVE_CUTOFF_CURRENT, RobotMap.DRIVE_CUTOFF_TIME) && DriverStation.getInstance().isAutonomous();
    }
    
    
}
