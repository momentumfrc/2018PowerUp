package org.usfirst.frc.team4999.utils;

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
	
	class Calculator extends Thread {
		private final static double DELAY = 0.05;
		private long lastTime;
		
		public Calculator() {
			super();
		}
		
		@Override
		public void run() {
			
			lastTime = (long) Timer.getFPGATimestamp() * 1000;
			
			double totalErr = 0, lastErr = 0;
			
			while(!Thread.interrupted()) {
				// Delay 50ms and calculate time for dT
				Timer.delay(DELAY);
				double dTime = (long)(Timer.getFPGATimestamp() * 1000) - lastTime;
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
				result = kP * (err + totalErr / tI + dErr * tD);
				
				// Write the result
				if(output != null)
					output.pidWrite(result);
			}
		}
	}

	public MomentumPID(String name, double kP, double tI, double tD, double iErrZone, double targetZone, PIDSource input, PIDOutput output) {
		this.name = name;
		this.kP = kP;
		this.tI = tI;
		this.tD = tD;
		this.iErrZone = iErrZone;
		this.targetZone = targetZone;
		setpoint = source.pidGet();
		this.source = input;
		this.output = output;
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
	
	public void enable() {
		calc = new Calculator();
		calc.start();
	}
	public void disable() {
		calc.interrupt();
	}
	public void setEnabled(boolean enabled) {
		if(enabled) {
			if(!calc.isAlive()) {
				enable();
			}
		} else if(calc.isAlive() && !calc.isInterrupted()) {
			disable();
		}
	}
	public boolean isEnabled() {
		return calc.isAlive();
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
		kP = p;
	}
	public void setI(double i) {
		tI = i;
	}
	public void setD(double d) {
		tD = d;
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
		builder.addDoubleProperty("errzone", this::getErrZone, this::setErrZone);
		builder.addDoubleProperty("targetzone", this::getTargetZone, this::setTargetZone);
		builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
		builder.addBooleanProperty("enabled", this::isEnabled, this::setEnabled);
	}
	
}
