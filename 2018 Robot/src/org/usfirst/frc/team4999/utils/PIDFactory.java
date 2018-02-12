package org.usfirst.frc.team4999.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.usfirst.frc.team4999.robot.RobotMap;
import org.usfirst.frc.team4999.robot.sensors.GyroFusion;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDFactory {
	
	private static final String BASE = "/home/lvuser/";
	private static final String MOVE_FILE = "movePID.properties";
	private static final String TURN_FILE = "turnPID.properties";
	private static final String MOVE_RATE_FILE = "moveRatePID.properties";
	private static final String TURN_RATE_FILE = "turnRatePID.properties";
	private static final String TILT_FILE = "tiltPID.properties";
	
	private static final String DEFAULT_P = "0";
	private static final String DEFAULT_I = "0";
	private static final String DEFAULT_D = "0";
	private static final String DEFAULT_ERR_ZONE = "10";
	private static final String DEFAULT_TARGET_ZONE = "3";
	private static final String DEFAULT_TARGET_TIME = "0.5";
	
	private static Properties openFile(String file) {
		System.out.println("Loading " + file);
		Properties ret = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			ret.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	private static void saveFile(Properties props, String file) {
		System.out.println("Saving " + file);
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			props.store(output, null);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
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
	
	private static boolean checkFile(String file) {
		File f = new File(file);
		return f.exists() && !f.isDirectory();
	}
	
	static class AverageEncoder implements PIDSource{
		private Encoder left, right;

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
			return RobotMap.gyro.getPitch();
		}
		
	}
	
	public static MomentumPID getMovePID() {
		MomentumPID ret;
		String path = BASE + MOVE_FILE;
		if(checkFile(path)) {
			Properties props = openFile(path);
			double p = Double.parseDouble(props.getProperty("P", DEFAULT_P));
			double i = Double.parseDouble(props.getProperty("I", DEFAULT_I));
			double d = Double.parseDouble(props.getProperty("D", DEFAULT_D));
			double errZone = Double.parseDouble(props.getProperty("Error Zone", DEFAULT_ERR_ZONE));
			double targetZone = Double.parseDouble(props.getProperty("Target Zone", DEFAULT_TARGET_ZONE));
			double targetTime = Double.parseDouble(props.getProperty("Target Time", DEFAULT_TARGET_TIME));
			ret = new MomentumPID("MovePID",p,i,d,errZone,targetZone,new AverageEncoder(RobotMap.leftDriveEncoder, RobotMap.rightDriveEncoder), null);
			ret.setTargetTime(targetTime);
		} else {
			double p = Double.parseDouble(DEFAULT_P);
			double i = Double.parseDouble(DEFAULT_I);
			double d = Double.parseDouble(DEFAULT_D);
			double errZone = Double.parseDouble(DEFAULT_ERR_ZONE);
			double targetZone = Double.parseDouble(DEFAULT_TARGET_ZONE);
			double targetTime = Double.parseDouble(DEFAULT_TARGET_TIME);
			ret = new MomentumPID("MovePID",p,i,d,errZone,targetZone,new AverageEncoder(RobotMap.leftDriveEncoder, RobotMap.rightDriveEncoder), null);
			ret.setTargetTime(targetTime);
		}
		ret.setListener(()->saveMovePID(ret));
		return ret;
	}
	
	public static void saveMovePID(MomentumPID pid) {
		savePID(pid, BASE+MOVE_FILE);
	}
	public static MomentumPID getTurnPID() {
		MomentumPID ret;
		String path = BASE + TURN_FILE;
		if(checkFile(path)) {
			Properties props = openFile(path);
			double p = Double.parseDouble(props.getProperty("P", DEFAULT_P));
			double i = Double.parseDouble(props.getProperty("I", DEFAULT_I));
			double d = Double.parseDouble(props.getProperty("D", DEFAULT_D));
			double errZone = Double.parseDouble(props.getProperty("Error Zone", DEFAULT_ERR_ZONE));
			double targetZone = Double.parseDouble(props.getProperty("Target Zone", DEFAULT_TARGET_ZONE));
			double targetTime = Double.parseDouble(props.getProperty("Target Time", DEFAULT_TARGET_TIME));
			ret = new MomentumPID("TurnPID",p,i,d,errZone,targetZone,RobotMap.gyro, null);
			ret.setTargetTime(targetTime);
		} else {
			double p = Double.parseDouble(DEFAULT_P);
			double i = Double.parseDouble(DEFAULT_I);
			double d = Double.parseDouble(DEFAULT_D);
			double errZone = Double.parseDouble(DEFAULT_ERR_ZONE);
			double targetZone = Double.parseDouble(DEFAULT_TARGET_ZONE);
			double targetTime = Double.parseDouble(DEFAULT_TARGET_TIME);
			ret = new MomentumPID("TurnPID",p,i,d,errZone,targetZone,RobotMap.gyro, null);
			ret.setTargetTime(targetTime);
		}
		ret.setListener(()->saveTurnPID(ret));
		return ret;
	}
	
	public static void saveTurnPID(MomentumPID pid) {
		savePID(pid, BASE+TURN_FILE);
	}
	
	public static MomentumPID getMoveRatePID() {
		MomentumPID ret;
		String path = BASE + MOVE_RATE_FILE;
		PIDSource source = new AverageEncoder(RobotMap.leftDriveEncoder, RobotMap.rightDriveEncoder);
		source.setPIDSourceType(PIDSourceType.kRate);
		if(checkFile(path)) {
			Properties props = openFile(path);
			double p = Double.parseDouble(props.getProperty("P", DEFAULT_P));
			double i = Double.parseDouble(props.getProperty("I", DEFAULT_I));
			double d = Double.parseDouble(props.getProperty("D", DEFAULT_D));
			double errZone = Double.parseDouble(props.getProperty("Error Zone", DEFAULT_ERR_ZONE));
			double targetZone = Double.parseDouble(props.getProperty("Target Zone", DEFAULT_TARGET_ZONE));
			double targetTime = Double.parseDouble(props.getProperty("Target Time", DEFAULT_TARGET_TIME));
			ret = new MomentumPID("MoveRatePID",p,i,d,errZone,targetZone,source, null);
			ret.setTargetTime(targetTime);
		} else {
			double p = Double.parseDouble(DEFAULT_P);
			double i = Double.parseDouble(DEFAULT_I);
			double d = Double.parseDouble(DEFAULT_D);
			double errZone = Double.parseDouble(DEFAULT_ERR_ZONE);
			double targetZone = Double.parseDouble(DEFAULT_TARGET_ZONE);
			double targetTime = Double.parseDouble(DEFAULT_TARGET_TIME);
			ret = new MomentumPID("MoveRatePID",p,i,d,errZone,targetZone,source, null);
			ret.setTargetTime(targetTime);
		}
		ret.setListener(()->saveMoveRatePID(ret));
		return ret;
	}
	public static void saveMoveRatePID(MomentumPID pid) {
		savePID(pid, BASE + MOVE_RATE_FILE);
	}
	
	public static MomentumPID getTurnRatePID() {
		MomentumPID ret;
		String path = BASE + TURN_RATE_FILE;
		PIDSource source = new GyroFusion();
		source.setPIDSourceType(PIDSourceType.kRate);
		if(checkFile(path)) {
			Properties props = openFile(path);
			double p = Double.parseDouble(props.getProperty("P", DEFAULT_P));
			double i = Double.parseDouble(props.getProperty("I", DEFAULT_I));
			double d = Double.parseDouble(props.getProperty("D", DEFAULT_D));
			double errZone = Double.parseDouble(props.getProperty("Error Zone", DEFAULT_ERR_ZONE));
			double targetZone = Double.parseDouble(props.getProperty("Target Zone", DEFAULT_TARGET_ZONE));
			double targetTime = Double.parseDouble(props.getProperty("Target Time", DEFAULT_TARGET_TIME));
			ret = new MomentumPID("TurnPID",p,i,d,errZone,targetZone,source, null);
			ret.setTargetTime(targetTime);
		} else {
			double p = Double.parseDouble(DEFAULT_P);
			double i = Double.parseDouble(DEFAULT_I);
			double d = Double.parseDouble(DEFAULT_D);
			double errZone = Double.parseDouble(DEFAULT_ERR_ZONE);
			double targetZone = Double.parseDouble(DEFAULT_TARGET_ZONE);
			double targetTime = Double.parseDouble(DEFAULT_TARGET_TIME);
			ret = new MomentumPID("TurnPID",p,i,d,errZone,targetZone,source, null);
			ret.setTargetTime(targetTime);
		}
		ret.setListener(()->saveTurnRatePID(ret));
		return ret;
	}
	public static void saveTurnRatePID(MomentumPID pid) {
		savePID(pid, BASE + TURN_RATE_FILE);
	}
	
	public static MomentumPID getTiltPID() {
		MomentumPID ret;
		String path = BASE + TILT_FILE;
		PIDSource source = new GyroPitch();
		if(checkFile(path)) {
			Properties props = openFile(path);
			double p = Double.parseDouble(props.getProperty("P", DEFAULT_P));
			double i = Double.parseDouble(props.getProperty("I", DEFAULT_I));
			double d = Double.parseDouble(props.getProperty("D", DEFAULT_D));
			double errZone = Double.parseDouble(props.getProperty("Error Zone", DEFAULT_ERR_ZONE));
			double targetZone = Double.parseDouble(props.getProperty("Target Zone", DEFAULT_TARGET_ZONE));
			double targetTime = Double.parseDouble(props.getProperty("Target Time", DEFAULT_TARGET_TIME));
			ret = new MomentumPID("TiltPID",p,i,d,errZone,targetZone,source, null);
			ret.setTargetTime(targetTime);
		} else {
			double p = Double.parseDouble(DEFAULT_P);
			double i = Double.parseDouble(DEFAULT_I);
			double d = Double.parseDouble(DEFAULT_D);
			double errZone = Double.parseDouble(DEFAULT_ERR_ZONE);
			double targetZone = Double.parseDouble(DEFAULT_TARGET_ZONE);
			double targetTime = Double.parseDouble(DEFAULT_TARGET_TIME);
			ret = new MomentumPID("TiltPID",p,i,d,errZone,targetZone,source, null);
			ret.setTargetTime(targetTime);
		}
		ret.setListener(()->saveTiltPID(ret));
		return ret;
	}
	public static void saveTiltPID(MomentumPID pid) {
		savePID(pid, BASE + TILT_FILE);
	}

}
