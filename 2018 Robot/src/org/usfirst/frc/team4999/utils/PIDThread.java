package org.usfirst.frc.team4999.utils;

import java.util.ArrayList;

public class PIDThread extends Thread {

	ArrayList<MomentumPID> controllers;
	
	public PIDThread(MomentumPID[] controllers) {
		super();
		this.controllers = new ArrayList<MomentumPID>(controllers.length);
		for(MomentumPID cont : controllers)
			this.controllers.add(cont);
		setName("PID Calculator");
	}
	
	@Override
	public void run() {
		
		while(!Thread.interrupted()) {
			for(MomentumPID cont : controllers) {
				if(cont.isEnabled())
					cont.calculate();
			}
		}
		// Safety measure - When the controller is disabled, set its output to zero
		for(MomentumPID cont : controllers) {
			cont.zeroOutput();
		}
	}
	
	public void addController(MomentumPID cont) {
		controllers.add(cont);
	}

}
