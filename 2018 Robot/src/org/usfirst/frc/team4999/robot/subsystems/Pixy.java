package org.usfirst.frc.team4999.robot.subsystems;

import java.nio.ByteBuffer;

import org.usfirst.frc.team4999.robot.commands.pixy.RecieveData;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pixy extends Subsystem {
	
	static class Parsed_Packet{
		int xCenter;
		int yCenter;
		int width;
		int height;
		
		Parsed_Packet(int xCenter, int yCenter, int width, int height){
			this.xCenter = xCenter;
			this.yCenter = yCenter;
			this.width = width;
			this.height = height;
		}
		
	}
	
	Parsed_Packet parsed_packet;
	
	ByteBuffer byte_array = ByteBuffer.allocateDirect(14);
	final int index = 0x54;
	I2C i2c = new I2C(I2C.Port.kOnboard, index);
	
	public void read_Byte() {
		byte_array.rewind();
		i2c.readOnly(byte_array, 14);
		parse_Byte();
	}
	
	
	private void sync() {
		byte[] sync_byte = new byte[2];
		do {
			i2c.readOnly(sync_byte, 1);
		}while(!(sync_byte[0] == 0x55 && sync_byte[1] == 0xaa));
		byte_array.rewind();
		byte_array.put(sync_byte);
		byte_array.position(2);
		i2c.readOnly(byte_array, 12);
	}
	
	
	private void parse_Byte() {
		if(byte_array.getShort(0) != 0xaa55) {
			System.out.println("Invalid Packet Recieved");
			sync();
		}
		parsed_packet = new Parsed_Packet(
				byte_array.getShort(6),
				byte_array.getShort(8),
				byte_array.getShort(10),
				byte_array.getShort(12)
				);
	}
	
	public int getCenterX() {
		if(parsed_packet == null)
			return -1;
		return parsed_packet.xCenter;
	}
	public int getCenterY() {
		if(parsed_packet == null)
			return -1;
		return parsed_packet.yCenter;
	}
	public int width() {
		if(parsed_packet == null)
			return -1;
		return parsed_packet.width;
	}
	public int height() {
		if(parsed_packet == null)
			return -1;
		return parsed_packet.height;
	}
	
	
    public void initDefaultCommand() {
        setDefaultCommand(new RecieveData());
    }
}

