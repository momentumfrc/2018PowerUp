package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.robot.commands.lift.MoveLift;
import org.usfirst.frc.team4999.robot.commands.lift.SetLiftToZero;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceCubeOnSwitch extends CommandGroup {
	
	public enum startSide {LEFT, MIDDLE, RIGHT};
	public enum targetSide {LEFT, RIGHT};

    public PlaceCubeOnSwitch(startSide start, targetSide target) {
    	
    	switch(start) {
    	case LEFT:
    		switch(target) {
    		case LEFT:
    			addSequential(new MoveDistance(5));// put random dist, will tune at future meetings
    	    	addSequential(new TurnDegrees(90));
    	    	addSequential(new MoveLift(10)); // tune that dist obvi
    	    	//need to write command to 'shoot' out cube
    	    	addSequential(new SetLiftToZero());
    	    	addSequential(new MoveDistance(-5)); // get outta there (again, tune the dist)
    			break;
    		case RIGHT:
    			break;
    		}
    		break;
    	case MIDDLE:
    		break;
    	case RIGHT:
    		break;
    	}
    }
}
