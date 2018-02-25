package org.usfirst.frc.team4999.commands.intake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootAndStop extends CommandGroup {

    public ShootAndStop() {
        addSequential(new ShootIntake());
        addSequential(new StopIntake());
    }
}
