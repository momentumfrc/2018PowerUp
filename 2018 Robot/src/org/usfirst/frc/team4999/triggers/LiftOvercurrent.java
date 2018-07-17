package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.PDPWrapper;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class LiftOvercurrent extends Trigger {
	
	private static final double CUTOFF_CURRENT = 20;
	private static final int CUTOFF_TIME = 1000;
	
	private PDPWrapper pdp = new PDPWrapper();
	
	public LiftOvercurrent() {
		super();
	}

    public boolean get() {
    	return pdp.checkOvercurrent(new int[] {RobotMap.LIFT_MOTOR1_PDP,  RobotMap.LIFT_MOTOR2_PDP}, CUTOFF_CURRENT, CUTOFF_TIME);
    }
}
