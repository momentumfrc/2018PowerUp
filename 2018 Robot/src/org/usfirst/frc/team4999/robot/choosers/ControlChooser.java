package org.usfirst.frc.team4999.robot.choosers;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4999.robot.commands.*;
import org.usfirst.frc.team4999.robot.commands.drive.FlightStickDrive;
import org.usfirst.frc.team4999.robot.commands.drive.XboxDrive;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControlChooser extends SendableChooser<Command> {
	
	private final String NAME = "Control Chooser";
	
	public ControlChooser() {
		super();
		addDefault("Flight Stick", new FlightStickDrive());
		addObject("Xbox Controller", new XboxDrive());
		
		SmartDashboard.putData(NAME, this);
		NetworkTableInstance.getDefault().getTable("SmartDashboard").getSubTable(NAME).getEntry("selected").addListener((notification)->{
			if(DriverStation.getInstance().isOperatorControl())
				getSelected().start();
		},TableEntryListener.kUpdate|TableEntryListener.kImmediate);
	}

}
