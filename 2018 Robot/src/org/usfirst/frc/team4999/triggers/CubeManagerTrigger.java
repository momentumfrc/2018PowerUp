package org.usfirst.frc.team4999.triggers;

import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class CubeManagerTrigger extends ToggleTrigger {
	
	private int lastButton = 0;
	public int getButton() {
		return lastButton;
	}
	
	@Override
    public boolean getTrigger() {
        lastButton = Robot.controlChooser.getSelected().getCubeManagerButton();
        return lastButton != 0;
    }
}
