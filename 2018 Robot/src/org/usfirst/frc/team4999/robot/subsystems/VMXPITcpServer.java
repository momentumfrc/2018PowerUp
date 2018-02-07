package org.usfirst.frc.team4999.robot.subsystems;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class VMXPITcpServer extends Subsystem {

    private static int PORT = Integer.parseInt("5800");
    private Thread connectClient;
	private DatagramSocket server;
    byte[] buffer = new byte[17];
	  DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

    
	public VMXPITcpServer() {
		try {
			server = new DatagramSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	private void recievePacket() {
		  try {
			server.receive(packet);
			String msg = new String(buffer, 0, packet.getLength());
	        System.out.println(packet.getAddress().getHostName() + ": " + msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

