package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.utils.MoPrefs;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DistanceBasedFallback extends CommandGroup {

    public DistanceBasedFallback() {
        addSequential(new MoveDistance(MoPrefs.getFallbackAutoDistance()));
    }
}
