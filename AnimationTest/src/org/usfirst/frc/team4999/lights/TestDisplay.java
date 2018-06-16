package org.usfirst.frc.team4999.lights;

import org.usfirst.frc.team4999.lights.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.nio.ByteBuffer;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.usfirst.frc.team4999.lights.animations.*;

/**
 * Makes a JFrame displays lights on it, to test animations
 * @author jordan
 *
 */

class TestComponent extends JComponent implements Display {

	private final int PIXEL_SIZE = 20;
	
	java.awt.Color[] pixels;
	
	Dimension pixsize;
	
	private long lastTime;
	
	public TestComponent(int numPixels) {
		super();
		pixels = new java.awt.Color[numPixels];
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = new java.awt.Color(255,255,255);
		}
		resize();
		lastTime = System.currentTimeMillis();
	}
	
	@Override
	public void paintComponent(Graphics gd) {
		Graphics2D g = (Graphics2D) gd;
		
		/*for(int y = 0; y < pixsize.height; y++) {
			for(int x = 0; x < pixsize.width; x++) {
				Rectangle rect = new Rectangle(x * PIXEL_SIZE, y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
				int i = (y * pixsize.width) + x;
				if(i >= pixels.length) {
					return;
				}
				//System.out.println("Color at " + i + " is " + pixels[i].toString());
				g.setPaint(pixels[i]);
				g.fill(rect);
			}
		}*/
		for(int i = 0; i < pixels.length; i++) {
			Rectangle rect = new Rectangle(i * PIXEL_SIZE, 0, PIXEL_SIZE, PIXEL_SIZE);
			g.setPaint(pixels[i]);
			g.fill(rect);
		}
	}
	
	public void resize() {
		/*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int maxcols = (screenSize.width - FRAME_BORDER) / PIXEL_SIZE;
		
		int x = (pixels.length % maxcols) * PIXEL_SIZE, y = PIXEL_SIZE;
		for(int i = 0; i < ((pixels.length * PIXEL_SIZE) / maxcols) / PIXEL_SIZE; i++) {
			y += PIXEL_SIZE;
			x = (screenSize.width - FRAME_BORDER);
		}
		
		pixsize = new Dimension(x / PIXEL_SIZE, y/PIXEL_SIZE);
		
		setPreferredSize(new Dimension(x,y));*/
		setPreferredSize(new Dimension(pixels.length*PIXEL_SIZE, PIXEL_SIZE));
		
	}
	
	//private ByteBuffer b = ByteBuffer.allocate(16);
	@Override
	public void show(Packet[] pixels) {
		
		double packetPerSec = pixels.length / ((System.currentTimeMillis() - lastTime) / 1000.0);
		lastTime = System.currentTimeMillis();
		if(packetPerSec > 781.25) {
			System.out.format("Exceeds data limit! %.2f packets/sec\n",packetPerSec);
		}
		
		for(Packet packet : pixels) {
			//b.rewind();
			//b.put(packet.getData());
			byte[] b = packet.getData();
			java.awt.Color c;
			switch(b[1]) {
			case 0x02:
				this.pixels[(int)(b[2]&0xff)] = new java.awt.Color((int)(b[3]&0xff), (int)(b[4]&0xff), (int)(b[5]&0xff));
				break;
			case 0x03:
				c = new java.awt.Color((int)(b[3]&0xff), (int)(b[4]&0xff), (int)(b[5]&0xff));
				for(int i = (int)(b[2]&0xff); i < Math.min((int)(b[2]&0xff)+(int)(b[6]&0xff), this.pixels.length); i++) {
					this.pixels[i] = c;
				}
				break;
			case 0x04:
				c = new java.awt.Color((int)(b[3]&0xff), (int)(b[4]&0xff), (int)(b[5]&0xff));
				int start = (int)(b[2]&0xff), length = (int)(b[6]&0xff), repeat = (int)(b[7]&0xff);
				for(int r = 0; r < this.pixels.length; r += repeat) {
					for(int i = r+start; i < r+start+length && i < this.pixels.length; i++) {
						this.pixels[i] = c;
					}
				}
				
				break;
			default:
				break;
			}
		}
		
		resize();
		
		revalidate();
		repaint();
	}
	
}

public class TestDisplay {
	
	public static void main(String[] args) {
		
		final int numPixels = 80;
		
		
		TestComponent tc = new TestComponent(numPixels);
		JFrame frame = new JFrame();
		frame.add(tc);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setVisible(true);
		
		Animator an = new Animator(tc);
		
		Packet[] test = new Packet[] {Commands.makeStride(0, new org.usfirst.frc.team4999.lights.Color(0,0,0), 1, 1)};
		tc.show(test);
		
		Color[] rainbowcolors = {
			new Color(139,0,255),
			Color.BLUE,
			Color.GREEN,
			Color.YELLOW,
			new Color(255,127,0),
			Color.RED
		};
		
		AnimationSequence momentum = new AnimationSequence(new Animation[] {
				Snake.twoColorSnake(Color.MOMENTUM_PURPLE, Color.MOMENTUM_BLUE, 1, 5, 2, 125),
				new Fade(new Color[]{Color.MOMENTUM_BLUE, Color.WHITE, Color.MOMENTUM_PURPLE}, 200, 0),
				new Bounce(Color.MOMENTUM_PURPLE, Color.MOMENTUM_BLUE, 8, 20, 50),
				new Bounce(Color.WHITE, new Color[] {Color.MOMENTUM_PURPLE, Color.MOMENTUM_PURPLE, Color.MOMENTUM_BLUE, Color.MOMENTUM_BLUE}, 20, 50)
		}, new int[] {5000, 5000, 10000, 5000});
		
		AnimationSequence rainbow = new AnimationSequence(new Animation[] {
				Snake.rainbowSnake(70),
				Fade.rainbowFade(100, 20),
				Snake.rainbowSnake(150),
				Fade.rainbowFade(200, 0),
				new Bounce(Color.WHITE, rainbowcolors, 20, 50),
				new Stack(rainbowcolors, 25, 40)
		}, new int[] {5000, 5000, 1000, 6000, 10000, 20000});
		
		//an.setAnimation(new SocketListener());
		an.setAnimation(rainbow);
		
		
	}

}