package org.usfirst.frc.team4999.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceCubeOnScale extends CommandGroup {
	
	public PlaceCubeOnScale(StartPosition start, TargetPosition target) {
		switch(start) {
        case LEFT:
        	switch(target) {
        	case LEFT:
        		addSequential(new MoveDistance(7.61111));
        		break;
        	case RIGHT:
        		addSequential(new MoveDistance(1.5));
        		addSequential(new TurnDegrees(90));
        		break;
        	}
        	break;
        case MIDDLE:
        	switch(target) {
        	case LEFT:
        		break;
        	case RIGHT:
        		break;
        	}
        	break;
        case RIGHT:
        	switch(target) {
        	case LEFT:
        		addSequential(new MoveDistance(7.61111));
        		break;
        	case RIGHT:
        		break;
        	}
        	break;
        }
    }
}
