/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4999.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4999.commands.*;
import org.usfirst.frc.team4999.commands.autonomous.*;
import org.usfirst.frc.team4999.commands.elbow.TeleopElbowPID;
import org.usfirst.frc.team4999.commands.elbow.ZeroElbow;
import org.usfirst.frc.team4999.commands.lift.ZeroLift;
import org.usfirst.frc.team4999.robot.choosers.*;
import org.usfirst.frc.team4999.robot.sensors.GyroFusion.Sensor;
import org.usfirst.frc.team4999.robot.subsystems.*;
import org.usfirst.frc.team4999.utils.MoPrefs;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final DriveSystem driveSystem = new DriveSystem();
	public static final Lift lift = new Lift();
	public static final Intake intake = new Intake();
	public static final Elbow elbow = new Elbow();
	public static OI m_oi;
	
	public static ControlChooser controlChooser = new ControlChooser();
	public static StartPosChooser startPos = new StartPosChooser();
	public static AutoModeChooser target = new AutoModeChooser();
	
	private Command autoCommand;
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();

		RobotMap.leftDriveEncoder.setDistancePerPulse(1/MoPrefs.getDriveEncTicks());
		RobotMap.rightDriveEncoder.setDistancePerPulse(1/MoPrefs.getDriveEncTicks());
		RobotMap.liftEncoder.setDistancePerPulse(1/MoPrefs.getElbowEncTicks());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		String fieldPos = DriverStation.getInstance().getGameSpecificMessage();
		TargetPosition switchPos, scalePos;
		if(fieldPos.length() >= 2) {
			switchPos = posFromChar(fieldPos.charAt(0));
			scalePos = posFromChar(fieldPos.charAt(1));
		} else {
			System.out.format("Recieved invalid data from FMS: \"%s\"\n", fieldPos);
			return;
		}
		
		switch(target.getSelected()) {
		case SWITCH:
			autoCommand = new PlaceCubeOnSwitch(startPos.getSelected(), switchPos);
			break;
		case SCALE:
			autoCommand = new PlaceCubeOnScale(startPos.getSelected(), scalePos);
			break;
		case FALLBACK_DISTANCE:
			autoCommand = new DistanceBasedFallback();
		case FALLBACK_TIME:
			autoCommand = new TimeBasedFallback();
		}
		
		autoCommand.start();
		
		ZeroElbow zeroElbow = new ZeroElbow();
		ZeroLift zeroLift = new ZeroLift();
		zeroElbow.start();
		zeroLift.start();
	}
	
	private TargetPosition posFromChar(char pos) {
		if(pos == 'L')
			return TargetPosition.LEFT;
		else if(pos == 'R')
			return TargetPosition.RIGHT;
		else
			System.out.format("Recieved invalid input \"%c\"\n", pos);
		return TargetPosition.LEFT;
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
 	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().removeAll();
		DriveTiltPID driveCommand = new DriveTiltPID();
		driveCommand.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
		SmartDashboard.putString("Connected Gyro", (RobotMap.gyro.currentSensor() == Sensor.ADIS)?"ADIS":"VMX");
		
		SmartDashboard.putData(RobotMap.pdp);
		
		//System.out.format("Connected:%b Pitch:%.2f Roll:%.2f Yaw:%.2f ", RobotMap.vmx.isConnected(), RobotMap.vmx.getPitch(), RobotMap.vmx.getRoll(), RobotMap.vmx.getYaw());
		
		//System.out.format("Angle: %.2f Sensor:%s Dist:%.2fm Rate:%.2fm/s\n", RobotMap.gyro.getAngle(), (RobotMap.gyro.currentSensor() == Sensor.ADIS)?"ADIS":"VMX", RobotMap.leftDriveEncoder.getDistance(), RobotMap.leftDriveEncoder.getRate());
		/*if(RobotMap.flightStick.getRawButton(5))
			RobotMap.leftDriveEncoder.reset();*/
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {
	}
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		//System.out.format("Angle:%.2f Rate:%.2f Pitch:%.2f Roll:%.2f xV:%.2f yV:%.2f\n", RobotMap.pi.getAngle(), RobotMap.pi.getRate(), RobotMap.pi.getPitch(), RobotMap.pi.getRoll(), RobotMap.pi.getXVelocity(), RobotMap.pi.getYVelocity());
		
		/*Robot.driveSystem.driveDisplacementPID();
		System.out.format("MOVE Enabled:%b Current:%.2f Setpoint:%.2f Output:%.2f\n", Robot.driveSystem.movePID.isEnabled(), RobotMap.leftDriveEncoder.getDistance(), Robot.driveSystem.movePID.getSetpoint(), Robot.driveSystem.movePID.get());
		System.out.format("TURN Enabled:%b Current:%.2f Setpoint:%.2f Output:%.2f\n", Robot.driveSystem.turnPID.isEnabled(), RobotMap.gyro.getAngle(), Robot.driveSystem.turnPID.getSetpoint(), Robot.driveSystem.turnPID.get());
		System.out.format("TILT Enabled:%b Current:%.2f Setpoint:%.2f Output:%.2f\n", Robot.driveSystem.pitchPID.isEnabled(), RobotMap.gyro.getPitch(), Robot.driveSystem.pitchPID.getSetpoint(), Robot.driveSystem.pitchPID.get());
		System.out.println("---------------");*/

	}
}
