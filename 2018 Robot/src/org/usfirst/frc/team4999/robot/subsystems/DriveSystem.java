package org.usfirst.frc.team4999.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.commands.drive.*;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;

/**
 *
 */
public class DriveSystem extends Subsystem {

    private SpeedControllerGroup leftside = new SpeedControllerGroup(RobotMap.leftFrontMotor, RobotMap.leftBackMotor);
    private SpeedControllerGroup rightside = new SpeedControllerGroup(RobotMap.rightFrontMotor, RobotMap.rightBackMotor);
    
    private DifferentialDrive drive = new DifferentialDrive(leftside, rightside);
  
    public MomentumPID movePID, turnPID;
    
    class AverageEncoder implements PIDSource{
		private Encoder left, right;

		private PIDSourceType sourcetype = PIDSourceType.kDisplacement;


		
		public AverageEncoder(Encoder left, Encoder right) {
			this.left = left;
			this.right = right;
		}
		
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			sourcetype = pidSource;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return sourcetype;
		}

		@Override
		public double pidGet() {
			switch(sourcetype) {
			case kRate:
				return (left.getRate() + right.getRate()) / 2;
			case kDisplacement:
			default:
				return (left.getDistance() + right.getDistance()) / 2;
			}
		}
		
	}
    
    public DriveSystem() {
    	super("Drive System");
    	drive.setDeadband(0);
    	addChild("Left Side", leftside);
    	addChild("Right Side", rightside);
    	
    	movePID = new MomentumPID(
    			"Movement PID Controller",
    			MoPrefs.getMoveP(), 
    			MoPrefs.getMoveI(), 
    			MoPrefs.getMoveD(),
    			MoPrefs.getMoveErrZone(),
    			MoPrefs.getMoveTargetZone(),
    			new AverageEncoder(RobotMap.leftDriveEncoder, RobotMap.rightDriveEncoder),
    			null
    		);
    	turnPID = new MomentumPID(
    			"Turn PID Controller",
    			MoPrefs.getTurnP(),
    			MoPrefs.getTurnI(),
    			MoPrefs.getTurnD(),
    			MoPrefs.getMoveErrZone(),
    			MoPrefs.getMoveTargetZone(),
    			RobotMap.gyro,
    			null
    		);
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new FlightStickDrive());
    }
    
    public void arcadeDrive(double moveRequest, double turnRequest, double speedLimiter) {
    	double m_r = clamp(moveRequest * speedLimiter, -1, 1);
    	double t_r = clamp(turnRequest * speedLimiter, -1, 1);
    	drive.arcadeDrive(m_r, t_r, false);
    }
    
    public void tankDrive(double leftSide, double rightSide, double speedLimiter) {
    	double l_m = clamp(leftSide * speedLimiter, -1, 1);
    	double r_m = clamp(rightSide * speedLimiter, -1, 1);
    	drive.tankDrive(l_m, r_m);
    }
    
    public void stop() {
    	movePID.disable();
    	turnPID.disable();
    	tankDrive(0,0,0);
    }
    
    private double clamp(double val, double min, double max) {
    	double out = Math.min(val, max);
    	out = Math.max(val, min);
    	return out;
    }
}

