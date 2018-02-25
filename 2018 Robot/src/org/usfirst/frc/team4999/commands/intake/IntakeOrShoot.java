package org.usfirst.frc.team4999.commands.intake;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeOrShoot extends CommandGroup {

    public IntakeOrShoot() {
        if(Robot.intake.holding()) {
        	addSequential(new ShootAndStop());
        } else {
        	addSequential(new IntakeAndHold());
        }
    }
}
