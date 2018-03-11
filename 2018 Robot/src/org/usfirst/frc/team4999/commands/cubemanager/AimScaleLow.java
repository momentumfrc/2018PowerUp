package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.lift.SetLiftHeight;
import org.usfirst.frc.team4999.robot.controllers.LiftPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AimScaleLow extends CommandGroup {
	
	private static final double SCALE_ANGLE = 45;
	
    public AimScaleLow() {
    	addParallel(new SetLiftHeight(LiftPosition.SCALE_LOW, true));
        addParallel(new SetElbowPosition(SCALE_ANGLE));
    }
}
