package org.usfirst.frc.team4999.robot.sensors;

import org.usfirst.frc.team4999.robot.RobotMap;

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
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}
	
	
	private void setCurrentSensor() {
		if(!vmx.isConnected()) {
			if(current == Sensor.VMXPI) {
				System.out.println("VMX not connected, using ADIS");
			}
			current = Sensor.ADIS;
		} else {
			if(current == Sensor.ADIS) {
				System.out.println("VMX connected");
				calculateOffsets(Sensor.VMXPI);
			}
			current = Sensor.VMXPI;
		}
	}
	
	private void calculateOffsets(Sensor sensor) {
		switch(sensor) {
		case VMXPI:
			vmxYawOffset = adis.getAngleZ() + adisYawOffset - vmx.getAngle();
			vmxPitchOffset = adis.getAngleX() + adisPitchOffset - vmx.getPitch();
			vmxRollOffset = adis.getAngleY() + adisRollOffset - vmx.getRoll();
			break;
		case ADIS:
			adisYawOffset = vmx.getAngle() + vmxYawOffset - adis.getAngleZ();
			adisPitchOffset = vmx.getPitch() + vmxYawOffset - adis.getAngleX();
			adisRollOffset = vmx.getRoll() + vmxRollOffset - adis.getAngleY();
			break;
		}
	}
	
	private double whichValue(double vmx, double adis) {
		switch(currentSensor()) {
		case ADIS:
			return adis;
		case VMXPI:
		default:
			return vmx;
		}
	}
	
	public double getAngle() {
		double vmxAngle = vmx.getAngle() + vmxYawOffset;
		double adisAngle = adis.getAngleZ() + adisYawOffset;
		
		setCurrentSensor();
		calculateOffsets(Sensor.ADIS);
		
		return whichValue(vmxAngle, adisAngle);
	}
	
	public double getRate() {
		double vmxRate = vmx.getRate();
		double adisRate = adis.getRateZ();
		
		setCurrentSensor();
		
		return whichValue(vmxRate, adisRate);
	}
	
	// TODO: Make sure the adis' yaw axis matches the pi's yaw axis, and adjust if necessary
	
	public double getPitch() {
		double vmxAngle = vmx.getPitch() + vmxPitchOffset;
		double adisAngle = adis.getAngleY() + adisPitchOffset;
		
		setCurrentSensor();
		calculateOffsets(Sensor.ADIS);
		return whichValue(vmxAngle, adisAngle);
	}
	
	public double getRoll() {
		double vmxAngle = vmx.getRoll() + vmxRollOffset;
		double adisAngle = adis.getAngleX() + adisRollOffset;
		
		setCurrentSensor();
		calculateOffsets(Sensor.ADIS);
		return whichValue(vmxAngle, adisAngle);
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
