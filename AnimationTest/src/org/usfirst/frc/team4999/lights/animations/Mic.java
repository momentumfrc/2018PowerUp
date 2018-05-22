package org.usfirst.frc.team4999.lights.animations;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.usfirst.frc.team4999.lights.Packet;

public class Mic implements Animation {
	
	TargetDataLine line;

	public Mic() {
		try {
			AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
			line = AudioSystem.getTargetDataLine(format);
			line.start();
		} catch (LineUnavailableException e) {
			return;
		}
	}

	@Override
	public Packet[] getNextFrame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFrameDelayMilliseconds() {
		// TODO Auto-generated method stub
		return 0;
	}

}
