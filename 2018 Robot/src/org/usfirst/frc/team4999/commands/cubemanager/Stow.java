package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.lift.SetLiftHeight;
import org.usfirst.frc.team4999.robot.controllers.LiftPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Stow extends CommandGroup {
	
	private static final double STOW_ANGLE = 0;

    public Stow() {
    	addParallel(new SetLiftHeight(LiftPosition.GROUND, true));
        addParallel(new SetElbowPosition(STOW_ANGLE));
    }
}
