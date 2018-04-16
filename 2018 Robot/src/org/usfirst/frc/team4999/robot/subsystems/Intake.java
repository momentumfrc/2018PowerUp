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
    private static final double HOLD_SPEED = 0.2;
    private static final double SHOOT_SPEED = 1;
	
	private PDPWrapper currentChecker = new PDPWrapper();
	
	private static final double HOLD_CURRENT = 2; // TODO: Set this to the real value
	private static final int HOLD_CUTOFF_TIME = 500;
	
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
    
    public void grip() {
		claw.set(DoubleSolenoid.Value.kForward);
	}
	public void release() {
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
		System.out.format("LC: %.2f RC:%.2f LS:%.2f RS:%.2f\n",RobotMap.pdp.getCurrent(RobotMap.LEFT_INTAKE_PDP),RobotMap.pdp.getCurrent(RobotMap.RIGHT_INTAKE_PDP), intakeLeft.get(), intakeRight.get());
		setIntake(INTAKE_SPEED);
		//holding = currentChecker.checkOvercurrent(new int[] {RobotMap.LEFT_INTAKE_PDP, RobotMap.RIGHT_INTAKE_PDP}, CUTOFF_CURRENT, CUTOFF_TIME);
	}
	
	public boolean hold() {
		grip();
		setIntake(HOLD_SPEED);
		return checkHeld();
	}
	
	public boolean checkHeld() {
		return !currentChecker.checkUndercurrent(new int[] {RobotMap.LEFT_INTAKE_PDP,  RobotMap.RIGHT_INTAKE_PDP}, HOLD_CURRENT, HOLD_CUTOFF_TIME);
	}
	public void shoot() {
		setIntake(-SHOOT_SPEED);
		holding = false;
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new StopIntake());
    }
    
}

