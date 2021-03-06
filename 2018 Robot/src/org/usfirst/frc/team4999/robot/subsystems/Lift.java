package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.commands.lift.MaintainLiftHeight;
import org.usfirst.frc.team4999.commands.lift.ManualLift;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.pid.MomentumPID;
import org.usfirst.frc.team4999.utils.PIDFactory;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lift extends Subsystem {
	
	private static final int MIN_HEIGHT = 100; // ticks //TODO maybe change maybe dont
	private static final double MAX_MOTOR_DELTA = 0.02;
	
	private SpeedControllerGroup motors = RobotMap.liftMotors;
	private DoubleSolenoid shifter = RobotMap.liftShifter;
	
	private Encoder encoder = RobotMap.liftEncoder;
	
	private MomentumPID slowLiftPID, fastLiftPID;
	public MomentumPID currentLiftPID;
	
	public Lift() {
		encoder.setDistancePerPulse(1/MoPrefs.getLiftEncTicks());
		
		slowLiftPID = PIDFactory.getSlowLiftPID();
		fastLiftPID = PIDFactory.getFastLiftPID();
		
		currentLiftPID = fastLiftPID;
		
		
		addChild(slowLiftPID);
		addChild(fastLiftPID);
		
	}
	
	
	public boolean isHighSpeed() {
		return shifter.get() == DoubleSolenoid.Value.kForward;
	}
	public boolean isHighSpeedPID() {
		return currentLiftPID == fastLiftPID;
	}
	public void shiftHigh() {
		currentLiftPID = fastLiftPID;
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	public void shiftLow() {
		currentLiftPID = slowLiftPID;
		shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void driveLiftPID() {
		if(currentLiftPID.isEnabled())
			set(currentLiftPID.get());
		else
			set(0);
	}
	
	public double getCurrentHeight() {
		return encoder.getDistance();
	}
	
	public boolean isOnTarget() {
		return currentLiftPID.onTargetForTime();
	}
	
	public void set(double power) {
		// Anti-jerk: Clip the difference between the requested power and the current power
		double delta = power - motors.get();
		//System.out.format("Clipping %.2f to within +/- %.2f\n", delta, MAX_MOTOR_DELTA);
		delta = Utils.clip(delta, -MAX_MOTOR_DELTA, MAX_MOTOR_DELTA);
		double c_power = motors.get() + delta;
		
		// Hard endstops: don't let the motors go above a max and min height
		if(c_power < 0 && encoder.get() <= MIN_HEIGHT) {
			System.out.format("Lift at or less than minimum height (%d)\n", encoder.get());
			motors.set(0);
		} else if(c_power > 0 && encoder.getDistance() >= MoPrefs.getMaxLiftHeight()) {
			System.out.format("Lift at or greater than maximum height(%.2f >= %.2f)\n", encoder.getDistance(), MoPrefs.getMaxLiftHeight());
			motors.set(0);
		} else {
			motors.set(c_power);
		}
	}
	public void setNoLimit(double power) {
		// Anti-jerk: Clip the difference between the requested power and the current power
		double delta = power - motors.get();
		//System.out.format("Clipping %.2f to within +/- %.2f\n", delta, MAX_MOTOR_DELTA);
		delta = Utils.clip(delta, -MAX_MOTOR_DELTA, MAX_MOTOR_DELTA);
		double c_power = motors.get() + delta;
		motors.set(c_power);
	}
	
	public void kill() {
		motors.set(0);
	}
	
	public void setHeight(double height) {
		double clippedHeight = Utils.clip(height, MIN_HEIGHT, MoPrefs.getMaxLiftHeight());
		currentLiftPID.setSetpoint(clippedHeight);
	}
	
	public void disablePID() {
		fastLiftPID.disable();
		slowLiftPID.disable();
	}
	
	public void enablePID() {
		currentLiftPID.enable();
	}
	
	public void zero() {
		encoder.reset();
	}

    public void initDefaultCommand() {
        setDefaultCommand(new ManualLift());
    }
    
    public void liftDashboard() {
    	SmartDashboard.putBoolean("Lift Fast", isHighSpeed());
    }
}

