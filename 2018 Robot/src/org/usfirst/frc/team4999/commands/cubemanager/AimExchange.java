package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.lift.SetLiftHeight;
import org.usfirst.frc.team4999.robot.controllers.LiftPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AimExchange extends CommandGroup {
	
	private static final double EXCHANGE_ANGLE = 45;

    public AimExchange() {
    	addParallel(new SetLiftHeight(LiftPosition.EXCHANGE, true));
        addParallel(new SetElbowPosition(EXCHANGE_ANGLE));
    }
}
