package org.usfirst.frc.team4999.robot.choosers;

import org.usfirst.frc.team4999.commands.autonomous.paths.TargetPosition;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TargetChooser extends SendableChooser<TargetPosition>{
	public static final String NAME = "Target Chooser";

	public TargetChooser() {
		super();
		addDefault("Target Left", TargetPosition.LEFT);
		addObject("Target Right", TargetPosition.RIGHT);
		SmartDashboard.putData(NAME, this);
		}
}
