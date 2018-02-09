/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4999.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static VictorSP leftFrontMotor = new VictorSP(2);
	public static VictorSP leftBackMotor = new VictorSP(3);
	public static VictorSP rightFrontMotor = new VictorSP(0);
	public static VictorSP rightBackMotor = new VictorSP(1);
	public static double auto_speed = 0.25;
	public static XboxController xbox = new XboxController(1);
	public static Joystick flightStick = new Joystick(0);
	
	public static Encoder leftDriveEncoder = new Encoder(0,1);
	public static Encoder rightDriveEncoder = new Encoder(2,3);
	
	public static Encoder liftEncoder = new Encoder(4,5);
	public static SpeedControllerGroup liftMotors = new SpeedControllerGroup(new VictorSP(4), new VictorSP(5));
	public static DoubleSolenoid liftShifter = new DoubleSolenoid(0,1);
	public static Spark liftBrake = new Spark(6);
	public static DigitalInput liftZeroSwitch = new DigitalInput(6);
	
	public static NetworkTable pidTable = NetworkTableInstance.getDefault().getTable("PID");
	
	//TODO: Debug pdp
	//public static PowerDistributionPanel pdp = new PowerDistributionPanel();

	
	public static int liftMotor1PDP = 14;
	public static int liftMotor2PDP = 15;
	public static int liftBrakePDP = 4;
	public static double driveDistanceBetweenWheels;
	
	
		
}
