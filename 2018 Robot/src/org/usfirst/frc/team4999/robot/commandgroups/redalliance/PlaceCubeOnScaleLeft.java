package org.usfirst.frc.team4999.robot.commandgroups.redalliance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4999.commands.autonomous.*;
import org.usfirst.frc.team4999.robot.commands.lift.MoveLift;
import org.usfirst.frc.team4999.robot.commands.lift.SetLiftToZero;

/**
 *
 */
public class PlaceCubeOnScaleLeft extends CommandGroup {

    public PlaceCubeOnScaleLeft() {
    	addSequential(new ForwardBackward(5));// put random dist, will tune at future meetings
    	addSequential(new TurnByDeg(90));
    	addSequential(new MoveLift(10)); // tune that dist obvi
    	//need to write command to 'shoot' out cube
    	addSequential(new SetLiftToZero());
    	addSequential(new ForwardBackward(-5)); // get outta there (again, tune the dist)
    }
}
