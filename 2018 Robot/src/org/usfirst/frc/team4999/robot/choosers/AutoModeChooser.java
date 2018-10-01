package org.usfirst.frc.team4999.robot.choosers;

import org.usfirst.frc.team4999.commands.autonomous.AutoMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoModeChooser extends SendableChooser<AutoMode> {
	
	public static final String NAME = "Auto Mode Chooser";
	
	public AutoModeChooser() {
		addObject("Switch", AutoMode.SWITCH);	
		addObject("Scale", AutoMode.SCALE);
		addObject("switch SIDE", AutoMode.SwitchRIGHTSIDE);
		addObject("Fallback Distance", AutoMode.FALLBACK_DISTANCE);
		addDefault("Fallback Time", AutoMode.FALLBACK_TIME);
		addObject("Fallback Time Shoot", AutoMode.FALLBACK_TIME_SHOOT);
		SmartDashboard.putData(NAME, this);
	}
}
