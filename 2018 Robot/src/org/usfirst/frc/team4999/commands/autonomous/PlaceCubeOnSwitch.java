package org.usfirst.frc.team4999.commands.autonomous;

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
        		break;
        	}
        	break;
        }
    }
}
