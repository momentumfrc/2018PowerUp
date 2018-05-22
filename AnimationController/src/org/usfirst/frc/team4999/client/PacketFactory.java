package org.usfirst.frc.team4999.client;

import java.nio.ByteBuffer;

import org.usfirst.frc.team4999.lights.Command;

public class PacketFactory {
	
	private static final byte PACKET_COMMAND = 0x01;
	private static final byte FRAME_COMMAND = 0x02;
	private static final byte REFRESH_COMMAND = 0x03;
	
	private static ByteBuffer buff = ByteBuffer.allocate(17);
	
	public static Packet sendCommand(Command newCommand ) {
		buff.clear();
		buff.put(PACKET_COMMAND);
		buff.put(newCommand.getData());
		return new Packet(buff.array());
	}
	
	public static Packet showFrame() {
		buff.clear();
		buff.put(FRAME_COMMAND);
		while(buff.hasRemaining()) buff.put((byte)0xFF);
		return new Packet(buff.array());
	}
	
	public static Packet setRefreshTime(int refreshTime) {
		buff.clear();
		buff.put(REFRESH_COMMAND);
		buff.putInt(refreshTime);
		while(buff.hasRemaining()) buff.put((byte)0xFF);
		return new Packet(buff.array());
	}
	
}
