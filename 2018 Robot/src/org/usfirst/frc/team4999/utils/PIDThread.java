package org.usfirst.frc.team4999.utils;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class PIDThread extends Thread {
	
	private final static double DELAY = 0.05;

	ArrayList<MomentumPID> controllers;
	
	private boolean dead = false;
	
	public PIDThread(MomentumPID[] controllers) {
		super();
		this.controllers = new ArrayList<MomentumPID>(controllers.length);
		for(MomentumPID cont : controllers)
			this.controllers.add(cont);
		setName("PID Calculator");
	}
	public PIDThread(int size) {
		super();
		this.controllers = new ArrayList<MomentumPID>(size);
		setName("PID Calculator");
	}
	
	@Override
	public void run() {
		
		while(!Thread.interrupted()) {
			
			Timer.delay(DELAY);
			
			synchronized(controllers) {
				for(MomentumPID cont : controllers) {
					if(cont.isEnabled())
						cont.calculate();
				}
			}
		}
		// Safety measure - When the controller is disabled, set its output to zero
		for(MomentumPID cont : controllers) {
			cont.zeroOutput();
		}
		dead = true;
	}
	
	public void addController(MomentumPID cont) {
		synchronized(controllers) {
			controllers.add(cont);
		}
	}
	
	public boolean isDead() {
		return dead;
	}

}
