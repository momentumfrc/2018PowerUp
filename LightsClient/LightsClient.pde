import org.usfirst.frc.team4999.client.*;
import org.usfirst.frc.team4999.lights.*;
import processing.sound.*;

Client c;

AudioIn mic;

float amp = 10;


Animation current;

void setup() {
  c = new Client("localhost", 5800);
  
  size(1024, 360);
  background(255);
  
  mic = new AudioIn(this, 0);
  mic.amp(amp);
  mic.start();
  
  //current = new SimpleSpectrum(this, c, mic);
  current = new Amplitude(this, c, mic);
}



void draw() {
  background(255);
  
  current.draw();
  
}
