package org.usfirst.frc.team4999.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;

import org.usfirst.frc.team4999.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class KillLift extends InstantCommand {
	
	private Lift lift = Robot.lift;

    public KillLift() {
        super();
        requires(lift);
    }

    // Called once when the command executes
    protected void initialize() {
    	lift.disablePID();
    	lift.set(0);
    	lift.brake();
    }

}
