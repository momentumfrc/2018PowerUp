package org.usfirst.frc.team4999.commands.intake;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class Release extends InstantCommand {
	
	private Claw claw = Robot.claw;

    public Release() {
        super();
        requires(claw);
    }

    // Called once when the command executes
    protected void initialize() {
    	claw.release();
    }

}