package org.usfirst.frc.team4999.robot.choosers;

import org.usfirst.frc.team4999.robot.controllers.*;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControlChooser extends SendableChooser<DriveController> {
	
	private final String NAME = "Control Chooser";
	
	public ControlChooser() {
		super();
		addObject("Flight Stick", new FlightStickWrapper());
		addObject("Xbox Controller", new XboxWrapper());
		addObject("Logitech F310", new F310Wrapper());
		addDefault("Xbox drive, F310 intake", new XboxF310Wrapper());
		
		SmartDashboard.putData(NAME, this);
	}

}
