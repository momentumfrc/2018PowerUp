package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.intake.IntakeOpen;
import org.usfirst.frc.team4999.commands.lift.SetLiftHeight;
import org.usfirst.frc.team4999.robot.controllers.LiftPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Hunt extends CommandGroup {

    public Hunt(double lift, double elbow) {
    	addSequential(new SetLiftAndElbow(lift, elbow));
    	addSequential(new IntakeOpen());
    }
}
