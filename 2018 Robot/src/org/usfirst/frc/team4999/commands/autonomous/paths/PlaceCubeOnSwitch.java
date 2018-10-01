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
        		/*
        		addSequential(new MoveDistance(2.635));
        		addSequential(new TurnDegrees(45));
        		addSequential(new MoveDistance(1.6));
        		*/
        		addSequential(new MoveDistance(1));
        		addSequential(new TurnDegrees(45.75));
        		addSequential(new MoveDistance(2.84));
        		addSequential(new TurnDegrees(-45.75));
        		addSequential(new MoveDistance(.61)); 
        		break;
        	/*case RIGHT:
        		addSequential(new MoveDistance(1));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(4.8));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(2.65));    		
        		break;*/
            case LEFTSIDE:
        		addSequential(new MoveDistance(1));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(2.4));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(2.65)); 
        		break;
        	case RIGHT:
        		addSequential(new MoveDistance(1));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(6.4));
        		addSequential(new TurnDegrees(86));
        		addSequential(new MoveDistance(3.27));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(1));
        		break;
        	}
        	break;
        case MIDDLE:
        	switch(target) {
        	case LEFT:
        		addSequential(new MoveDistance(-.6));
        		addSequential(new TurnDegrees(60.71));
        		addSequential(new MoveDistance(-2.8));
        		addSequential(new TurnDegrees(-60.71));
        		addSequential(new MoveDistance(-.65)); 
        		break;
        	case RIGHT:
        		addSequential(new MoveDistance(-.6));
        		addSequential(new TurnDegrees(-60.71));
        		addSequential(new MoveDistance(-2.8));
        		addSequential(new TurnDegrees(60.71));
        		addSequential(new MoveDistance(-.65)); 
        		break;
			case LEFTSIDE:
		        addSequential(new MoveDistance(.6));
        		addSequential(new TurnDegrees(-41.42));
        		addSequential(new MoveDistance(3.7));
        		addSequential(new TurnDegrees(41.42));
        		addSequential(new MoveDistance(1.22));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(1));
				break;
			case RIGHTSIDE:
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
        case RIGHT:
        	switch(target) {
        	case LEFT:
        		addSequential(new MoveDistance(-1));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(-4.8));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(-2.65));   
        		break;
        	case RIGHT:
        		/*
        		addSequential(new MoveDistance(2.635));
        		addSequential(new TurnDegrees(-45));
        		addSequential(new MoveDistance(1.446));
        		*/
        		addSequential(new MoveDistance(-1));
        		addSequential(new TurnDegrees(45.75));
        		addSequential(new MoveDistance(-2.5));
        		addSequential(new TurnDegrees(-45));
        		addSequential(new MoveDistance(-.61)); 
        		break;
           	case LEFTSIDE:
        		addSequential(new MoveDistance(-1));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(-2.4));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(-2.65)); 
        		break;
        	case RIGHTSIDE:
        		addSequential(new MoveDistance(-1));
        		addSequential(new TurnDegrees(90));
        		addSequential(new MoveDistance(-6.1));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(-3.27));
        		addSequential(new TurnDegrees(-90));
        		addSequential(new MoveDistance(-1));
        		break;
        	}
        	break;
        }
        
        //addSequential(new SetLiftAndElbow(LiftPosition.SWITCH,CubeManager.ElbowState.ELBOW_SWITCH.angle()));
        addSequential(new SetElbowPosition(35));
        addSequential(new Shoot());
    }
}
