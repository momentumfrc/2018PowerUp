package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.subsystems.DriveSystem;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnDegrees extends Command {

	private Encoder left = RobotMap.leftDriveEncoder;
	private Encoder right = RobotMap.rightDriveEncoder;
	
	private double ticksPerMeter = 1;
	
	private PIDController angleController;
	
	private double angle;
	private EncoderTurnOutput angleGetter;
	
	static class EncoderTurnOutput implements PIDSource {
		
		private Encoder left, right;
		private double lStart, rStart;
		
		private PIDSourceType sourcetype = PIDSourceType.kDisplacement;
		
		
		public EncoderTurnOutput(Encoder left, Encoder right) {
			this.left = left;
			this.right = right;
			lStart = left.getDistance();
			rStart = right.getDistance();
		}
		private double getAngle() { 
	    	return (((left.getDistance() - lStart) - (right.getDistance() - rStart))/RobotMap.driveDistanceBetweenWheels) * 360;
	    }
		private double getAngleRate() {
			return ((left.getRate() - right.getRate())/RobotMap.driveDistanceBetweenWheels) * 360;
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			// TODO Auto-generated method stub
			sourcetype = pidSource;
		}


		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return sourcetype;
		}


		@Override
		public double pidGet() {
			switch(sourcetype) {
			case kRate:
				return getAngleRate();
			case kDisplacement:
			default:
				return getAngle();
			}
		}
	}
	

	static class DriveTurn implements PIDOutput {
		private DriveSystem output;
		
		public DriveTurn(DriveSystem output) {
			this.output = output;
		}
		@Override
		public void pidWrite(double output) {
			this.output.arcadeDrive(0, output, RobotMap.auto_speed);
		}
		
	}
	
    public TurnDegrees(double angle) {
    	requires(Robot.driveSystem);
    	this.angle = angle;
    	angleGetter = new EncoderTurnOutput(left, right);
    	angleController = new PIDController(0.2,0,0.01,new EncoderTurnOutput(left, right),new DriveTurn(Robot.driveSystem)
    			);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	left.setDistancePerPulse(1/ticksPerMeter);
    	right.setDistancePerPulse(1/ticksPerMeter);
    	angleController.setSetpoint(angleGetter.getAngle() + angle);
    	angleController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return angleController.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	angleController.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	angleController.disable();
    }

}
