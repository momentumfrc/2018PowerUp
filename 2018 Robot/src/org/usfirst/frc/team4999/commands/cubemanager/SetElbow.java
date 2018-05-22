package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.MaintainElbowPosition;
import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetElbow extends CommandGroup {

    public SetElbow(double elbow) {
		addSequential(new SetElbowPosition(elbow));
		addSequential(new MaintainElbowPosition());
    }
}
