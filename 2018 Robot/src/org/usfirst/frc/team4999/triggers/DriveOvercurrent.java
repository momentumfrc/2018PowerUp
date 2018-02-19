package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DriveOvercurrent extends Trigger {
	
	private static final int[] MOTORS = {0,1,2,3};
	
	private static final double CUTOFF_CURRENT = 30;
	private static final double CUTOFF_TIME = 1;
	
	private PowerDistributionPanel pdp = RobotMap.pdp;
	
	private Timer time = new Timer();
	
	public DriveOvercurrent() {
		super();
		time.start();
	}

    public boolean get() {
        for(int i : MOTORS) {
        	if(pdp.getCurrent(i) > CUTOFF_CURRENT) {
        		return time.hasPeriodPassed(CUTOFF_TIME);
        	}
        }
        time.reset();
        return false;
    }
}
