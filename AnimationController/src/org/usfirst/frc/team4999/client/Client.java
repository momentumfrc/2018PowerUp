package org.usfirst.frc.team4999.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.usfirst.frc.team4999.lights.Command;

public class Client {
	
	private ArrayList<Packet> packetBuffer;
	
	private Thread io;
	
	private static final int MAX_BUFFER_SIZE = 60;
	
	public Client(String hostname, int port) {
		
		packetBuffer = new ArrayList<Packet>();
		io = new Thread() {
			public void run() {
				try (
					Socket socket = new Socket(hostname, port);
					OutputStream out = socket.getOutputStream();
					InputStream in = socket.getInputStream();
				) {
					while(!Thread.interrupted()) {
						if(!packetBuffer.isEmpty()) {
							out.write(packetBuffer.remove(0).getData());
						} else {
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
								break;
							}
						}
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		io.start();
	}
	
	public void sendPacket(Packet p) {
		packetBuffer.add(p);
		if(packetBuffer.size() > MAX_BUFFER_SIZE) {
			System.out.println("Too many packets, removing oldest");
			packetBuffer.remove(0);
		}
	}
	
	public void sendCommand(Command newCommand) {
		sendPacket(PacketFactory.sendCommand(newCommand));
	}
	
	public void sendFrame(Command[] frame) {
		for(Command c : frame) {
			sendCommand(c);
		}
		sendPacket(PacketFactory.showFrame());
	}
	
	public void stop() {
		io.interrupt();
	}

}
