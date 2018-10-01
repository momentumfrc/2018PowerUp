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
				break;
			default:
				break;
    		}
    	break;
    	
    	case LEFT:
    		switch(target) {
    		case LEFT:
                addSequential(new MoveDistance(-3.5));
                addSequential(new TurnDegrees(-90));
                addSequential(new MoveDistance(-.5));
            break;
			case RIGHT:
			default:
				break;
    		}
    	break;
		case MIDDLE:
			break;
		default:
			break;
    	}
        addSequential(new SetElbowPosition(35));
        addSequential(new Shoot());
    }
}
