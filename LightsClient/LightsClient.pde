import org.usfirst.frc.team4999.client.*;
import org.usfirst.frc.team4999.lights.*;
import processing.sound.*;

Client c;

AudioIn mic;
FFT fft;
processing.sound.Amplitude ampAnalyzer;

float amp = 10;
int bands = 512;

Animation current;

void setup() {
  c = new Client("localhost", 5800);
  
  size(1024, 360);
  background(255);
  fft = new FFT(this, bands);
  ampAnalyzer = new processing.sound.Amplitude(this);
  
  mic = new AudioIn(this, 0);
  mic.amp(amp);
  mic.start();
  fft.input(mic);
  ampAnalyzer.input(mic);
  
  //current = new SimpleSpectrum(this, c, fft);
  current = new Amplitude(this, c, ampAnalyzer);
}



void draw() {
  background(255);
  
  current.draw();
  
}
