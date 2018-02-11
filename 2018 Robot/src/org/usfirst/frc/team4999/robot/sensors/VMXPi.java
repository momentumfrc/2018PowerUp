package org.usfirst.frc.team4999.robot.sensors;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class VMXPi implements PIDSource {

    private static int PORT = 5800;
    private static int PACKET_LENGTH = 32;
    
	private DatagramSocket server;
	DatagramPacket packet = new DatagramPacket(new byte[PACKET_LENGTH], PACKET_LENGTH);
	
	private Thread recieveLoop;
	
	private double zAngle, zRate, pitch, roll, xVelocity, yVelocity;
	private double zOffset;
	
	// When two synchronized blocks are synchronized on the same object, only one block is allowed to run at a time.
	// By synchronizing before we read and write the zAngle and zRates, we can make sure they don't change while we're reading them
	private Object syncLock = new Object();
	
	private PIDSourceType m_source = PIDSourceType.kDisplacement;
	
	private long millis;
    
	private static VMXPi instance;
	
	public static VMXPi getInstance() {
		if(instance == null) {
			instance = new VMXPi();
		}
		return instance;
	}
	
	private VMXPi() {
		try {
			server = new DatagramSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		recieveLoop = new Thread() {
			ByteBuffer buff = ByteBuffer.allocate(PACKET_LENGTH);
			
			@Override
			public void run() {
				setName("VMX Thread");
				while(!Thread.interrupted()) {
					try {
						server.receive(packet);
						buff.rewind();
						buff.put(packet.getData());
						synchronized(syncLock) {
							zAngle = buff.getDouble(0);
							zRate = buff.getDouble(8);
							pitch = buff.getDouble(16);
							roll = buff.getDouble(24);
							xVelocity = buff.getDouble(32);
							yVelocity = buff.getDouble(40);
							millis = System.currentTimeMillis();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		recieveLoop.start();
	}
	
	public double getAngle() {
		synchronized(syncLock) {
			return zAngle + zOffset;
		}
	}
	
	public double getRate() {
		synchronized(syncLock) {
			return zRate;
		}
	}
	public double getPitch() {
		synchronized(syncLock) {
			return pitch;
		}
	}
	public double getRoll() {
		synchronized(syncLock) {
			return roll;
		}
	}
	public double getXVelocity() {
		synchronized(syncLock) {
			return xVelocity;
		}
	}
	public double getYVelocity() {
		synchronized(syncLock) {
			return yVelocity;
		}
	}
	
	public long getTimeSinceLastPacket() {
		synchronized(syncLock) {
			return System.currentTimeMillis() - millis;
		}
	}
	
	public void zero() {
		zOffset = -getAngle();
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		m_source = pidSource;
	}


	@Override
	public PIDSourceType getPIDSourceType() {
		return m_source;
	}


	@Override
	public double pidGet() {
		switch(m_source) {
			case kRate:
				return getRate();
			case kDisplacement:
			default:
				return getAngle();
		}
	}
}

