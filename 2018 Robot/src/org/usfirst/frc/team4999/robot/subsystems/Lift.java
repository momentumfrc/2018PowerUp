package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.commands.lift.MaintainLiftHeight;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;
import org.usfirst.frc.team4999.utils.PIDFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {
	
	private static final double MIN_HEIGHT = 0;
	private static final double MAX_HEIGHT = 2; // meters
	
	private SpeedControllerGroup motors = RobotMap.liftMotors;
	private DoubleSolenoid shifter = RobotMap.liftShifter;
	private DoubleSolenoid brake = RobotMap.liftBrake;
	
	private Encoder encoder = RobotMap.liftEncoder;
	
	private MomentumPID slowLiftPID, fastLiftPID;
	
	public Lift() {
		encoder.setDistancePerPulse(1/MoPrefs.getLiftEncTicks());
		
		slowLiftPID = PIDFactory.getSlowLiftPID();
		fastLiftPID = PIDFactory.getFastLiftPID();
		
	}
	
	public boolean isBraked() {
		return brake.get() == DoubleSolenoid.Value.kForward;
	}
	
	public void brake() {
		brake.set(DoubleSolenoid.Value.kForward);
	}
	public void releaseBrake() {
		brake.set(DoubleSolenoid.Value.kReverse);
	}
	
	public boolean isHighSpeed() {
		return shifter.get() == DoubleSolenoid.Value.kForward;
	}
	public void shiftHigh() {
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	public void shiftLow() {
		shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void driveLiftPID() {
		if(isHighSpeed())
			driveFastLiftPID();
		else
			driveSlowLiftPID();
	}
	
	public double getCurrentHeight( ) {
		return encoder.getDistance();
	}
	
	public boolean isOnTarget() {
		if(isHighSpeed())
			return fastLiftPID.onTargetForTime();
		else
			return slowLiftPID.onTargetForTime();
	}
	
	public void driveSlowLiftPID() {
		if(slowLiftPID.isEnabled() && !isBraked())
			motors.set(slowLiftPID.get());
		else
			motors.set(0);
	}
	public void driveFastLiftPID() {
		if(fastLiftPID.isEnabled() && !isBraked())
			motors.set(fastLiftPID.get());
		else
			motors.set(0);
	}
	
	public void set(double power) {
		if(power < 0 && encoder.getDistance() <= MIN_HEIGHT)
			motors.set(0);
		else if(power < 0 && encoder.getDistance() >= MAX_HEIGHT)
			motors.set(0);
		else
			motors.set(power);
	}
	public void setHeight(double height) {
		double clippedHeight = clip(height, MIN_HEIGHT, MAX_HEIGHT);
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
		if(isHighSpeed())
			fastLiftPID.enable();
		else
			slowLiftPID.enable();
	}

    public void initDefaultCommand() {
        setDefaultCommand(new MaintainLiftHeight());
    }
    
    private double clip(double value, double min, double max) {
    	return Math.min(Math.max(value, min), max);
    }
}

