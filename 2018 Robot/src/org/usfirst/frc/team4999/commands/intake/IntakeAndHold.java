package org.usfirst.frc.team4999.commands.intake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeAndHold extends CommandGroup {

    public IntakeAndHold() {
        addSequential(new RunIntake());
        addSequential(new Hold());
    }
}
