package org.usfirst.frc.team4999.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PersistentException;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MomentumPID implements Sendable {
	
	private double kP, tI, tD, iErrZone;
	private double targetZone;
	private double result;
	private PIDSource source;
	private PIDOutput output;
	private double setpoint;
	private Calculator calc;
	private String name, subsystem = "Ungrouped";
	
	NetworkTableEntry pStore, iStore, dStore;
		
	class Calculator extends Thread {
		private final static double DELAY = 0.05;
		private long lastTime;
		
		public Calculator() {
			super();
			setName(name + " Calculator");
		}
		
		@Override
		public void run() {
			
			lastTime = (long) Timer.getFPGATimestamp() * 1000;
			
			double totalErr = 0, lastErr = 0;
			
			while(!Thread.interrupted()) {
				// Delay 50ms and calculate time for dT
				Timer.delay(DELAY);
				long dTime = (long)(Timer.getFPGATimestamp() * 1000) - lastTime;
				lastTime = (long) Timer.getFPGATimestamp() * 1000;
				
				// Calculate error
				double err = setpoint - source.pidGet();
				
				// Calculate totalErr
				if(Math.abs(err) > iErrZone) {
					totalErr = 0;
				} else {
					totalErr += err * dTime;
				}
				
				// Calculate dErr
				double dErr = (err - lastErr) / dTime;
				lastErr = err;
				
				// Combine all the parts
				if(tI > 0)
					result = kP * (err + totalErr / tI + dErr * tD);
				else
					result = kP * (err + dErr * tD);
				
				// Write the result
				if(output != null)
					output.pidWrite(result);
			}
			result = 0;
			if(output != null)
				output.pidWrite(0);
		}
	}
	
	public MomentumPID(String name, double iErrZone, double targetZone, PIDSource input, PIDOutput output) {
		this(name, MoPrefs.getDefaultP(), MoPrefs.getDefaultI(), MoPrefs.getDefaultD(), iErrZone, targetZone, input, output);
	}

	public MomentumPID(String name, double kP, double tI, double tD, double iErrZone, double targetZone, PIDSource input, PIDOutput output) {
		this.name = name;
		this.iErrZone = iErrZone;
		this.targetZone = targetZone;
		this.source = input;
		setpoint = source.pidGet();
		this.output = output;
		
		loadTable();
		NetworkTable valueStorer = NetworkTableInstance.getDefault().getTable(MoPrefs.getPIDTableName()).getSubTable("name");
		 
		pStore = valueStorer.getEntry("P");
		iStore = valueStorer.getEntry("I");
		dStore = valueStorer.getEntry("D");		
		
		this.kP = pStore.getDouble(kP);
		this.tI = iStore.getDouble(tI);
		this.tD = iStore.getDouble(tD);
		
		//pStore.addListener((notification)->{this.kP = pStore.getDouble(this.kP); saveTable();}, TableEntryListener.kUpdate|TableEntryListener.kImmediate);
		//iStore.addListener((notification)->{this.tI = iStore.getDouble(this.tI); saveTable();}, TableEntryListener.kUpdate|TableEntryListener.kImmediate);
		//dStore.addListener((notification)->{this.tD = dStore.getDouble(this.tD); saveTable();}, TableEntryListener.kUpdate|TableEntryListener.kImmediate);
		
	}
	
	public double getSetpoint() {
		return setpoint;
	}
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}
	
	/**
	 * Sets the setpoint to a value relative to the current position
	 * @param delta The difference between the new setpoint and the current position
	 */
	public void setSetpointRelative(double delta) {
		this.setpoint = source.pidGet() + delta;
	}
	
	public boolean onTarget() {
		return Math.abs(setpoint - source.pidGet()) < targetZone;
	}
	
	/**
	 * Gets the most recent result of the PID calculation
	 * @return The most recent result of the PID calculation
	 */
	public double get() {
		return result;
	}
	
	public void setOutput(PIDOutput output) {
		this.output = output;
	}
	
	public void enable() {
		System.out.format("Enabling... P:%.4f I:%.4f D:%.4f\n",kP,tI,tD);
		calc = new Calculator();
		calc.start();
	}
	public void disable() {
		System.out.println("Disabling");
		if(isEnabled())
			calc.interrupt();
	}
	public void setEnabled(boolean enabled) {
		if(enabled) {
			if(!isEnabled()) {
				enable();
			}
		} else if(isEnabled() && !calc.isInterrupted()) {
			disable();
		}
	}
	public boolean isEnabled() {
		return calc != null && calc.isAlive();
	}
	public double getP() {
		return kP;
	}
	public double getI() {
		return tI;
	}
	public double getD() {
		return tD;
	}
	public double getErrZone() {
		return iErrZone;
	}
	public double getTargetZone() {
		return targetZone;
	}
	
	public void setP(double p) {
		System.out.format("Setting p to %2f\n", p);
		pStore.setDouble(p);
		kP = p;
		saveTable();
	}
	public void setI(double i) {
		iStore.setDouble(i);
		tI = i;
		saveTable();
	}
	public void setD(double d) {
		dStore.setDouble(d);
		tD = d;
		saveTable();
	}
	public void setErrZone(double zone) {
		iErrZone = zone;
	}
	public void setTargetZone(double zone) {
		targetZone = zone;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getSubsystem() {
		return subsystem;
	}

	@Override
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;		
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("PIDController");
		builder.addDoubleProperty("p", this::getP, this::setP);
		builder.addDoubleProperty("i", this::getI, this::setI);
		builder.addDoubleProperty("d", this::getD, this::setD);
		builder.addDoubleProperty("f", this::getErrZone, this::setErrZone); // The smartdashboard expects a PID controller to have a p,i,d,f and doesn't work if its missing something
		//builder.addDoubleProperty("targetzone", this::getTargetZone, this::setTargetZone);
		builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
		builder.addBooleanProperty("enabled", this::isEnabled, this::setEnabled);
	}
	
	private void saveTable() {
		System.out.format("Saving table: %s > %s to %s\n", MoPrefs.getPIDTableName(), name, MoPrefs.getPIDTableLocation() + "." + name);
		try {
			NetworkTableInstance.getDefault().getTable(MoPrefs.getPIDTableName()).getSubTable(name).saveEntries(MoPrefs.getPIDTableLocation() + "." + name);
		} catch (PersistentException e) {
			e.printStackTrace();
		}
	}
	
	private void loadTable() {
		System.out.format("Loading table: %s\n", MoPrefs.getPIDTableLocation() + "." + name);
		try {
			NetworkTableInstance.getDefault().getTable(MoPrefs.getPIDTableName()).getSubTable(name).loadEntries(MoPrefs.getPIDTableLocation() + "." + name);
		} catch (PersistentException e) {
			e.printStackTrace();
		}
	}
	
}
