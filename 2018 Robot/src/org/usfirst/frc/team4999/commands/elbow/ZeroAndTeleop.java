package org.usfirst.frc.team4999.commands.elbow;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ZeroAndTeleop extends CommandGroup {

    public ZeroAndTeleop() {
        addSequential(new ZeroElbow());
        addSequential(new TeleopElbowPID());
    }
}
