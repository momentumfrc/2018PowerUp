package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class FailsafeDrive extends Trigger {

    public boolean get() {
        return Robot.controlChooser.getSelected().getFailsafeDrive();
    }
}
