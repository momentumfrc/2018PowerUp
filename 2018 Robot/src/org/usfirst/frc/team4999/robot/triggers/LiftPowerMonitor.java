package org.usfirst.frc.team4999.robot.triggers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class LiftPowerMonitor extends Trigger {
	
	PowerDistributionPanel pdp = RobotMap.pdp;
	
	private int motor1 = RobotMap.liftMotor1PDP;
	private int motor2 = RobotMap.liftMotor2PDP;
	
	private int cutoffAmp = 35;
	private int cutoffMs = 500;
	
	Timer timer;
	
	public LiftPowerMonitor() {
		super();
		timer = new Timer();
		timer.start();
	}

    public boolean get() {
        if(pdp.getCurrent(motor1) >= cutoffAmp || pdp.getCurrent(motor2) >= cutoffAmp ) {
        	if(timer.hasPeriodPassed(cutoffMs / 1000.0)) {
        		return true;
        	}
        } else {
        	timer.reset();
        }
    	return false;
    }
}
