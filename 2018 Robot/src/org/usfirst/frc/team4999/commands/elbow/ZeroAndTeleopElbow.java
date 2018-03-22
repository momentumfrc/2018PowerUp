package org.usfirst.frc.team4999.commands.elbow;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ZeroAndTeleopElbow extends CommandGroup {

    public ZeroAndTeleopElbow() {
        addSequential(new ZeroElbow());
        addSequential(new TeleopElbowPID());
    }
}
