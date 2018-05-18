import org.usfirst.frc.team4999.client.*;
import org.usfirst.frc.team4999.lights.*;
import processing.sound.*;

Client c;

AudioIn mic;
FFT fft;

float amp = 20;
int bands = 512;

Animation current;

void setup() {
  c = new Client("localhost", 5800);
  
  size(1024, 360);
  background(255);
  fft = new FFT(this, bands);
  
  mic = new AudioIn(this, 0);
  mic.amp(amp);
  mic.start();
  fft.input(mic);
  
  current = new SimpleSpectrum(this, fft, c);
}



void draw() {
  background(255);
  noStroke();
  
  current.draw();
  
}
