package org.usfirst.frc.team4999.commands.cubemanager;

import org.usfirst.frc.team4999.commands.elbow.TeleopElbowPID;
import org.usfirst.frc.team4999.commands.intake.Drop;
import org.usfirst.frc.team4999.commands.intake.Shoot;
import org.usfirst.frc.team4999.commands.lift.ManualLift;
import org.usfirst.frc.team4999.commands.lift.TeleopLift;
import org.usfirst.frc.team4999.robot.controllers.LiftPosition;
import org.usfirst.frc.team4999.triggers.CubeManagerTrigger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CubeManager extends InstantCommand {
	
	private enum LiftState {
		LIFT_STOW,
		LIFT_CARRY,
		LIFT_GROUND,
		LIFT_EXCHANGE,
		LIFT_SWITCH,
		LIFT_SCALE_LOW,
		LIFT_SCALE_HIGH,
		UNKNOWN
	};
	private enum ElbowState {
		ELBOW_STOW(5),
		ELBOW_GROUND(90),
		ELBOW_CARRY(15),
		ELBOW_EXCHANGE(90),
		ELBOW_SWITCH(45),
		ELBOW_SCALE_LOW(45),
		ELBOW_SCALE_HIGH(45),
		UNKNOWN(-1);
		private final double angle;
		public double angle() {
			return angle;
		}
		ElbowState(double angle){
			this.angle = angle;
		}
		
	};
	private enum SystemState {
		STOW,
		HUNT,
		CARRY,
		LIFT,
		AIM,
		MANUAL_RAISE,
		RELEASED,
		MANUAL_LOWER,
		ELBOW_STOWED_LIFT_HELD
	};
	
	private static LiftState lift = LiftState.LIFT_STOW;
	private static ElbowState elbow = ElbowState.ELBOW_STOW;
	private static SystemState system = SystemState.STOW;

	private CubeManagerTrigger buttonGetter;
	
	public CubeManager(CubeManagerTrigger buttonGetter) {
		super();
		this.buttonGetter = buttonGetter;
	}

    // Called once when the command executes
    protected void initialize() {
    	
    	int button = buttonGetter.getButton();
    	
    	switch(system) {
    	case STOW:
    		if(button == 1) {
    			system = SystemState.HUNT;
    			lift = LiftState.LIFT_GROUND;
    			elbow = ElbowState.ELBOW_GROUND;
    			(new Hunt(LiftPosition.GROUND, elbow.angle())).start();
    		} else if(button == 7) {
    			system = SystemState.MANUAL_LOWER;
    			lift = LiftState.UNKNOWN;
    			elbow = ElbowState.UNKNOWN;
    			(new ManualLift()).start();
    			(new TeleopElbowPID()).start();
    		}
    		break;
    	case HUNT:
    		if(button == 2) {
    			system = SystemState.CARRY;
    			lift = LiftState.LIFT_CARRY;
    			elbow = ElbowState.ELBOW_CARRY;
    			(new Carry(LiftPosition.CARRY,elbow.angle())).start();
    		}
    		break;
    	case CARRY:
    		switch(button) {
    		case 3:
    			system = SystemState.LIFT;
    			lift = LiftState.LIFT_EXCHANGE;
    			elbow = ElbowState.ELBOW_CARRY;
    			(new SetLiftAndElbow(LiftPosition.EXCHANGE, elbow.angle())).start();
    			break;
    		case 7:
    			system = SystemState.MANUAL_RAISE;
    			lift = LiftState.UNKNOWN;
    			elbow = ElbowState.UNKNOWN;
    			(new ManualLift()).start();
    			(new TeleopElbowPID()).start();
    			break;
    		case 1:
    			system = SystemState.STOW;
    			lift = LiftState.LIFT_STOW;
    			elbow = ElbowState.ELBOW_STOW;
    			(new SetLiftAndElbow(LiftPosition.STOW, elbow.angle())).start();
    			break;
    		}
    		break;
    	case LIFT:
    		switch(lift) {
    		case LIFT_EXCHANGE:
    			if(button == 4) {
    				system = SystemState.CARRY;
    				lift = LiftState.LIFT_CARRY;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.CARRY, elbow.angle())).start();
    			} else if(button == 3) {
    				lift = LiftState.LIFT_SWITCH;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.SWITCH, elbow.angle())).start();
    			} else if(button == 5) {
    				system = SystemState.AIM;
    				elbow = ElbowState.ELBOW_EXCHANGE;
    				(new SetLiftAndElbow(LiftPosition.EXCHANGE, elbow.angle())).start();
    			}
    			break;
    		case LIFT_SWITCH:
    			if(button == 4) {
    				lift = LiftState.LIFT_EXCHANGE;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.EXCHANGE, elbow.angle())).start();
    			} else if (button == 3) {
    				lift = LiftState.LIFT_SCALE_LOW;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.SCALE_LOW, elbow.angle())).start();
    			} else if(button == 5) {
    				system = SystemState.AIM;
    				elbow = ElbowState.ELBOW_SWITCH;
    				(new SetLiftAndElbow(LiftPosition.SWITCH, elbow.angle())).start();
    			}
    			break;
    		case LIFT_SCALE_LOW:
    			if(button == 4) {
    				lift = LiftState.LIFT_SWITCH;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.SWITCH, elbow.angle())).start();
    			} else if (button == 3) {
    				lift = LiftState.LIFT_SCALE_HIGH;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.SCALE_HIGH, elbow.angle())).start();
    			} else if(button == 5) {
    				system = SystemState.AIM;
    				elbow = ElbowState.ELBOW_SCALE_LOW;
    				(new SetLiftAndElbow(LiftPosition.SCALE_LOW, elbow.angle())).start();
    			}
    			break;
    		case LIFT_SCALE_HIGH:
    			if(button == 4) {
    				lift = LiftState.LIFT_SCALE_LOW;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.SCALE_LOW, elbow.angle())).start();
    			} else if(button == 5) {
    				system = SystemState.AIM;
    				elbow = ElbowState.ELBOW_SCALE_HIGH;
    				(new SetLiftAndElbow(LiftPosition.SCALE_HIGH, elbow.angle())).start();
    			}
    			break;
			default:
				break;
    		}
    		break;
    	case AIM:
    		if(button == 5) {
				system = SystemState.RELEASED;
				(new Shoot()).start();
			} else if(button == 6) {
				system = SystemState.RELEASED;
				(new Drop()).start();
			} else if(button == 1){
	    		switch(elbow) {
	    		case ELBOW_EXCHANGE:
    				system = SystemState.LIFT;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.EXCHANGE, elbow.angle())).start();
    				break;
	    		case ELBOW_SWITCH:
	    			system = SystemState.LIFT;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.SWITCH, elbow.angle())).start();
    				break;
	    		case ELBOW_SCALE_LOW:
	    			system = SystemState.LIFT;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.SCALE_LOW, elbow.angle())).start();
    				break;
	    		case ELBOW_SCALE_HIGH:
	    			system = SystemState.LIFT;
    				elbow = ElbowState.ELBOW_CARRY;
    				(new SetLiftAndElbow(LiftPosition.SCALE_HIGH, elbow.angle())).start();
    				break;
				default:
					break;
	    		}
			}
    		break;
    	case MANUAL_RAISE:
    		if(button == 5) {
				system = SystemState.RELEASED;
				(new Shoot()).start();
			} else if(button == 6) {
				system = SystemState.RELEASED;
				(new Drop()).start();
			}
    		break;
    	case RELEASED:
    		if(button == 7) {
    			system = SystemState.MANUAL_LOWER;
    			lift = LiftState.UNKNOWN;
    			elbow = ElbowState.UNKNOWN;
    			(new ManualLift()).start();
    			(new TeleopElbowPID()).start();
    		} else if(button == 1) {
    			system = SystemState.ELBOW_STOWED_LIFT_HELD;
    			elbow = ElbowState.ELBOW_STOW;
    			(new SetElbow(elbow.angle())).start();
    		}
    		break;
    	case MANUAL_LOWER:
    	case ELBOW_STOWED_LIFT_HELD:
    		if(button == 4) {
    			system = SystemState.STOW;
    			lift = LiftState.LIFT_STOW;
    			elbow = ElbowState.ELBOW_STOW;
    			(new SetLiftAndElbow(LiftPosition.STOW, elbow.angle())).start();
    		}
    		break;
    	}
    }

}
