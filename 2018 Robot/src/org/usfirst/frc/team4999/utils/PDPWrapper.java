package org.usfirst.frc.team4999.utils;

import org.usfirst.frc.team4999.robot.RobotMap;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

public class PDPWrapper {
	
	private PowerDistributionPanel pdp = RobotMap.pdp;
	
	private long[] timers = new long[16];
	
	private long getTimeMillis() {
		return (long)Timer.getFPGATimestamp() * 1000;
	}

	public PDPWrapper() {
		for(int i = 0; i < timers.length; i++) {
			timers[i] = getTimeMillis();
		}
	}
	
	/**
	 * Checks if a channel of the PDP has been above the specified current for the specified period of time
	 * @param channel Channel of the PDP to check
	 * @param current Cutoff current in amps
	 * @param cutofftime Cutoff time in milliseconds
	 * @return If the channel is over the current limit
	 */
	public boolean checkOvercurrent(int channel, double current, int cutofftime) {
		if(pdp.getCurrent(channel) > current)
			return getTimeMillis() - timers[channel] < cutofftime;
		else
			timers[channel] = getTimeMillis();
		return false;
	}
	
	/**
	 * Checks if some channels of the PDP has been above the specified current for the specified period of time
	 * @param channels Channels of the PDP to check
	 * @param current Cutoff current in amps
	 * @param cutofftime Cutoff time in milliseconds
	 * @return If any of the channels are over the current limit
	 */
	public boolean checkOvercurrent(int[] channels, double current, int cutofftime) {
		for(int channel : channels) {
			if(checkOvercurrent(channel, current, cutofftime))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if a channel of the PDP has been below the specified current for the specified period of time
	 * @param channel Channel of the PDP to check
	 * @param current Cutoff current in amps
	 * @param cutofftime Cutoff time in milliseconds
	 * @return If the channel is under the current limit
	 */
	public boolean checkUndercurrent(int channel, double current, int cutofftime) {
		if(pdp.getCurrent(channel) < current)
			return getTimeMillis() - timers[channel] < cutofftime;
		else
			timers[channel] = getTimeMillis();
		return false;
	}
	
	/**
	 * Checks if some channels of the PDP has been above the specified current for the specified period of time
	 * @param channels Channels of the PDP to check
	 * @param current Cutoff current in amps
	 * @param cutofftime Cutoff time in milliseconds
	 * @return If any of the channels are over the current limit
	 */
	public boolean checkUndercurrent(int[] channels, double current, int cutofftime) {
		for(int channel : channels) {
			if(checkUndercurrent(channel, current, cutofftime))
				return true;
		}
		return false;
	}

}
