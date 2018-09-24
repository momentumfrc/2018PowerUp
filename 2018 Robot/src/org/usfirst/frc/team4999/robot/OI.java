/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4999.robot;

import org.usfirst.frc.team4999.commands.*;
import org.usfirst.frc.team4999.commands.elbow.*;
import org.usfirst.frc.team4999.commands.intake.*;
import org.usfirst.frc.team4999.commands.lift.*;
import org.usfirst.frc.team4999.triggers.*;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	Trigger failsafeDrive = new BooleanTrigger(()->{return Robot.controlChooser.getSelected().getFailsafeDrive();});
	Trigger failsafeElbow = new BooleanTrigger(()->{return Robot.controlChooser.getSelected().getFailsafeElbow();});
	Trigger failsafeLift = new BooleanTrigger(()->{return Robot.controlChooser.getSelected().getFailsafeLift();});
	Trigger zerolift = new BooleanTrigger(()->{return Robot.controlChooser.getSelected().getZeroLift();});
	
	Trigger brownout = new BooleanTrigger(()->{return RobotController.isBrownedOut();});
	
	Trigger hunt = new BooleanTrigger(()->{return Robot.controlChooser.getSelected().getIntake();});
	Trigger shoot = new BooleanTrigger(()->{return Robot.controlChooser.getSelected().getShoot();});
	
	Trigger driveOvercurrent = new DriveOvercurrent();
	Trigger liftOvercurrent = new LiftOvercurrent();
	
	
	public OI() {
		failsafeDrive.whenActive(new DriveNoPID());
		
		failsafeElbow.whenActive(new ElbowNoLimit());
		
		failsafeLift.whenActive(new ManualLiftNoLimit());
		
		zerolift.whenActive(new ZeroAndManualLift());
		
		driveOvercurrent.whenActive(new InstantCommand() {
			protected void initialize() {
				Robot.controlChooser.getSelected().vibrate(1);
				Robot.lights.pushAnimation("Pinning", Robot.lights.blinkPurple);
			}
		});
		driveOvercurrent.whenInactive(new InstantCommand() {
			protected void initialize() {
				Robot.controlChooser.getSelected().vibrate(0);
				Robot.lights.popAnimation("Pinning");
			}
		});
		
		
		liftOvercurrent.whenActive(new KillLift());
		
		hunt.whenActive(new IntakeOpen());
		hunt.whenInactive(new GrabAndHold());
		
		shoot.whenActive(new Shoot());
		
		brownout.whenActive(new InstantCommand() {
		    protected void initialize() {
		    	Robot.lights.pushAnimation("Battery", Robot.lights.blinkRed);
		    }
		});
		
	}
	
}
