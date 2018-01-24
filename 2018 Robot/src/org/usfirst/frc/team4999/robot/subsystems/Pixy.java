package org.usfirst.frc.team4999.robot.subsystems;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.usfirst.frc.team4999.misc.DetectedVisionObject;
import org.usfirst.frc.team4999.robot.commands.pixy.RecieveData;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Pixy camera for vision processing
 */
public class Pixy extends Subsystem {
	
	// Pixy's slave address
	final int index = 0x54;
	
	final short sync_word = (short)0xaa55;
	
	private ArrayList<DetectedVisionObject> detectedObjects;
	private ByteBuffer recieveBuffer = ByteBuffer.allocateDirect(12);
	private ByteBuffer sendBuffer = ByteBuffer.allocate(6);
	private I2C i2c = new I2C(I2C.Port.kOnboard, index);
	
	private short panPos = 0;
	private short tiltPos = 0;
	
	
	private boolean findStart() {
		short word = (short)0xffff, lastword = (short)0xffff;
		while(true) {
			i2c.readOnly(recieveBuffer, 2);
			word = recieveBuffer.getShort(0);
			
			if(word == 0 && lastword == 0) return false;
			
			if(word == sync_word && lastword == sync_word) return true;
			
			lastword = word;
			
		}
	}
	
	private void recieveObjects() {
		while(true) {
			i2c.readOnly(recieveBuffer, 12);
			
			short checksum = recieveBuffer.getShort(0);
			if(checksum == 0) break; // checksum is 0 when there are no more objects (not a very well documented feature)
			
			int sum = 0;
			for(int i = 4; i <= 12; i += 2) {
				sum += recieveBuffer.getShort(i);
			}
			
			if(sum == checksum) {
				detectedObjects.add(new DetectedVisionObject(
						recieveBuffer.getShort(6),
						recieveBuffer.getShort(8),
						recieveBuffer.getShort(10),
						recieveBuffer.getShort(12)
						));
			}	
		}
			
	}
	
	public void recieveFrame() {
		if(findStart()) {
			detectedObjects.clear();
			recieveObjects();
		}
	}
	
	public ArrayList<DetectedVisionObject> getObjects() {
		return detectedObjects;
	}
	
	public void move(short tilt, short pan) {
		this.tiltPos = tilt;
		this.panPos = pan;
		sendBuffer.rewind();
		sendBuffer.putShort((short) 0xff00);
		sendBuffer.putShort(pan);
		sendBuffer.putShort(tilt);
		i2c.writeBulk(sendBuffer, 6);
	}
	
	public void tilt(short tilt) {
		move(tilt, panPos);
	}
	
	public void pan(short pan) {
		move(tiltPos, pan);
	}
	
	public void setExposure(byte brightess) {
		sendBuffer.rewind();
		sendBuffer.putShort((short)0xfe00);
		sendBuffer.put(brightess);
		i2c.writeBulk(sendBuffer, 3);
	}
	
	public void setLED(byte r, byte g, byte b) {
		sendBuffer.rewind();
		sendBuffer.putShort((short)0xfd00);
		sendBuffer.put(r);
		sendBuffer.put(g);
		sendBuffer.put(b);
		i2c.writeBulk(sendBuffer, 5);
	}
	
	
    public void initDefaultCommand() {
        setDefaultCommand(new RecieveData());
    }
}

