package org.usfirst.frc.team4999.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.sensors.GyroFusion;
import org.usfirst.frc.team4999.robot.sensors.GyroFusion.Sensor;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDFactory {
	
	private static final String BASE = "/home/lvuser/";
	private static final String MOVE_FILE = "movePID.properties";
	private static final String TURN_FILE = "turnPID.properties";
	private static final String TILT_FILE = "tiltPID.properties";
	private static final String FAST_LIFT_FILE = "fastLiftPID.properties";
	private static final String SLOW_LIFT_FILE = "slowLiftPID.properties";
	private static final String ELBOW_FILE = "elbowPID.properties";
	
	private static final String DEFAULT_P = "0";
	private static final String DEFAULT_I = "0";
	private static final String DEFAULT_D = "0";
	private static final String DEFAULT_ERR_ZONE = "10";
	private static final String DEFAULT_TARGET_ZONE = "3";
	private static final String DEFAULT_TARGET_TIME = "0.5";
	
	private static final PIDThread calculator = new PIDThread(7);
	
	public static void addToCalculator(MomentumPID cont) {
		calculator.addController(cont);
		if(!calculator.isAlive() && !calculator.isDead())
			calculator.start();
	}
	
	private static Properties openFile(String file) {
		System.out.println("Loading " + file);
		Properties ret = new Properties();
		try (FileInputStream input = new FileInputStream(file)) {
			ret.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return ret;
	}
	
	private static void saveFile(Properties props, String file) {
		System.out.println("Saving " + file);
		try (FileOutputStream output = new FileOutputStream(file)) {
			props.store(output, "PID Values for a PID controller");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void savePID(MomentumPID pid, String path) {
		Properties props = new Properties();
		props.put("P", String.format("%f", pid.getP()));
		props.put("I", String.format("%f", pid.getI()));
		props.put("D", String.format("%f", pid.getD()));
		props.put("Error Zone", String.format("%f", pid.getErrZone()));
		props.put("Target Zone", String.format("%f", pid.getTargetZone()));
		props.put("Target Time", String.format("%f", pid.getTargetTime()));
		saveFile(props, path);
	}
	
	private static MomentumPID loadPID(String name, String path, PIDSource source, PIDOutput output) {
		MomentumPID ret;
		if(checkFile(path)) {
			Properties props = openFile(path);
			double p = Double.parseDouble(props.getProperty("P", DEFAULT_P));
			double i = Double.parseDouble(props.getProperty("I", DEFAULT_I));
			double d = Double.parseDouble(props.getProperty("D", DEFAULT_D));
			double errZone = Double.parseDouble(props.getProperty("Error Zone", DEFAULT_ERR_ZONE));
			double targetZone = Double.parseDouble(props.getProperty("Target Zone", DEFAULT_TARGET_ZONE));
			double targetTime = Double.parseDouble(props.getProperty("Target Time", DEFAULT_TARGET_TIME));
			ret = new MomentumPID(name,p,i,d,errZone,targetZone,source, output);
			ret.setTargetTime(targetTime);
		} else {
			double p = Double.parseDouble(DEFAULT_P);
			double i = Double.parseDouble(DEFAULT_I);
			double d = Double.parseDouble(DEFAULT_D);
			double errZone = Double.parseDouble(DEFAULT_ERR_ZONE);
			double targetZone = Double.parseDouble(DEFAULT_TARGET_ZONE);
			double targetTime = Double.parseDouble(DEFAULT_TARGET_TIME);
			ret = new MomentumPID(name,p,i,d,errZone,targetZone,source, output);
			ret.setTargetTime(targetTime);
		}
		return ret;
	}
	private static boolean checkFile(String file) {
		File f = new File(file);
		return f.exists() && !f.isDirectory();
	}
	
	static class AverageEncoder implements PIDSource{
		private Encoder left, right;
		
		private static final int SAMPLES = 10;

		private PIDSourceType sourcetype = PIDSourceType.kDisplacement;


		
		public AverageEncoder(Encoder left, Encoder right) {
			this.left = left;
			this.right = right;
		}
		
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			sourcetype = pidSource;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return sourcetype;
		}

		@Override
		public double pidGet() {
			switch(sourcetype) {
			case kRate:
				return (left.getRate() + right.getRate()) / 2;
			case kDisplacement:
			default:
				return (left.getDistance() + right.getDistance()) / 2;
			}
		}
		
	}
	
	static class GyroPitch implements PIDSource {
		PIDSourceType type = PIDSourceType.kDisplacement;
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			type = pidSource;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return type;
		}
		
		@Override
		public double pidGet() {
			double angle = RobotMap.gyro.getPitch();
			if(RobotMap.gyro.currentSensor() == Sensor.ADIS)
				angle = 0;
			double tiltRange = MoPrefs.getTiltRange();
			//System.out.format("Range:%.2f Angle:%.2f\n", tiltRange, angle);
			if(angle > tiltRange)
				return angle - tiltRange;
			else if(angle < -tiltRange)
				return angle + tiltRange;
			else
				return 0;
		}
		
	}
	
	public static MomentumPID getMovePID() {
		MomentumPID ret;
		String path = BASE + MOVE_FILE;
		//PIDSource source = new AverageEncoder(RobotMap.leftDriveEncoder, RobotMap.rightDriveEncoder);
		PIDSource source = RobotMap.leftDriveEncoder;
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		ret = loadPID("MovePID",path, source, null);
		ret.setListener(()->saveMovePID(ret));
		addToCalculator(ret);
		return ret;
	}
	
	public static void saveMovePID(MomentumPID pid) {
		savePID(pid, BASE+MOVE_FILE);
	}
	public static MomentumPID getTurnPID() {
		MomentumPID ret;
		String path = BASE + TURN_FILE;
		PIDSource source = new GyroFusion();
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		ret = loadPID("TurnPID",path, source, null);
		ret.setListener(()->saveTurnPID(ret));
		addToCalculator(ret);
		return ret;
	}
	
	public static void saveTurnPID(MomentumPID pid) {
		savePID(pid, BASE+TURN_FILE);
	}
	
	public static MomentumPID getTiltPID() {
		MomentumPID ret;
		String path = BASE + TILT_FILE;
		PIDSource source = new GyroPitch();
		ret = loadPID("TiltPID",path, source, null);
		ret.setListener(()->saveTiltPID(ret));
		addToCalculator(ret);
		return ret;
	}
	public static void saveTiltPID(MomentumPID pid) {
		savePID(pid, BASE + TILT_FILE);
	}
	
	public static MomentumPID getFastLiftPID() {
		MomentumPID ret;
		String path = BASE + FAST_LIFT_FILE;
		PIDSource source = RobotMap.liftEncoder;
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		ret = loadPID("FastLiftPID",path, source, null);
		ret.setListener(()->saveFastLiftPID(ret));
		addToCalculator(ret);
		return ret;
	}
	public static void saveFastLiftPID(MomentumPID pid) {
		savePID(pid, BASE + FAST_LIFT_FILE);
	}
	public static MomentumPID getSlowLiftPID() {
		MomentumPID ret;
		String path = BASE + SLOW_LIFT_FILE;
		PIDSource source = RobotMap.liftEncoder;
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		ret = loadPID("SlowLiftPID",path, source, null);
		ret.setListener(()->saveSlowLiftPID(ret));
		addToCalculator(ret);
		return ret;
	}
	public static void saveSlowLiftPID(MomentumPID pid) {
		savePID(pid, BASE + SLOW_LIFT_FILE);
	}
	
	public static MomentumPID getElbowPID() {
		MomentumPID ret;
		String path = BASE + ELBOW_FILE;
		PIDSource source = RobotMap.elbowEncoder;
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		ret = loadPID("ElbowPID",path, source, null);
		ret.setListener(()->saveElbowPID(ret));
		addToCalculator(ret);
		return ret;
	}
	public static void saveElbowPID(MomentumPID pid) {
		savePID(pid, BASE + ELBOW_FILE);
	}

}
