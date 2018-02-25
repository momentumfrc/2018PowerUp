package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.commands.intake.StopIntake;
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
    
    private boolean holding = false;
    
    public Intake() {
    	super();
    	intakeLeft.setInverted(true);
    }
    
    public void setIntake(double speed) {
    	intakeRight.set(speed);
    	intakeLeft.set(speed);
    }
    public void initDefaultCommand() {
        setDefaultCommand(new StopIntake());
    }
    
    public void setHolding(boolean holding) {
    	this.holding = holding;
    }
    
    public boolean holding() {
    	return holding;
    }
}

