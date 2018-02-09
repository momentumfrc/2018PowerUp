package org.usfirst.frc.team4999.robot.sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class GyroFusion implements PIDSource {
	VMXPi vmx = VMXPi.getInstance();
	ADIS16448_IMU adis = new ADIS16448_IMU();
	private PIDSourceType pidType = PIDSourceType.kRate;
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}

	@Override
	public double pidGet() {
		if(pidType == pidType.kDisplacement) {
			 if(vmx.getTimeSinceLastPacket() > VMXPi.RESPONSECUTOFF) {
					return adis.getAngleZ();
				 }
				 return vmx.getAngle();
		}else {
			 if(vmx.getTimeSinceLastPacket() > VMXPi.RESPONSECUTOFF) {
					return adis.getRateZ();
				 }
				 return vmx.getRate();
		}
	}

}
