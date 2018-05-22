package org.usfirst.frc.team4999.triggers;

/**
 *
 */
public class ButtonTrigger extends ToggleTrigger {
	
	private ButtonInterface trigger;
	
	public ButtonTrigger(ButtonInterface trigger) {
		this.trigger = trigger;
	}
	

	@Override
	boolean getTrigger() {
		return trigger.get();
	}
}
