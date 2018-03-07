package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.commands.intake.StopIntake;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;
import org.usfirst.frc.team4999.utils.PDPWrapper;
import org.usfirst.frc.team4999.utils.PIDFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
    private Spark intakeLeft = RobotMap.intakeLeft;
    private Spark intakeRight = RobotMap.intakeRight;
    private DoubleSolenoid claw = RobotMap.clawArms;
    
    private static final double INTAKE_SPEED = 1;
    private static final double HOLD_SPEED = 0.1;
    private static final double SHOOT_SPEED = 1;
    
    private static final double CUTOFF_CURRENT = 8;
	private static final int CUTOFF_TIME = 500;
	
	private PDPWrapper currentChecker = new PDPWrapper();
	
	private Timer time = new Timer();
    
    private boolean holding = false;
    
    public Intake() {
    	super();
    	intakeLeft.setInverted(true);
    	time.start();
    }
    
    private void setIntake(double speed) {
    	intakeRight.set(speed);
    	intakeLeft.set(speed);
    }
    
    private void grip() {
		claw.set(DoubleSolenoid.Value.kForward);
	}
	private void release() {
		claw.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void stop() {
		setIntake(0);
		grip();
	}
	
	public void hunt() {
		release();
		setIntake(INTAKE_SPEED);
	}
	
	public void grab() {
		grip();
		if(holding) {
			setIntake(HOLD_SPEED);
		} else {
			setIntake(INTAKE_SPEED);
			holding = currentChecker.checkOvercurrent(new int[] {RobotMap.LEFT_INTAKE_PDP, RobotMap.RIGHT_INTAKE_PDP}, CUTOFF_CURRENT, CUTOFF_TIME);
		}	
		
	}
	
	public void shoot() {
		setIntake(-SHOOT_SPEED);
		holding = false;
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new StopIntake());
    }
    
}

