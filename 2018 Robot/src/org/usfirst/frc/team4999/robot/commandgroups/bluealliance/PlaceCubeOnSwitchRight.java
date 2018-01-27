package org.usfirst.frc.team4999.robot.commandgroups.bluealliance;

import org.usfirst.frc.team4999.commands.autonomous.ForwardBackward;
import org.usfirst.frc.team4999.commands.autonomous.TurnByDeg;
import org.usfirst.frc.team4999.robot.commands.lift.MoveLift;
import org.usfirst.frc.team4999.robot.commands.lift.SetLiftToZero;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceCubeOnSwitchRight extends CommandGroup {

    public PlaceCubeOnSwitchRight() {
    	addSequential(new ForwardBackward(5));// put random dist, will tune at future meetings
    	addSequential(new TurnByDeg(90));
    	addSequential(new MoveLift(10)); // tune that dist obvi
    	//need to write command to 'shoot' out cube
    	addSequential(new SetLiftToZero());
    	addSequential(new ForwardBackward(-5)); // get outta there (again, tune the dist)
    }
}
