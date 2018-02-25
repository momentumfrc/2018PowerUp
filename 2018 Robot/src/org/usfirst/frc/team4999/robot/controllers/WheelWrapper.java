package org.usfirst.frc.team4999.robot.controllers;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class WheelWrapper extends DriveController {
	
	private Joystick wheel = RobotMap.wheel;
	
	private static final double[] Gears = {0.2, 0.4, 0.6, 0.8, 1};
	private int currentGear = 2;
	
	private int currentPos = 0;
	private boolean povHeld = false;
	
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
	public double getLiftPosition() {
		double[] values = LiftPosition.values();
		int pov = wheel.getPOV();
		if(pov == -1) {
			povHeld = false;
		} else if(!povHeld) {
			povHeld = true;
			if(pov == 0 && currentPos < values.length-1) {
				currentPos++;
			} else if(pov == 180 && currentPos > 0) {
				currentPos--;
			}
		}
		return values[currentPos];
	}

	@Override
	public boolean getReverseDirection() {
		return wheel.getRawButtonPressed(12);
	}

	@Override
	public boolean getKillPID() {
		return wheel.getRawButtonPressed(13);
	}

	@Override
	public boolean getIntake() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getArms() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getClaw() {
		// TODO Auto-generated method stub
		return 0;
	}

}
