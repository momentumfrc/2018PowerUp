package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.lift.SetLiftHeight;
import org.usfirst.frc.team4999.robot.controllers.LiftPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AimSwitch extends CommandGroup {
	
	private static final double SWITCH_ANGLE = 45;
	
    public AimSwitch() {
    	addParallel(new SetLiftHeight(LiftPosition.SWITCH, true));
        addParallel(new SetElbowPosition(SWITCH_ANGLE));
    }
}
