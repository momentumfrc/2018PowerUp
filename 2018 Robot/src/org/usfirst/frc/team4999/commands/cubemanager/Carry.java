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

    public Carry(double lift, double elbow) {
    	addSequential(new Grab());
    	addParallel(new SetLiftAndElbow(lift, elbow));
    	addSequential(new Hold());
    }
}
