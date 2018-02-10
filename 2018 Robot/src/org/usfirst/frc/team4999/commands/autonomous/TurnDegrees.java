package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.sensors.GyroFusion;
import org.usfirst.frc.team4999.robot.subsystems.DriveSystem;
import org.usfirst.frc.team4999.utils.MoPrefs;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnDegrees extends Command {

	private PIDController angleController;
	private GyroFusion angleGetter;
	private double angle;
	
	private Timer onTargetTime;
	

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
    	angleGetter = RobotMap.gyro;
    	angleGetter.setPIDSourceType(PIDSourceType.kDisplacement);
    	angleController = new PIDController(
    			MoPrefs.getTurnP(),
    			MoPrefs.getTurnI(),
    			MoPrefs.getTurnD(),
    			angleGetter,
    			new DriveTurn(Robot.driveSystem)
    	);
    	onTargetTime = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angleController.setSetpoint(angleGetter.pidGet() + angle);
    	angleController.enable();
    	onTargetTime.start();
    	System.out.format("Beginning turn using P:%.2f I:%.2f D:%.2f\n", angleController.getP(), angleController.getI(), angleController.getD());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.format("Current:%.2f Setpoint:%.2f Output:%.2f\n", angleGetter.getAngle(), angleController.getSetpoint(), angleController.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(angleController.onTarget()) {
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
    	angleController.disable();
    	Robot.driveSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}
