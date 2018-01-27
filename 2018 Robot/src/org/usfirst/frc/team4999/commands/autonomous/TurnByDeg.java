package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnByDeg extends Command {

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
			
		}


		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public double pidGet() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
    public TurnByDeg() {
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
