package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.robot.MoPrefs;
import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveDistance extends Command {
	
	private PIDController movePID, turnPID;
	private Encoder left = RobotMap.leftDriveEncoder;
	private Encoder right = RobotMap.rightDriveEncoder;
	private double distance;
	
	private Timer onTargetTime;
	
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
	

	class OutputNothing implements PIDOutput {

		@Override
		public void pidWrite(double output) {
			// Intentionally blank
		}
		
	}
	
	
    public MoveDistance(double distance) {
    	requires(Robot.driveSystem);
    	this.distance = distance;
    	movePID = new PIDController(
    			MoPrefs.getMoveP(), 
    			MoPrefs.getMoveI(), 
    			MoPrefs.getMoveD(),
    			new AverageEncoder(left, right),
    			new OutputNothing()
    		);
    	turnPID = new PIDController(
    			MoPrefs.getTurnP(),
    			MoPrefs.getTurnI(),
    			MoPrefs.getTurnD(),
    			RobotMap.gyro,
    			new OutputNothing()
    		);
    	onTargetTime = new Timer();
    	System.out.format("Beginning move using\n    Move P:%.2d I:%.2d D:%.2d\n    Turn: P:%.2d I:%.2d D:%.2d\n", 
    			movePID.getP(), movePID.getI(), movePID.getD(),
    			turnPID.getP(), turnPID.getI(), turnPID.getD()
    			);
    }
  

    

    // Called just before this Command runs the first time
    protected void initialize() {
    	movePID.setSetpoint(((left.getDistance() + right.getDistance()) / 2) + distance);
    	movePID.enable();
    	turnPID.setSetpoint(RobotMap.gyro.getAngle());
    	turnPID.enable();
    	onTargetTime.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSystem.arcadeDrive(movePID.get(), turnPID.get(), MoPrefs.getAutoSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(movePID.onTarget()) {
        	if(onTargetTime.hasPeriodPassed(MoPrefs.getTargetTime())) {
        		return true;
        	}
        } else {
        	onTargetTime.reset();
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	movePID.disable();
    	turnPID.disable();
    	Robot.driveSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
