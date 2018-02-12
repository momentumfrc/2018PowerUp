package org.usfirst.frc.team4999.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.commands.drive.*;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;
import org.usfirst.frc.team4999.utils.PIDFactory;

/**
 *
 */
public class DriveSystem extends Subsystem {

    private SpeedControllerGroup leftside = new SpeedControllerGroup(RobotMap.leftFrontMotor, RobotMap.leftBackMotor);
    private SpeedControllerGroup rightside = new SpeedControllerGroup(RobotMap.rightFrontMotor, RobotMap.rightBackMotor);
    
    private DifferentialDrive drive = new DifferentialDrive(leftside, rightside);
  
    public MomentumPID movePID, turnPID;
    public MomentumPID moveRatePID, turnRatePID;
    public MomentumPID pitchPID;
    
    
    
    public DriveSystem() {
    	super("Drive System");
    	drive.setDeadband(0);
    	addChild("Left Side", leftside);
    	addChild("Right Side", rightside);
    	
    	movePID = PIDFactory.getMovePID();
    	turnPID = PIDFactory.getTurnPID();
    	
    	moveRatePID = PIDFactory.getMoveRatePID();
    	turnRatePID = PIDFactory.getMoveRatePID();
    	
    	pitchPID = PIDFactory.getTiltPID();
    	
    	addChild(drive);
    	addChild(movePID);
    	addChild(turnPID);
    	addChild(moveRatePID);
    	addChild(turnRatePID);
    	addChild(pitchPID);
    	
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new FlightStickDrive());
    }
    
    public void arcadeDrive(double moveRequest, double turnRequest, double speedLimiter) {
    	double m_r = clip(moveRequest, -1, 1) * speedLimiter;
    	double t_r = clip(turnRequest, -1, 1) * speedLimiter;
    	drive.arcadeDrive(m_r, t_r, false);
    }
    
    public void tankDrive(double leftSide, double rightSide, double speedLimiter) {
    	double l_m = clip(leftSide * speedLimiter, -1, 1);
    	double r_m = clip(rightSide * speedLimiter, -1, 1);
    	drive.tankDrive(l_m, r_m);
    }
    
    public void stop() {
    	movePID.disable();
    	turnPID.disable();
    	moveRatePID.disable();
    	turnRatePID.disable();
    	pitchPID.disable();
    	tankDrive(0,0,0);
    }
    
    public void driveTurn() {
    	if(turnPID.isEnabled())
    		arcadeDrive(0, turnPID.get(), MoPrefs.getAutoSpeed());
    	else
    		arcadeDrive(0,0,0);
    }
    
    public void driveMove() {
    	if(turnPID.isEnabled() && movePID.isEnabled() && pitchPID.isEnabled())
    		arcadeDrive(movePID.get() + pitchPID.get(), turnPID.get(), MoPrefs.getAutoSpeed());
    	else if(turnPID.isEnabled() && movePID.isEnabled())
    		arcadeDrive(movePID.get(), turnPID.get(), MoPrefs.getAutoSpeed());
    	else
    		arcadeDrive(0,0,0);
    }
    
    private double clip(double val, double min, double max) {
    	return Math.max(Math.min(val, max), min);
    }
    
}

