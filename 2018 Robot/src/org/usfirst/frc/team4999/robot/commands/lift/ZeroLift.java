package org.usfirst.frc.team4999.robot.commands.lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Disengages the brake, moves the lift down, reengages the brake
 */
public class ZeroLift extends CommandGroup {

    public ZeroLift() {
    	addSequential(new SetLiftBrake(false));
    	addSequential(new SetLiftToZero());
    	addSequential(new SetLiftBrake(true));
    }
}
