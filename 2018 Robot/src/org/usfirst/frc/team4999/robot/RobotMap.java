/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4999.robot;

import org.usfirst.frc.team4999.robot.controllers.LogitechF310;
import org.usfirst.frc.team4999.robot.sensors.ADIS16448_IMU;
import org.usfirst.frc.team4999.robot.sensors.GyroFusion;
import org.usfirst.frc.team4999.robot.sensors.VMXPi;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team4999.robot.sensors.ADIS16448_IMU.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
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
	
	// Drive System
	public static VictorSP leftFrontMotor = new VictorSP(2);
	public static VictorSP leftBackMotor = new VictorSP(3);
	public static VictorSP rightFrontMotor = new VictorSP(0);
	public static VictorSP rightBackMotor = new VictorSP(1);
	public static Encoder leftDriveEncoder = new Encoder(0,1, true);
	public static Encoder rightDriveEncoder = new Encoder(2,3);
	
	// Controls
	public static XboxController xbox = new XboxController(1);
	public static Joystick flightStick = new Joystick(0);
	public static LogitechF310 f310 = new LogitechF310(2);
	public static Joystick wheel = new Joystick(3);
	
	// Lift
	public static Encoder liftEncoder = new Encoder(4,5);
	public static SpeedControllerGroup liftMotors = new SpeedControllerGroup(new VictorSP(4), new VictorSP(5));
	public static DoubleSolenoid liftShifter = new DoubleSolenoid(0,1);
	public static DigitalInput liftZeroSwitch = new DigitalInput(6);
	
	// Gyros
	public static ADIS16448_IMU adis = new ADIS16448_IMU(Axis.kZ, AHRSAlgorithm.kMadgwick);
	public static AHRS vmx = new AHRS(SerialPort.Port.kUSB);
	public static GyroFusion gyro = new GyroFusion();
	
	// Grabber
	public static Spark elbow = new Spark(6);
	public static Encoder elbowEncoder = new Encoder(7,8);
	public static Spark intakeLeft = new Spark(7);
	public static Spark intakeRight = new Spark(8);
	public static DoubleSolenoid clawArms = new DoubleSolenoid(4,5);
	
	
	public static PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	public static final int LEFT_INTAKE_PDP = 12;
	public static final int RIGHT_INTAKE_PDP = 13;
	public static final int ELBOW_PDP = 4;
	
	public static final int LIFT_MOTOR1_PDP = 14;
	public static final int LIFT_MOTOR2_PDP = 15;
	
	public static final int RF_DRIVE_MOTOR_PDP = 2;
	public static final int RB_DRIVE_MOTOR_PDP = 3;
	public static final int LF_DRIVE_MOTOR_PDP = 0;
	public static final int LB_DRIVE_MOTOR_PDP = 1;
}
