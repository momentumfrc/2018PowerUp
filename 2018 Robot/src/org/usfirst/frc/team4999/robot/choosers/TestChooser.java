package org.usfirst.frc.team4999.robot.choosers;

import org.usfirst.frc.team4999.robot.commands.pixy.LogObjects;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class TestChooser extends SendableChooser<Command> {
	
	private Command logObjects = new LogObjects();
	
	public TestChooser() {
		super();
		
		addDefault("Log Pixy Objects", logObjects);
		
	}
	
	public void stopCommands() {
		logObjects.cancel();
	}
}
