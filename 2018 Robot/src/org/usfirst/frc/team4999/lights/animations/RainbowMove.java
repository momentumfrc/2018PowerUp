package org.usfirst.frc.team4999.lights.animations;

import org.usfirst.frc.team4999.lights.Color;
import org.usfirst.frc.team4999.lights.Commands;
import org.usfirst.frc.team4999.lights.Packet;
import org.usfirst.frc.team4999.utils.Utils;

import edu.wpi.first.wpilibj.SpeedController;

public class RainbowMove implements Animation {
	
	SpeedController[] motors;

	public RainbowMove(SpeedController[] motors) {
		this.motors = motors;
	}
	
	private Color fadeBetween(double percent, Color fg, Color bg) {
	    int r = (int)((fg.getRed() - bg.getRed()) * percent) + bg.getRed();
	    int g = (int)((fg.getGreen() - bg.getGreen()) * percent) + bg.getGreen();
	    int b = (int)((fg.getBlue() - bg.getBlue()) * percent) + bg.getBlue();
	    return new Color(r,g,b);
	  }
	  private Color[] rainbow = {
	     new Color(255,0,0),
	     new Color(255, 127, 0),
	     new Color(255,255,0),
	     new Color(0,255,0),
	     new Color(0,0,255),
	     new Color(75,0,130),
	     new Color(148,0,211),
	     new Color(255,255,255)
	  };
	  public Color mapRainbow(double percent) {
	    percent = Utils.clip(percent,0,1);
	    if(Math.abs(percent) < 0.00001) return rainbow[rainbow.length-1];
	    double idx = Utils.map(1-percent, 0, 1, 0, (rainbow.length-1));
	    int i = (int)idx;
	    if(i >=7) System.out.println(percent);
	    return fadeBetween(idx-i,rainbow[i+1],rainbow[i]);
	  }

	@Override
	public Packet[] getNextFrame() {
		double sum = 0;
		for(int i = 0; i < motors.length; i++) {
			sum += motors[i].get();
		}
		sum /= motors.length;
		Packet[] out = {Commands.makeStride(0, mapRainbow(Math.abs(sum)), 1, 1)};
		return out;
	}

	@Override
	public int getFrameDelayMilliseconds() {
		return 100;
	}

}
