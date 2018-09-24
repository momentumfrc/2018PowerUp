package org.usfirst.frc.team4999.commands.lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ZeroAndManualLift extends CommandGroup {

    public ZeroAndManualLift() {
    	addSequential(new ZeroLift());
    	addSequential(new ManualLift());
    }
}
