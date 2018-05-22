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
  private static int PACKET_LENGTH = 48;

	private DatagramSocket server;
	DatagramPacket packet = new DatagramPacket(new byte[PACKET_LENGTH], PACKET_LENGTH);

	private Thread recieveLoop;

	private volatile double zAngle, zRate, pitch, roll, xVelocity, yVelocity;
	private double zOffset;

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
						zAngle = buff.getDouble(0);
						zRate = buff.getDouble(8);
						pitch = buff.getDouble(16);
						roll = buff.getDouble(24);
						xVelocity = buff.getDouble(32);
						yVelocity = buff.getDouble(40);
						millis = System.currentTimeMillis();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		recieveLoop.start();
	}

	public double getAngle() {
		return zAngle + zOffset;
	}

	public double getRate() {
		return zRate;
	}
	public double getPitch() {
		return pitch;
	}
	public double getRoll() {
		return roll;
	}
	public double getXVelocity() {
		return xVelocity;
	}
	public double getYVelocity() {
		return yVelocity;
	}

	public long getTimeSinceLastPacket() {
		return System.currentTimeMillis() - millis;
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
