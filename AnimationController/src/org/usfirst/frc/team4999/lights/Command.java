package org.usfirst.frc.team4999.lights;

public class Command {
	
	private static final int MAX_PACKET_SIZE = 16;
	
	private byte[] data;
	
	public Command(byte[] data) {
		if(data.length > MAX_PACKET_SIZE) throw new IllegalArgumentException("Packets are no longer than " + MAX_PACKET_SIZE + "bytes");
		this.data = data;
	}
	
	public int getSize() {
		return data.length;
	}
	
	public byte[] getData() {
		return data;
	}
}
