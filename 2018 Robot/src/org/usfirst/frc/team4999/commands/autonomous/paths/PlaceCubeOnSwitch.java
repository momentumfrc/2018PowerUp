package org.usfirst.frc.team4999.commands.autonomous.paths;

import org.usfirst.frc.team4999.commands.autonomous.MoveDistance;
import org.usfirst.frc.team4999.commands.autonomous.TurnDegrees;
import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.intake.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceCubeOnSwitch extends CommandGroup {
	
    public PlaceCubeOnSwitch(StartPosition start, TargetPosition target) {
        switch(start) {
        case LEFT:
        	switch(target) {
        	case LEFT:
        		addSequential(new MoveDistance(2.635));
        		addSequential(new TurnDegrees(45));
        		addSequential(new MoveDistance(1.6));
        		break;
        	case RIGHT:
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
        		break;
        	case RIGHT:
        		addSequential(new MoveDistance(2.635));
        		addSequential(new TurnDegrees(-45));
        		addSequential(new MoveDistance(1.446));
        		break;
        	}
        	break;
        }
        
        //addSequential(new SetLiftAndElbow(LiftPosition.SWITCH,CubeManager.ElbowState.ELBOW_SWITCH.angle()));
        addSequential(new SetElbowPosition(35));
        addSequential(new Shoot());
    }
}
