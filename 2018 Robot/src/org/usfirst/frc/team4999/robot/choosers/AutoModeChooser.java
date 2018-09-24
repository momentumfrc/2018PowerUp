package org.usfirst.frc.team4999.robot.choosers;

import org.usfirst.frc.team4999.commands.autonomous.AutoMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoModeChooser extends SendableChooser<AutoMode> {
	
	public static final String NAME = "Auto Mode Chooser";
	
	public AutoModeChooser() {
		addDefault("Switch", AutoMode.SWITCH);
		addObject("Switch RIGHTSIDE", AutoMode.RIGHTSIDE);
		addObject("Switch LEFTSIDE", AutoMode.LEFTSIDE);		
		addObject("Scale", AutoMode.SCALE);
		addObject("Fallback Distance", AutoMode.FALLBACK_DISTANCE);
		addObject("Fallback Time", AutoMode.FALLBACK_TIME);
		addObject("Fallback Time Shoot", AutoMode.FALLBACK_TIME_SHOOT);
		SmartDashboard.putData(NAME, this);
	}
}
