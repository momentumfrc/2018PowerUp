package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.commands.lift.MaintainLiftHeight;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;
import org.usfirst.frc.team4999.utils.PIDFactory;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {
	
	private static final double MIN_HEIGHT = 0.25; // meters
	private static final double MAX_MOTOR_DELTA = 0.05;
	
	private SpeedControllerGroup motors = RobotMap.liftMotors;
	private DoubleSolenoid shifter = RobotMap.liftShifter;
	
	private Encoder encoder = RobotMap.liftEncoder;
	
	private MomentumPID slowLiftPID, fastLiftPID, currentLiftPID;
	
	public Lift() {
		encoder.setDistancePerPulse(1/MoPrefs.getLiftEncTicks());
		
		slowLiftPID = PIDFactory.getSlowLiftPID();
		fastLiftPID = PIDFactory.getFastLiftPID();
		currentLiftPID = slowLiftPID;
		
		addChild(slowLiftPID);
		addChild(fastLiftPID);
		
	}
	
	
	public boolean isHighSpeed() {
		return shifter.get() == DoubleSolenoid.Value.kForward;
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
	
	public double getCurrentHeight( ) {
		return encoder.getDistance();
	}
	
	public boolean isOnTarget() {
		return currentLiftPID.onTargetForTime();
	}
	
	public void set(double power) {
		// Anti-jerk: Clip the difference between the requested power and the current power
		double delta = power - motors.get();
		delta = Utils.clip(delta, -MAX_MOTOR_DELTA, MAX_MOTOR_DELTA);
		double c_power = motors.get() + delta;
		
		// Hard endstops: don't let the motors go above a max and min height
		if(c_power < 0 && encoder.getDistance() <= MIN_HEIGHT)
			motors.set(0);
		else if(c_power > 0 && encoder.getDistance() >= MoPrefs.getMaxLiftHeight())
			motors.set(0);
		else
			motors.set(c_power);
	}
	public void setHeight(double height) {
		double clippedHeight = Utils.clip(height, MIN_HEIGHT, MoPrefs.getMaxLiftHeight());
		if(isHighSpeed())
			fastLiftPID.setSetpoint(clippedHeight);
		else
			slowLiftPID.setSetpoint(clippedHeight);
	}
	
	public void disablePID() {
		fastLiftPID.disable();
		slowLiftPID.disable();
	}
	
	public void enablePID() {
		currentLiftPID.enable();
	}
	
	public void setHome() {
		encoder.reset();
	}

    public void initDefaultCommand() {
        setDefaultCommand(new MaintainLiftHeight());
    }
    
}

