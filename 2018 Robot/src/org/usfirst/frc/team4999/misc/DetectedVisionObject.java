package org.usfirst.frc.team4999.misc;

import java.awt.Point;

import org.opencv.core.Rect;

public class DetectedVisionObject implements Comparable<DetectedVisionObject> {

	final int xCenter;
	final int yCenter;
	final int width;
	final int height;
	
	public DetectedVisionObject(int xCenter, int yCenter, int width, int height){
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.width = width;
		this.height = height;
	}
	
	public Rect getBoundingBox() {
		return new Rect((int)(xCenter - (width / 2.0)), (int)(yCenter - (height/2.0)), width, height);
	}
	
	public Point getCenter() {
		return new Point(xCenter, yCenter);
	}
	

	public int getArea() {
		return width * height;
	}
	
	@Override
	public String toString() {
		return String.format("DetectedVisionObject[cx:%d,cy:%d,w:%d,h:%d]", xCenter, yCenter, width, height);
	}
	

	@Override
	public int compareTo(DetectedVisionObject o) {
		return getArea() - o.getArea();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof DetectedVisionObject))
			return false;
		DetectedVisionObject obj = (DetectedVisionObject) o;
		return (obj.xCenter == xCenter && obj.yCenter == yCenter && obj.width == width && obj.height == height);
	}
}
