/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4999.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team4999.commands.*;
import org.usfirst.frc.team4999.robot.choosers.*;
import org.usfirst.frc.team4999.robot.subsystems.*;
import org.usfirst.frc.team4999.utils.MoPrefs;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final DriveSystem driveSystem = new DriveSystem();
	public static OI m_oi;
	public static ControlChooser controlChooser = new ControlChooser();


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();

		RobotMap.leftDriveEncoder.setDistancePerPulse(1/MoPrefs.getEncTicks());
		RobotMap.rightDriveEncoder.setDistancePerPulse(1/MoPrefs.getEncTicks());
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
		System.out.println(DriverStation.getInstance().getGameSpecificMessage());
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
 	}

	@Override
	public void teleopInit() {
		TeleopPID teleopPID = new TeleopPID();
		teleopPID.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
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
		
		if(Robot.driveSystem.turnPID.isEnabled()) {
			//System.out.format("Current:%.2f Setpoint:%.2f Output:%.2f\n", RobotMap.gyro.getAngle(), Robot.driveSystem.turnPID.getSetpoint(), Robot.driveSystem.turnPID.get());
	    	Robot.driveSystem.arcadeDrive(0, Robot.driveSystem.turnPID.get(), MoPrefs.getAutoSpeed());
		} else {
			Robot.driveSystem.arcadeDrive(0, 0, 0);
		}
	}
}
