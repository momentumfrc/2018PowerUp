import org.usfirst.frc.team4999.client.*;
import org.usfirst.frc.team4999.lights.*;
import processing.sound.*;

Client c;

AudioIn mic;
FFT fft;
int bands = 512;
int startBand = 2;
int endBand = 42;

float[][] spectrum = new float[endBand - startBand][4];
float[] avg = new float[endBand - startBand];

double amp = 50;

int oldest = 0;

PFont font;

void setup() {
  c = new Client("localhost", 5800);
  
  size(1024, 360);
  background(255);
  fft = new FFT(this, bands);
  
  mic = new AudioIn(this, 0);
  mic.start();
  fft.input(mic);
  c.sendPacket(PacketFactory.setRefreshTime(30));
  font = createFont("Arial", 16, true);
}

Color fadeBetween(double percent, Color fg, Color bg) {
  int r = (int)((fg.getRed() - bg.getRed()) * percent) + bg.getRed();
  int g = (int)((fg.getGreen() - bg.getGreen()) * percent) + bg.getGreen();
  int b = (int)((fg.getBlue() - bg.getBlue()) * percent) + bg.getBlue();
  return new Color(r,g,b);
}
Color[] rainbow = {
   new Color(255,0,0),
   new Color(255, 127, 0),
   new Color(255,255,0),
   new Color(0,255,0),
   new Color(0,0,255),
   new Color(75,0,130),
   new Color(148,0,211),
   new Color(255,255,255)
};
Color mapRainbow(float percent) {
  percent = constrain(percent,0,1);
  if(Math.abs(percent) < 0.00001) return rainbow[6];
  double idx = map(1-percent, 0, 1, 0, (rainbow.length-1));
  int i = (int)idx;
  if(i >=7) System.out.println(percent);
  return fadeBetween(idx-i,rainbow[i+1],rainbow[i]);
}

void draw() {
  background(255);
  noStroke();
  fft.analyze();
  
  for(int i = 0; i < spectrum.length; i++) {
    spectrum[i][oldest] = fft.spectrum[i+startBand];
  }
  oldest = (oldest + 1) % spectrum[0].length;
  
  for(int i = 0; i < spectrum.length; i++) {
    avg[i] = 0;
    for(int j = 0; j < spectrum[0].length; j++) {
      avg[i] += spectrum[i][j];
    }
    avg[i] /= spectrum[i].length;
  }
  
  int w = width / avg.length;
  int scale = 300;
  Command[] frame = new Command[avg.length];
  for(int i = 0; i < avg.length; i++) {
    float val = (float)(avg[i] * amp);
    Color c = mapRainbow(val);
    frame[i] = CommandFactory.makeStride(i, c, 1, frame.length);
    //frame[(i*2)+1] = CommandFactory.makeStride(frame.length - i - 1, c, 1, frame.length);
    fill(c.getRed(), c.getGreen(), c.getBlue());
    rect(i*w, height-((float)val*scale), w, (float)val*scale);
  }
  
  c.sendFrame(frame);
  
  textFont(font);
  fill(0);
  text(String.format("Packet queue: %d", c.getQueue()), 10, 20);
  
}
