package org.usfirst.frc.team4999.robot.sensors;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class GyroFusion implements PIDSource {
	VMXPi vmx = RobotMap.pi;
	ADIS16448_IMU adis = RobotMap.adis;
	
	private double adisYawOffset = 0, adisPitchOffset = 0, adisRollOffset = 0;
	private double vmxYawOffset = 0, vmxPitchOffset = 0, vmxRollOffset = 0;

	private PIDSourceType pidType = PIDSourceType.kRate;
	
	private int maxDelay = MoPrefs.getMaxPiDelay();
	
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
		if(vmx.getTimeSinceLastPacket() > maxDelay) { // If no connection w/ vmx, use adis
			firstTimeBack = true;
			return adisAngle;
		} else if(firstTimeBack) { // If just reestablished connection, calculate vmx's offsets and use adis
			firstTimeBack = false;
			vmxYawOffset = adis.getAngleZ() + adisYawOffset - vmx.getAngle();
			vmxPitchOffset = adis.getAngleX() + adisPitchOffset - vmx.getPitch();
			vmxRollOffset = adis.getAngleY() + adisRollOffset - vmx.getRoll();
			return adisAngle;
		} else { // Normally calculate adis' offsets and use vmx
			adisYawOffset = vmx.getAngle() + vmxYawOffset - adis.getAngleZ();
			adisPitchOffset = vmx.getPitch() + vmxYawOffset - adis.getAngleX();
			adisRollOffset = vmx.getRoll() + vmxRollOffset - adis.getAngleY();
			return vmxAngle;
		}
	}
	
	public double getAngle() {
		double vmxAngle = vmx.getAngle() + vmxYawOffset;
		double adisAngle = adis.getAngleZ() + adisYawOffset;
		
		return whichSensor(adisAngle, vmxAngle);
	}
	
	public double getRate() {
		if(vmx.getTimeSinceLastPacket() > maxDelay) {
			return adis.getRateZ();
		}
		return vmx.getRate();
	}
	
	// TODO: Make sure the adis' yaw axis matches the pi's yaw axis, and adjust if necessary
	
	public double getPitch() {
		double vmxAngle = vmx.getPitch() + vmxPitchOffset;
		double adisAngle = adis.getAngleX() + adisPitchOffset;
		
		return whichSensor(adisAngle, vmxAngle);
	}
	
	public double getRoll() {
		double vmxAngle = vmx.getRoll() + vmxRollOffset;
		double adisAngle = adis.getAngleZ() + adisRollOffset;
		
		return whichSensor(adisAngle, vmxAngle);
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
