package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.intake.Shoot;
import org.usfirst.frc.team4999.robot.Robot;
import org.usfirst.frc.team4999.triggers.CubeManagerTrigger;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CubeManager extends InstantCommand {
	
	private static enum State {
		STOW,
		HUNT,
		CARRY,
		AIM_EXCHANGE,
		AIM_SWITCH,
		AIM_SCALE_LOW,
		AIM_SCALE_HIGH
	};

	private static State currentState = State.STOW;
	
	private CubeManagerTrigger buttonGetter;
	
	public CubeManager(CubeManagerTrigger buttonGetter) {
		this.buttonGetter = buttonGetter;
	}
	
	/*
	 * Buttons:
	 * 1 - intake pushed
	 * 2 - intake released
	 * 3 - Aim up
	 * 4 - Aim down
	 * 5 - Shoot
	 */
    public void initialize() {
    	int button = buttonGetter.getButton();
        switch(currentState) {
        case STOW:
        	if(button == 1) {
        		currentState = State.HUNT;
        		(new Hunt()).start();
        	}
        	break;
        case HUNT:
        	if(button == 2) {
        		currentState = State.CARRY;
        		(new Carry()).start();
        	}
        	break;
        case CARRY:
        	if(button == 1 && !Robot.intake.checkHeld()) {
        		currentState = State.STOW;
        		(new Stow()).start();
        	} else if(button == 3 ) {
        		currentState = State.AIM_EXCHANGE;
        		(new AimExchange()).start();
        	}
        	break;
        case AIM_EXCHANGE:
        	if(button == 3) {
        		currentState = State.AIM_SWITCH;
        		(new AimSwitch()).start();
        	} else if(button == 4) {
        		currentState = State.CARRY;
        		(new Carry()).start();
        	} else if(button == 5) {
        		currentState = State.STOW;
        		(new Shoot()).start();
        		(new Stow()).start();
        	}
        case AIM_SWITCH:
        	if(button == 3) {
        		currentState = State.AIM_SCALE_LOW;
        		(new AimScaleLow()).start();
        	} else if(button == 4) {
        		currentState = State.AIM_EXCHANGE;
        		(new AimExchange()).start();
        	} else if(button == 5) {
        		currentState = State.STOW;
        		(new Shoot()).start();
        		(new Stow()).start();
        	}
        case AIM_SCALE_LOW:
        	if(button == 3) {
        		currentState = State.AIM_SCALE_HIGH;
        		(new AimScaleHigh()).start();
        	} else if(button == 4) {
        		currentState = State.AIM_SWITCH;
        		(new AimSwitch()).start();
        	} else if(button == 5) {
        		currentState = State.STOW;
        		(new Shoot()).start();
        		(new Stow()).start();
        	}
        case AIM_SCALE_HIGH:
        	if(button == 4) {
        		currentState = State.AIM_SCALE_LOW;
        		(new AimScaleLow()).start();
        	} else if(button == 5) {
        		currentState = State.STOW;
        		(new Shoot()).start();
        		(new Stow()).start();
        	}
        default:
        	return;
        }
    }
}
