package org.usfirst.frc.team4999.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.commands.XboxDrive;

/**
 *
 */
public class DriveSystem extends Subsystem {

    private SpeedControllerGroup leftside = new SpeedControllerGroup(RobotMap.leftFrontMotor, RobotMap.leftBackMotor);
    private SpeedControllerGroup rightside = new SpeedControllerGroup(RobotMap.rightFrontMotor, RobotMap.rightBackMotor);
    
    private DifferentialDrive drive = new DifferentialDrive(leftside, rightside);
    
    public DriveSystem() {
    	super("Drive System");
    	drive.setDeadband(0);
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new XboxDrive());
    }
    
    public void ArcadeDrive(double moveRequest, double turnRequest, double speedLimiter) {
    	double m_r = clamp(moveRequest * speedLimiter, -1, 1);
    	double t_r = clamp(turnRequest * speedLimiter, -1, 1);
    	drive.arcadeDrive(m_r, t_r);
    }
    
    private double clamp(double val, double min, double max) {
    	double out = Math.min(val, max);
    	out = Math.max(val, min);
    	return out;
    }
}

