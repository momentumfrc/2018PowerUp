package org.usfirst.frc.team4999.robot.choosers;

import org.usfirst.frc.team4999.commands.autonomous.paths.StartPosition;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StartPosChooser extends SendableChooser<StartPosition> {
	private static final String NAME = "Start Position Chooser";
	
	public StartPosChooser() {
		super();
		addObject("Left", StartPosition.LEFT);
		addDefault("Middle", StartPosition.MIDDLE);
		addObject("Right", StartPosition.RIGHT);
		SmartDashboard.putData(NAME, this);
	}
	
}
