import org.usfirst.frc.team4999.client.*;
import org.usfirst.frc.team4999.lights.*;
import processing.sound.*;

Client c;

AudioIn mic;
SoundFile soundF;

float amp = 20;


Animation current;

PFont font;

void setup() {
  c = new Client("localhost", 5800);
  
  size(1024, 360);
  background(255);
  
  mic = new AudioIn(this, 0);
  soundF = new SoundFile(this, "MEGALOVANIA.ogg");
  soundF.loop();
  
  mic.amp(amp);
  mic.start();
  
  font = createFont("Arial", 16, true);
  
  current = new SimpleSpectrum(this, c, soundF, 30);
  //current = new Amplitude(this, c, soundF, 5);
  
  current.setup();
}



void draw() {
  background(255);
  
  current.draw();
  textFont(font);
  if(c.getConnected()) {
      fill(0,255,0);
      text("Connected", width - 90, 20);
    } else {
      fill(255,0,0);
      text("No Connection", width - 120, 20);
    }
  
}

void keyPressed() {
  if(key == 'r') {
    if(c.reconnect()) {
      current.setup();
    }
  }
}
