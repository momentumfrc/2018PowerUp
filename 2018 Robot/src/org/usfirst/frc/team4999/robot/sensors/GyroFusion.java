package org.usfirst.frc.team4999.robot.sensors;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class GyroFusion implements PIDSource {
	AHRS vmx = RobotMap.vmx;
	ADIS16448_IMU adis = RobotMap.adis;
	
	public static enum Sensor { VMXPI, ADIS };
	
	private Sensor current = Sensor.VMXPI;
	
	private double adisYawOffset = 0, adisPitchOffset = 0, adisRollOffset = 0;
	private double vmxYawOffset = 0, vmxPitchOffset = 0, vmxRollOffset = 0;

	private PIDSourceType pidType = PIDSourceType.kRate;
	
	private boolean firstTimeBack = false;
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}
	
	private double whichSensor(double adisAngle, double vmxAngle) {
		if(!vmx.isConnected()) { // If no connection w/ vmx, use adis
			if(!firstTimeBack)
				System.out.println("VMX not connected, using ADIS");
			firstTimeBack = true;
			
			current = Sensor.ADIS;
			
			return adisAngle;
		} else if(firstTimeBack) { // If just reestablished connection, calculate vmx's offsets and use adis
			System.out.println("Reestablished connection, switching to VMX");
			firstTimeBack = false;
			vmxYawOffset = adis.getAngleZ() + adisYawOffset - vmx.getAngle();
			vmxPitchOffset = adis.getAngleX() + adisPitchOffset - vmx.getPitch();
			vmxRollOffset = adis.getAngleY() + adisRollOffset - vmx.getRoll();
			
			current = Sensor.ADIS;
			
			return adisAngle;
		} else { // Normally calculate adis' offsets and use vmx
			adisYawOffset = vmx.getAngle() + vmxYawOffset - adis.getAngleZ();
			adisPitchOffset = vmx.getPitch() + vmxYawOffset - adis.getAngleX();
			adisRollOffset = vmx.getRoll() + vmxRollOffset - adis.getAngleY();
			
			current = Sensor.VMXPI;
			
			return vmxAngle;
		}
	}
	
	public double getAngle() {
		double vmxAngle = vmx.getAngle() + vmxYawOffset;
		double adisAngle = adis.getAngleZ() + adisYawOffset;
		
		return whichSensor(adisAngle, vmxAngle);
	}
	
	public double getRate() {
		if(!vmx.isConnected()) {
			return adis.getRateZ();
		}
		return vmx.getRate();
	}
	
	// TODO: Make sure the adis' yaw axis matches the pi's yaw axis, and adjust if necessary
	
	public double getPitch() {
		double vmxAngle = vmx.getPitch() + vmxPitchOffset;
		double adisAngle = adis.getAngleY() + adisPitchOffset;
		
		return whichSensor(adisAngle, vmxAngle);
	}
	
	public double getRoll() {
		double vmxAngle = vmx.getRoll() + vmxRollOffset;
		double adisAngle = adis.getAngleX() + adisRollOffset;
		
		return whichSensor(adisAngle, vmxAngle);
	}
	
	public Sensor currentSensor() {
		return current;
	}

	@Override
	public double pidGet() {
		if(pidType == PIDSourceType.kDisplacement) {
			return getAngle();
		} else {
			return getRate();
		}
	}

}
