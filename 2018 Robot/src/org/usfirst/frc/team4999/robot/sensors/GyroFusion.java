package org.usfirst.frc.team4999.robot.sensors;

import org.usfirst.frc.team4999.robot.MoPrefs;
import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class GyroFusion implements PIDSource {
	VMXPi vmx = RobotMap.pi;
	ADIS16448_IMU adis = RobotMap.adis;
	
	private double adisOffset = 0;

	private PIDSourceType pidType = PIDSourceType.kRate;
	
	private int maxDelay = MoPrefs.getMaxPiDelay();
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}
	
	public double getAngle() {
		if(vmx.getTimeSinceLastPacket() > maxDelay) {
			return adis.getAngleZ() + adisOffset;
		}
		adisOffset = vmx.getAngle() - adis.getAngleZ();
		return vmx.getAngle();
	}
	
	public double getRate() {
		if(vmx.getTimeSinceLastPacket() > maxDelay) {
			return adis.getRateZ();
		}
		return vmx.getRate();
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
