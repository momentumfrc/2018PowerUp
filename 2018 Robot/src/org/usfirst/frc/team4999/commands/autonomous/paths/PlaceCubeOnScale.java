package org.usfirst.frc.team4999.commands.autonomous.paths;

import org.usfirst.frc.team4999.commands.autonomous.MoveDistance;
import org.usfirst.frc.team4999.commands.autonomous.TurnDegrees;
import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.intake.Shoot;
import org.usfirst.frc.team4999.commands.lift.SetLiftHeight;

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
        		addSequential(new MoveDistance(1));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(6));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(6.61111));
        		
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
        		addSequential(new MoveDistance(1));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(6));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(6.61111));
        		break;
        	}
        	break;
        }
		addSequential(new SetLiftHeight(.5, true));//TODO Figure out the lift height for Max scale 
		addSequential(new SetElbowPosition(90));
		addSequential(new Shoot());
    }
}
