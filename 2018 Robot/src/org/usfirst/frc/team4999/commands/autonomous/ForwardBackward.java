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
public class ForwardBackward extends Command {
	
	private static PIDController pid;
	private Encoder leftSideEncoder = RobotMap.leftDriveEncoder;
	private Encoder rightSideEncoder = RobotMap.rightDriveEncoder;
	private static double ticksPerMeter = 1;
	
	
	static class AverageEncoder implements PIDSource{
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
		
		static class DriveWithAngleCorrection implements PIDOutput {
			private DriveSystem output;
			private Encoder left, right;
			private double lStart, rStart;
			
			private final double distanceBetweenWheels = 0.5; // meters
			private final double moveErrGain = 0.1;
			
			public DriveWithAngleCorrection(DriveSystem output, Encoder left, Encoder right) {
				this.output = output;
				this.left = left;
				this.right = right;
				this.lStart = left.getDistance();
				this.rStart = right.getDistance();
			}
			
			 private double getAngle() { 
		    	return (((left.getDistance() - lStart) - (right.getDistance() - rStart))/distanceBetweenWheels) * 360;
		    }

			@Override
			public void pidWrite(double output) {
				this.output.arcadeDrive(output, getAngle() * moveErrGain, RobotMap.auto_speed);
			}
			
		}
		
	    // Called just before this Command runs the first time
	    protected void initialize() {
	    	left.setDistancePerPulse(1/ticksPerMeter);
	    	right.setDistancePerPulse(1/ticksPerMeter);
	    	pid.setSetpoint(((left.getDistance() + right.getDistance()) / 2) + distance);
	    	pid.enable();
	    }
		
		
		
		
	}
	
	
	
    public ForwardBackward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
