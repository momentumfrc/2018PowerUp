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
	
	private static final double HUNT_ANGLE = 90;
	
	class SetLiftAndElbow extends CommandGroup {
		public SetLiftAndElbow() {
			addParallel(new SetLiftHeight(LiftPosition.GROUND, true));
	        addParallel(new SetElbowPosition(HUNT_ANGLE));
		}
	}

    public Hunt() {
    	addSequential(new SetLiftAndElbow());
    	addSequential(new IntakeOpen());
    }
}
