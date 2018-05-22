package org.usfirst.frc.team4999.client;

public class Packet {
	
	private static final int MAX_LENGTH = 25;
	byte[] packet;
	public Packet(byte[] b) {
		if(b.length > MAX_LENGTH) throw new IllegalArgumentException("byte[] size exceeds maximum packet size");
		packet = b.clone();
	}
	
	public byte[] getData() {
		return packet;
	}
	
	public int getLength() {
		return packet.length;
	}
	
	@Override
	public String toString() {
		String out = "NetworkPacket[ ";
		for(byte b : packet) {
			out += String.format("0x%02X ", b);
		}
		out += "]";
		return out;
	}

}
