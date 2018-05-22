package org.usfirst.frc.team4999.commands;

import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.intake.Shoot;
import org.usfirst.frc.team4999.commands.lift.SetLiftHeight;
import org.usfirst.frc.team4999.utils.MoPrefs;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepareClimb extends CommandGroup {
	
	private static final double ELBOW_POSITION = 0;

    public PrepareClimb() {
    	addSequential(new Shoot());
    	addParallel(new SetLiftHeight(MoPrefs.getClimbHeight(), true));
    	addParallel(new SetElbowPosition(ELBOW_POSITION));
    }
}
