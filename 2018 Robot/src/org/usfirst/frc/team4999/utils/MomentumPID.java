package org.usfirst.frc.team4999.utils;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MomentumPID implements Sendable {
	
	
	
	private double kP, kI, kD, iErrZone;
	private double targetZone, targetTime;
	private double result;
	private PIDSource source;
	private PIDOutput output;
	private double setpoint;
	private String name, subsystem = "Ungrouped";
	
	private Timer onTargetTimer;
	
	private double totalErr;
	private double lastErr;
	private long lastTime;
	private boolean enabled;
	
	private Runnable updateListener = ()->{};
	
	public MomentumPID(String name, double kP, double kI, double kD, double iErrZone, double targetZone, PIDSource input, PIDOutput output) {
		this.name = name;
		this.iErrZone = iErrZone;
		this.targetZone = targetZone;
		this.source = input;
		setpoint = source.pidGet();
		this.output = output;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		onTargetTimer = new Timer();
		onTargetTimer.start();
		lastTime = (long) Timer.getFPGATimestamp() * 1000;
	}

	
	public void calculate() {
		// calculate time for dT
		long now = (long)(Timer.getFPGATimestamp() * 1000);
		long dTime = now - lastTime;
		lastTime = now;
		
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
		result = kP * err + kI * totalErr + kD * dErr;
		
		// Write the result
		if(output != null)
			output.pidWrite(result);
	}
	
	public void zeroOutput() {
		result = 0;
		if(output != null)
			output.pidWrite(result);
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
	
	public boolean onTargetForTime() {
		if(onTarget() && onTargetTimer.hasPeriodPassed(targetTime)) {
			return true;
		} else if (!onTarget()) {
			onTargetTimer.reset();
		}
		return false;
	}
	
	public void setTargetTime(double time) {
		targetTime = time;
	}
	public double getTargetTime() {
		return targetTime;
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
		System.out.format("Enabling... P:%.4f I:%.4f D:%.4f\n",kP,kI,kD);
		enabled = true;
	}
	public void disable() {
		if(enabled)
			System.out.println("Disabling");
		zeroOutput();
		enabled = false;
	}
	public void setEnabled(boolean enabled) {
		if(enabled)
			enable();
		else
			disable();
	}
	public boolean isEnabled() {
		return enabled;
	}
	public double getP() {
		return kP;
	}
	public double getI() {
		return kI;
	}
	public double getD() {
		return kD;
	}
	public double getErrZone() {
		return iErrZone;
	}
	public double getTargetZone() {
		return targetZone;
	}
	
	public void setP(double p) {
		System.out.format("Setting p to %2f\n", p);
		kP = p;
		updateListener.run();
	}
	public void setI(double i) {
		kI = i;
		updateListener.run();
	}
	public void setD(double d) {
		kD = d;
		updateListener.run();
	}
	public void setErrZone(double zone) {
		iErrZone = zone;
		updateListener.run();
	}
	public void setTargetZone(double zone) {
		targetZone = zone;
		updateListener.run();
	}
	
	public void setListener(Runnable listener) {
		updateListener = listener;
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
		
}
