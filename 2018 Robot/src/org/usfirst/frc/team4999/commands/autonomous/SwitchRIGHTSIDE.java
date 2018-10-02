package org.usfirst.frc.team4999.commands.autonomous;

import org.usfirst.frc.team4999.commands.autonomous.paths.StartPosition;
import org.usfirst.frc.team4999.commands.autonomous.paths.TargetPosition;
import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.intake.Shoot;
import org.usfirst.frc.team4999.utils.MoPrefs;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchRIGHTSIDE extends CommandGroup {

    public SwitchRIGHTSIDE(StartPosition start, TargetPosition target) {
    	switch(start) {
    	case RIGHT:
    		switch(target) {
    		case RIGHT:
                addSequential(new MoveDistance(-3.5));
                addSequential(new TurnDegrees(-90));
                addSequential(new MoveDistance(-.5));
            break;
			case LEFT:
				addSequential(new MoveDistance(-.6));
				addSequential(new TurnDegrees(-90));
				addSequential(new MoveDistance(-6));
				addSequential(new TurnDegrees(90));
				addSequential(new MoveDistance(-2.9));
				addSequential(new TurnDegrees(90));
				break;
    		}
    	break;   	
		case MIDDLE:
			switch(target) {
    		case LEFT:
		        addSequential(new MoveDistance(.6));
        		addSequential(new TurnDegrees(-41.42));
        		addSequential(new MoveDistance(3.7));
        		addSequential(new TurnDegrees(41.42));
        		addSequential(new MoveDistance(1.22));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(1));
				break;
			case RIGHT:
				addSequential(new MoveDistance(.6));
        		addSequential(new TurnDegrees(41.42));
        		addSequential(new MoveDistance(3.7));
        		addSequential(new TurnDegrees(-41.42));
        		addSequential(new MoveDistance(1.22));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(1));
				break; 
			}
			break;
    	case LEFT:
    		switch(target) {
    		case LEFT:
                addSequential(new MoveDistance(-3.5));
                addSequential(new TurnDegrees(90));
                addSequential(new MoveDistance(-.5));
            break;
			case RIGHT:
				addSequential(new MoveDistance(-.6));
				addSequential(new TurnDegrees(90));
				addSequential(new MoveDistance(-6));
				addSequential(new TurnDegrees(-90));
				addSequential(new MoveDistance(-2.9));
				addSequential(new TurnDegrees(-90));
				break;
    		}
    	break;
		}
        addSequential(new SetElbowPosition(35));
        addSequential(new Shoot());
    }
}
