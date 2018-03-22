package org.usfirst.frc.team4999.commands.lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ZeroAndTeleopLift extends CommandGroup {

    public ZeroAndTeleopLift() {
    	addSequential(new ZeroLift());
    	addSequential(new ManualLift());
    }
}
