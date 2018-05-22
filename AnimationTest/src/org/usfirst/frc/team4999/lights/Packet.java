package org.usfirst.frc.team4999.lights;

public class Packet {
	
	private static final int MAX_PACKET_SIZE = 16;
	
	private byte[] data;
	
	public Packet(byte[] data) {
		if(data.length > MAX_PACKET_SIZE) throw new IllegalArgumentException("Packets are no longer than " + MAX_PACKET_SIZE + "bytes");
		this.data = data.clone();
	}
	
	public int getSize() {
		return data.length;
	}
	
	public byte[] getData() {
		return data;
	}
	
	@Override
	public String toString() {
		String out = "Packet [";
		for(byte b : data) {
			out += String.format("0x%02X ", b);
		}
		out += "]";
		return out;
	}
}
