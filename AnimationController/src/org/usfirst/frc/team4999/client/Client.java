package org.usfirst.frc.team4999.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.usfirst.frc.team4999.lights.Command;

public class Client {
	
	private class IOThread extends Thread {
		private final String hostname;
		private final int port;
		public IOThread(String hostname, int port) {
			this.hostname = hostname;
			this.port = port;
		}
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
			} catch (java.net.ConnectException e) {
				System.out.println("Could not connect");
			} catch (java.net.SocketException e) {
				System.out.println("Lost connection");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private final String hostname;
	private final int port;
	
	private ArrayList<Packet> packetBuffer;
	
	private Thread io;
	
	private static final int MAX_BUFFER_SIZE = 60;
	
	public Client(String hostname, int port) {
		
		this.hostname = hostname;
		this.port = port;
		
		packetBuffer = new ArrayList<Packet>();
		io = new IOThread(hostname, port);
		io.start();
	}
	
	public boolean reconnect() {
		if(!io.isAlive()) {
			io = new IOThread(hostname, port);
			io.start();
			return true;
		}
		return false;
	}
	
	public void sendPacket(Packet p) {
		if(!getConnected()) {
			return;
		}
		packetBuffer.add(p);
		if(packetBuffer.size() > MAX_BUFFER_SIZE) {
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
	
	public int getQueue() {
		return packetBuffer.size();
	}
	
	public boolean getConnected() {
		return io.isAlive();
	}

}
