package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Claw extends Subsystem {
	
	private DoubleSolenoid claw = RobotMap.clawArms;
	
	public void grip() {
		claw.set(DoubleSolenoid.Value.kForward);
	}
	public void release() {
		claw.set(DoubleSolenoid.Value.kReverse);
	}
	public boolean isGripped() {
		return claw.get() == DoubleSolenoid.Value.kForward;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

