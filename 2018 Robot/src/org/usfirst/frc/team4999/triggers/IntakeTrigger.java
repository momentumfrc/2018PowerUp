package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class IntakeTrigger extends ToggleTrigger {

	@Override
    public boolean getTrigger() {
        return Robot.controlChooser.getSelected().getIntake();
    }
}
