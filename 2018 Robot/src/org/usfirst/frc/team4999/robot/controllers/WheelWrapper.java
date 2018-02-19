package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class WheelWrapper extends DriveController {
	
	private Joystick wheel = RobotMap.wheel;
	
	private static final double[] Gears = {0.2, 0.4, 0.6, 0.8, 1};
	private int currentGear = 2;
	
	private int liftSpeed = 1;

	@Override
	public double getMoveRequest() {
		double mr = -wheel.getRawAxis(2);
		return map(mr, -1, 1, 0, 1);
	}

	@Override
	public double getTurnRequest() {
		return wheel.getRawAxis(0);
	}

	@Override
	public double getSpeedLimiter() {
		if(wheel.getRawButtonPressed(6) && currentGear < Gears.length-1)
			currentGear++;
		if(wheel.getRawButtonPressed(5) && currentGear > 0)
			currentGear--;
		return Gears[currentGear];
	}

	@Override
	public double getLiftSpeed() {
		if(wheel.getPOV() == 0)
			return liftSpeed;
		else if(wheel.getPOV() == 180)
			return -liftSpeed;
		else
			return 0;
	}

	@Override
	public boolean getReverseDirection() {
		return wheel.getRawButtonPressed(12);
	}

	@Override
	public boolean getKillPID() {
		return wheel.getRawButtonPressed(13);
	}

}
