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
	private DatagramSocket server;
	DatagramPacket packet = new DatagramPacket(new byte[17], 17);
	
	private Thread recieveLoop;
	private double zAngle, zRate;
	
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
			ByteBuffer buff = ByteBuffer.allocate(17);
			
			@Override
			public void run() {
				setName("VMX Thread");
				while(!Thread.interrupted()) {
					try {
						server.receive(packet);
						byte[] data = packet.getData();
						int checksum = 0;
						for(int i = 1; i < data.length; i++) {
							checksum += (data[i]&0xFF);
						}
						if(checksum % 255 == (data[0]&0xFF)) { // 0xFF removes the sign
							buff.rewind();
							buff.put(data);
							synchronized(syncLock) {
								zAngle = buff.getDouble(1);
								zRate = buff.getDouble(9);
								millis = System.currentTimeMillis();
							}
						} else System.out.format("%d != %d\n", checksum % 255, (data[0]&0xFF));
						
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
			return zAngle;
		}
	}
	
	public double getRate() {
		synchronized(syncLock) {
			return zRate;
		}
	}
	
	public long getTimeSinceLastPacket() {
		synchronized(syncLock) {
			return System.currentTimeMillis() - millis;
		}
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

