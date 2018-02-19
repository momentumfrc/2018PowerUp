package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.utils.MoPrefs;
import org.usfirst.frc.team4999.utils.MomentumPID;
import org.usfirst.frc.team4999.utils.PIDFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
    private Spark intakeLeft = RobotMap.intakeLeft;
    private Spark intakeRight = RobotMap.intakeRight;
    
    private DoubleSolenoid intakeSolenoid = RobotMap.clawArms;
    
    
    public Intake() {
    	super();
    	intakeLeft.setInverted(true);
    }
    
    public void setIntake(double speed) {
    	intakeRight.set(speed);
    	intakeLeft.set(speed);
    }
    public boolean isGripping() {
    	return intakeSolenoid.get() == DoubleSolenoid.Value.kForward;
    }
    public void grip() {
    	intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void release() {
    	intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

