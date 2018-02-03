package org.usfirst.frc.team4999.robot.subsystems;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.commands.lift.KillLift;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Lift extends PIDSubsystem {
	
	static double p = 0, i = 0, d = 0;
	
	NetworkTable pidTable = RobotMap.pidTable.getSubTable("Lift");
	SpeedControllerGroup motors = RobotMap.liftMotors;
	DoubleSolenoid shifter = RobotMap.liftShifter;
	Spark brake = RobotMap.liftBrake;
	
	
	PowerDistributionPanel pdp = RobotMap.pdp;
	
	public boolean braked = false;
	
	private final double liftSpeedLimit = 0.1;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public Lift() {
		super(p,i,d);
		
		// get PID values from PID table
		if(pidTable.containsKey("P"))
			getPIDController().setP(pidTable.getEntry("P").getDouble(p));
		else
			pidTable.getEntry("P").setDouble(p);
		
		if(pidTable.containsKey("I"))
			getPIDController().setI(pidTable.getEntry("I").getDouble(i));
		else
			pidTable.getEntry("I").setDouble(i);
		
		if(pidTable.containsKey("D"))
			getPIDController().setD(pidTable.getEntry("D").getDouble(d));
		else
			pidTable.getEntry("D").setDouble(d);
		
		// Make sure values are persistent
		pidTable.getEntry("P").setPersistent();
		pidTable.getEntry("I").setPersistent();
		pidTable.getEntry("D").setPersistent();
		
		// Update the saved values when the livewindow values are updated
		NetworkTable liveWindowTable = NetworkTableInstance.getDefault().getTable("LiveWindow").getSubTable(getPIDController().getSubsystem()).getSubTable(getPIDController().getName());
		liveWindowTable.getEntry("p").addListener((notification)->savePIDValues(),TableEntryListener.kUpdate|TableEntryListener.kImmediate);
		liveWindowTable.getEntry("i").addListener((notification)->savePIDValues(),TableEntryListener.kUpdate|TableEntryListener.kImmediate);
		liveWindowTable.getEntry("d").addListener((notification)->savePIDValues(),TableEntryListener.kUpdate|TableEntryListener.kImmediate);
		
		// Default to low gear
		shiftLow();
		
		addChild("Lift Motors", motors);
		addChild("Gearbox Shifter", shifter);
		addChild("Brake", brake);
	}
	
	public void savePIDValues() {
		pidTable.getEntry("P").setDouble(getPIDController().getP());
		pidTable.getEntry("I").setDouble(getPIDController().getI());
		pidTable.getEntry("D").setDouble(getPIDController().getD());
	}
	
	public void shiftHigh() {
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	
	public void shiftLow() {
		shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void set(double speed) {
		if(braked)
			motors.set(0);
		else
			motors.set(speed * liftSpeedLimit);
	}
	

    public void initDefaultCommand() {
        setDefaultCommand(new KillLift());
    }

	@Override
	protected double returnPIDInput() {
		return RobotMap.liftEncoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		set(output);
	}
    
}

