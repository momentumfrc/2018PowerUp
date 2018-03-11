package org.usfirst.frc.team4999.commands;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class FailsafeCubes extends InstantCommand {

    public FailsafeCubes() {
        super();
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.m_oi.disableCubeManager();
    }

}
