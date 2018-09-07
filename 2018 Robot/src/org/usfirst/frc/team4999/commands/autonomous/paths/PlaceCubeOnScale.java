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
        		addSequential(new MoveDistance(6.1));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(6.72));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(2));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(1));
        		break;
        	case RIGHT:
        		addSequential(new MoveDistance(1));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(6));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(6.45871));
        		break;

        	}
        	break;
        case MIDDLE:
        	switch(target) {
        	case LEFT:
		        addSequential(new MoveDistance(.6));
        		addSequential(new TurnDegrees(-131.42));
        		addSequential(new MoveDistance(3.7));
        		addSequential(new TurnDegrees(131.42));
        		addSequential(new MoveDistance(5.2));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(.42));
        		break;
        	case RIGHT:
		        addSequential(new MoveDistance(.6));
        		addSequential(new TurnDegrees(131.42));
        		addSequential(new MoveDistance(3.7));
        		addSequential(new TurnDegrees(-131.42));
        		addSequential(new MoveDistance(5.2));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(.42));
        		break;
        	}
        	break;
        case RIGHT:
        	switch(target) {
        	case LEFT:
        		addSequential(new MoveDistance(6.1));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(6.72));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(2));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(1));
        		break;
        	case RIGHT:
        		addSequential(new MoveDistance(8.22));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(1.0));
        		break;
        	}
        	break;
        }
		addSequential(new SetLiftHeight(-3.7, true));
		addSequential(new SetElbowPosition(60));
		addSequential(new Shoot());
    }
}
