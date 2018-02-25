package org.usfirst.frc.team4999.commands.intake;

import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ToggleArms extends InstantCommand {

	private Claw claw = Robot.claw;

    public ToggleArms() {
        super();
        requires(claw);
    }

    // Called once when the command executes
    protected void initialize() {
    	if(claw.isGripped())
    		claw.release();
    	else
    		claw.grip();
    }

}
