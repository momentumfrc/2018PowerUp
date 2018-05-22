package org.usfirst.frc.team4999.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team4999.commands.DriveNoPID;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;
import org.usfirst.frc.team4999.utils.PIDFactory;
import org.usfirst.frc.team4999.utils.PIDThread;
import org.usfirst.frc.team4999.utils.Utils;

/**
 *
 */
public class DriveSystem extends Subsystem {

    private SpeedControllerGroup leftside = new SpeedControllerGroup(RobotMap.leftFrontMotor, RobotMap.leftBackMotor);
    private SpeedControllerGroup rightside = new SpeedControllerGroup(RobotMap.rightFrontMotor, RobotMap.rightBackMotor);
    
    private DifferentialDrive drive = new DifferentialDrive(leftside, rightside);
  
    public MomentumPID movePID, turnPID;
    public MomentumPID pitchPID;    
    
    
    public DriveSystem() {
    	super("Drive System");
    	drive.setDeadband(0);
    	addChild("Left Side", leftside);
    	addChild("Right Side", rightside);
    	
    	movePID = PIDFactory.getMovePID();
    	turnPID = PIDFactory.getTurnPID();
    	
    	pitchPID = PIDFactory.getTiltPID();
    	
    	addChild(drive);
    	addChild(movePID);
    	addChild(turnPID);
    	addChild(pitchPID);
    	
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new DriveNoPID());
    }
    
    public void arcadeDrive(double moveRequest, double turnRequest, double speedLimiter) {
    	double m_r = Utils.clip(moveRequest, -1, 1) * speedLimiter;
    	double t_r = Utils.clip(turnRequest, -1, 1) * speedLimiter;
    	drive.arcadeDrive(m_r, t_r, false);
    }
    
    public void tankDrive(double leftSide, double rightSide, double speedLimiter) {
    	double l_m = Utils.clip(leftSide * speedLimiter, -1, 1);
    	double r_m = Utils.clip(rightSide * speedLimiter, -1, 1);
    	drive.tankDrive(l_m, r_m);
    }
    
    public void stop() {
    	movePID.disable();
    	turnPID.disable();
    	pitchPID.disable();
    	tankDrive(0,0,0);
    }
    
    public void driveDisplacementPID() {
    	double moveRequest = 0, turnRequest = 0;
    	if(pitchPID.isEnabled() && pitchPID.get() != 0) {
    		arcadeDrive(pitchPID.get(), 0, 1);
    		System.out.println("TILTING!!!");
    		return;
    	}
    	if(movePID.isEnabled())
    		moveRequest = movePID.get();
    	if(turnPID.isEnabled())
    		turnRequest = turnPID.get();
    	
    	arcadeDrive(moveRequest, turnRequest, MoPrefs.getAutoSpeed());
    }
    
    public void arcadeDriveStraight(double moveRequest, double turnRequest, double speedLimit) {
    	if(pitchPID.isEnabled() && pitchPID.get() != 0) {
    		arcadeDrive(pitchPID.get(), 0, 1);
    		System.out.println("TILTING!!!");
    		return;
    	} else {
    		arcadeDrive(moveRequest, turnPID.get(), speedLimit);
    	}
    }
    
    public void arcadeDriveTilt(double moveRequest, double turnRequest, double speedLimit) {
    	if(pitchPID.isEnabled() && pitchPID.get() != 0) {
    		System.out.print(pitchPID.get()+ " ");
    		arcadeDrive(-pitchPID.get(), 0, 1);
    		System.out.println("TILTING!!");
    	} else {
    		arcadeDrive(moveRequest, turnRequest, speedLimit);
    	}
    }
    public double get() {
    	return (leftside.get() + rightside.get())/2;
    }
    
}

