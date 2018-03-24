package org.usfirst.frc.team4999.commands.lift;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class InstantSetZero extends InstantCommand {

    public InstantSetZero() {
        super();
        requires(Robot.lift);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.lift.setHome();
    }

}
