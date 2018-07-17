package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;
import org.usfirst.frc.team4999.utils.PIDFactory;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elbow extends Subsystem {
	
	private static final int MIN_POS = 150;

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
    		System.out.format("Elbow at or past maximum rotation (%d), can only retract\n",encoder.get());
    		elbow.set(0);
    	} else if(speed < 0 && encoder.get() <= MIN_POS) {
    		System.out.format("Elbow at or past minimum rotation(%d), can only extend\n", encoder.get());
    		elbow.set(0);
    	} else {
    		elbow.set(speed);
    	}
    }
    
    public void setElbowNoLimits(double speed) {
    	elbow.set(speed);
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
    public void reset() {
    	encoder.reset();
    }

    public void initDefaultCommand() {
    	// No default command
    }
}

