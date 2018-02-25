package org.usfirst.frc.team4999.utils;

public class Utils {

    public static double clip(double value, double min, double max) {
    	return Math.min(Math.max(value, min), max);
    }
    
    public static double map(double val, double inmin, double inmax, double outmin, double outmax) {
    	return (((val - inmin) / (inmax - inmin)) * (outmax - outmin)) + outmin;
    }

}
