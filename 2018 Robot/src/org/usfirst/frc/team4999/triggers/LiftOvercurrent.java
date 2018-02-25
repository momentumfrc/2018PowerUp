package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class LiftOvercurrent extends Trigger {
	
	private static final double CUTOFF_CURRENT = 30;
	private static final double CUTOFF_TIME = 1;
	
	private PowerDistributionPanel pdp = RobotMap.pdp;
	
	private Timer time = new Timer();
	
	public LiftOvercurrent() {
		super();
		time.start();
	}

    public boolean get() {
    	if(pdp.getCurrent(RobotMap.LIFT_MOTOR1_PDP) > CUTOFF_CURRENT || pdp.getCurrent(RobotMap.LIFT_MOTOR2_PDP) > CUTOFF_CURRENT) {
    		return time.hasPeriodPassed(CUTOFF_TIME);
    	}
        time.reset();
        return false;
    }
}
