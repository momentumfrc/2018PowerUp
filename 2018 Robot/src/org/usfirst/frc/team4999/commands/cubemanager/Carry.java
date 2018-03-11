package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.intake.Grab;
import org.usfirst.frc.team4999.commands.intake.Hold;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Carry extends CommandGroup {
	
	private static final double CARRY_ANGLE = 10;

    public Carry() {
    	addSequential(new Grab());
    	addParallel(new SetElbowPosition(CARRY_ANGLE));
    	addSequential(new Hold());
    }
}
