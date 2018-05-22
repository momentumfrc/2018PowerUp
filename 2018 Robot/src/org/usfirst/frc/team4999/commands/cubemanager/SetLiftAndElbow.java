package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.MaintainElbowPosition;
import org.usfirst.frc.team4999.commands.elbow.SetElbowPosition;
import org.usfirst.frc.team4999.commands.lift.MaintainLiftHeight;
import org.usfirst.frc.team4999.commands.lift.SetLiftHeight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetLiftAndElbow extends CommandGroup {
	
	private class Lift extends CommandGroup {
		public Lift(double lift) {
	    	addSequential(new SetLiftHeight(lift, true));
	    	addSequential(new MaintainLiftHeight());
		}
	}

    public SetLiftAndElbow(double lift, double elbow) {
    	addParallel(new Lift(lift));
    	addParallel(new SetElbow(elbow));
    }
}
