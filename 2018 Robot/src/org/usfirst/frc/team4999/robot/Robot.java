/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4999.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4999.commands.*;
import org.usfirst.frc.team4999.commands.autonomous.*;
import org.usfirst.frc.team4999.commands.autonomous.paths.*;
import org.usfirst.frc.team4999.commands.elbow.ZeroAndTeleopElbow;
import org.usfirst.frc.team4999.commands.elbow.ZeroElbow;
import org.usfirst.frc.team4999.commands.lift.ManualLiftNoLimit;
import org.usfirst.frc.team4999.commands.lift.ZeroLift;
import org.usfirst.frc.team4999.lights.BrightnessFilter;
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
	public static final Lift lift = new Lift();
	public static final Intake intake = new Intake();
	public static final Elbow elbow = new Elbow();
	public static OI m_oi;
	
	public static ControlChooser controlChooser = new ControlChooser();
	public static StartPosChooser startPos = new StartPosChooser();
	public static AutoModeChooser target = new AutoModeChooser();
	public static LightsChooser lights = new LightsChooser();
	
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
		
		BrightnessFilter.register();
		
		CameraServer.getInstance().startAutomaticCapture();
		
		RobotMap.liftEncoder.setReverseDirection(true);
		
		RobotMap.liftEncoder.setDistancePerPulse(1/MoPrefs.getLiftEncTicks());
		RobotMap.elbowEncoder.setDistancePerPulse(1/6.2222);
		
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
			if(switchPos == null || scalePos == null) {
				System.out.format("Recieved invalid data from FMS: \"%s\"\n", fieldPos);
				autoCommand = new TimeBasedFallback();
				autoCommand.start();
				return;
			}
		} else {
			System.out.format("Recieved invalid data from FMS: \"%s\"\n", fieldPos);
			autoCommand = new TimeBasedFallback();
			autoCommand.start();
			return;
		}
		System.out.format("switch:%s start:%s\n", (switchPos == TargetPosition.LEFT) ? "LEFT":"RIGHT", (startPos.getSelected() == StartPosition.LEFT) ? "LEFT":"RIGHT");
		switch(target.getSelected()) {
		case SWITCH:
			autoCommand = new PlaceCubeOnSwitch(startPos.getSelected(), switchPos);
			break;
		case SCALE:
			autoCommand = new PlaceCubeOnScale(startPos.getSelected(), scalePos);
			break;
		case FALLBACK_DISTANCE:
			autoCommand = new DistanceBasedFallback();
			break;
		case FALLBACK_TIME:
			autoCommand = new TimeBasedFallback();
			break;
		case FALLBACK_TIME_SHOOT:
			if((startPos.getSelected() == StartPosition.LEFT && switchPos == TargetPosition.LEFT) || (startPos.getSelected() == StartPosition.RIGHT && switchPos == TargetPosition.RIGHT))
				autoCommand = new TimeBasedFallbackSwitch();
			else
				autoCommand = new TimeBasedFallback();
			break;
		}
		
		RobotMap.leftDriveEncoder.reset();
		RobotMap.rightDriveEncoder.reset();
		
		autoCommand.start();
		
		/*ZeroElbow zeroElbow = new ZeroElbow();
		ZeroLift zeroLift = new ZeroLift();
		zeroElbow.start();
		zeroLift.start();*/
	}
	
	private TargetPosition posFromChar(char pos) {
		if(pos == 'L')
			return TargetPosition.LEFT;
		else if(pos == 'R')
			return TargetPosition.RIGHT;
		else
			System.out.format("Recieved invalid input \"%c\"\n", pos);
		return null;
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
		new DriveNoPID().start();
		new ZeroAndTeleopElbow().start();
		new ManualLiftNoLimit().start();
		
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
		lift.liftDashboard();
		
		SmartDashboard.putData(RobotMap.pdp);
		
		//System.out.format("LiftEncoder count:%d distance:%.2f\n", RobotMap.liftEncoder.get(),RobotMap.liftEncoder.getDistance());
		
		
		//System.out.format("ELBOW: Count:%d Angle:%.2f\n", RobotMap.elbowEncoder.get(), RobotMap.elbowEncoder.getDistance());
		
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
		
		System.out.format("LIFT Enabled:%b Fast:%b Current:%.2f Setpoint:%.2f Output:%.2f\n", lift.currentLiftPID.isEnabled(), lift.isHighSpeedPID(), RobotMap.liftEncoder.getDistance(), lift.currentLiftPID.getSetpoint(), lift.currentLiftPID.get());
		lift.driveLiftPID();
		
		elbow.drivePID();
		System.out.format("ELBOW Enabled:%b Current:%.2f Setpoint:%.2f Output:%.2f\n",Robot.elbow.pid.isEnabled(), RobotMap.elbowEncoder.getDistance(),Robot.elbow.pid.getSetpoint(),Robot.elbow.pid.get());
		
		//System.out.format("Accel X:%.2f Y:%.2f Z:%.2f\n", RobotMap.vmx.getWorldLinearAccelX(),RobotMap.vmx.getWorldLinearAccelY(),RobotMap.vmx.getWorldLinearAccelZ());
		//System.out.format("Angle:%.2f Rate:%.2f Pitch:%.2f Roll:%.2f xV:%.2f yV:%.2f\n", RobotMap.pi.getAngle(), RobotMap.pi.getRate(), RobotMap.pi.getPitch(), RobotMap.pi.getRoll(), RobotMap.pi.getXVelocity(), RobotMap.pi.getYVelocity());
		
		Robot.driveSystem.driveDisplacementPID();
		System.out.format("MOVE Enabled:%b Current:%.2f Setpoint:%.2f Output:%.2f\n", Robot.driveSystem.movePID.isEnabled(), RobotMap.leftDriveEncoder.getDistance(), Robot.driveSystem.movePID.getSetpoint(), Robot.driveSystem.movePID.get());
		System.out.format("TURN Enabled:%b Current:%.2f Setpoint:%.2f Output:%.2f\n", Robot.driveSystem.turnPID.isEnabled(), RobotMap.vmx.getAngle(), Robot.driveSystem.turnPID.getSetpoint(), Robot.driveSystem.turnPID.get());
		System.out.println("---------------");

	}
}
