package org.usfirst.frc.team4999.commands.intake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabAndHold extends CommandGroup {

    public GrabAndHold() {
       addSequential(new Grab());
       addSequential(new Hold());
       addSequential(new StopIntake());
    }
}
