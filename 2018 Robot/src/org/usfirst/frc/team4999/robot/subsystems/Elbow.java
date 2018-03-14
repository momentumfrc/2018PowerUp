package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;
import org.usfirst.frc.team4999.utils.PDPWrapper;
import org.usfirst.frc.team4999.utils.PIDFactory;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elbow extends Subsystem {
	
	private static final int MIN_POS = 0;
	
	private static final double ZERO_SPEED = 0.2;
	private static final double ZERO_CUTOFF_CURRENT = 10; // amperes
	private static final int ZERO_CUTOFF_TIME = 500; // ms
	
	private PDPWrapper pdp = new PDPWrapper();

    public MomentumPID pid;
    private Spark elbow = RobotMap.elbow;
    private Encoder encoder = RobotMap.elbowEncoder;
    
    public Elbow() {
    	pid = PIDFactory.getElbowPID();
    	addChild(pid);
    }
    
    /**
     * Set power to elbow motor. Positive is down (away from lift), negative is up (towards lift).
     * @param speed
     */
    public void setElbowMotor(double speed) {
    	if(speed > 0 && encoder.get() >= MoPrefs.getMaxElbowRotation()) {
    		System.out.println("Elbow at or past maximum rotation, can only retract");
    		elbow.set(0);
    	} else if(speed < 0 && encoder.get() <= MIN_POS) {
    		System.out.println("Elbow at or past minimum rotation, can only extend");
    		elbow.set(0);
    	} else {
    		elbow.set(speed);
    	}
    }
    
    public void drivePID() {
    	setElbowMotor(pid.get());
    }
    
    public void retract() {
    	pid.setSetpoint(0);
    }
    public void extend() {
    	pid.setSetpoint(MoPrefs.getMaxElbowRotation());
    }
    public boolean zero() {
    	if(pdp.checkOvercurrent(RobotMap.ELBOW_PDP, ZERO_CUTOFF_CURRENT, ZERO_CUTOFF_TIME)) {
    		encoder.reset();
    		elbow.set(0);
    		return true;
    	} else {
    		elbow.set(-ZERO_SPEED);
    		return false;
    	}
    }

    public void initDefaultCommand() {
    	// No default command
    }
}

