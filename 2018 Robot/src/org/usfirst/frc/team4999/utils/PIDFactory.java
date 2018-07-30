package org.usfirst.frc.team4999.utils;

import org.usfirst.frc.team4999.pid.*;
import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.sensors.GyroFusion;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDFactory extends MomentumPIDFactoryBase {
	
	private static final String MOVE_FILE = "movePID.properties";
	private static final String TURN_FILE = "turnPID.properties";
	private static final String FAST_LIFT_FILE = "fastLiftPID.properties";
	private static final String SLOW_LIFT_FILE = "slowLiftPID.properties";
	private static final String ELBOW_FILE = "elbowPID.properties";
	
	
	public static MomentumPID getMovePID() {
		String path = LOCATION + MOVE_FILE;
		PIDSource source = RobotMap.leftDriveEncoder;
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		return loadPID("MovePID", path, source, null);
	}
	
	public static MomentumPID getTurnPID() {
		String path = LOCATION + TURN_FILE;
		PIDSource source = new GyroFusion();
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		return loadPID("TurnPID",path, source, null);
	}
	
	public static MomentumPID getFastLiftPID() {
		String path = LOCATION + FAST_LIFT_FILE;
		PIDSource source = RobotMap.liftEncoder;
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		return loadPID("FastLiftPID",path, source, null);
	}
	public static MomentumPID getSlowLiftPID() {
		String path = LOCATION + SLOW_LIFT_FILE;
		PIDSource source = RobotMap.liftEncoder;
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		return loadPID("SlowLiftPID",path, source, null);
	}
	
	public static MomentumPID getElbowPID() {
		String path = LOCATION + ELBOW_FILE;
		PIDSource source = RobotMap.elbowEncoder;
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		return loadPID("ElbowPID",path, source, null);
	}

}
