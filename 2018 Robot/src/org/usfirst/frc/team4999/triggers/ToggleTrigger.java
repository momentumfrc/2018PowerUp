package org.usfirst.frc.team4999.triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;

public abstract class ToggleTrigger extends Trigger {
	
	private boolean enabled = true;
	
	abstract boolean getTrigger();

	@Override
	public boolean get() {
		return enabled & getTrigger();
	}
	
	public void enable() {
		enabled = true;
	}
	public void disable() {
		enabled = false;
	}

}
