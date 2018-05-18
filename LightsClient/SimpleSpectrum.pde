public class SimpleSpectrum implements Animation {
  
  FFT fft;
  PApplet window;
  
  PFont font;
  
  int startBand = 2;
  int endBand = 33;
  
  int refreshRate = 40;
  
  int oldest = 0;
  
  float[][] spectrum = new float[endBand - startBand][4];
  float[] avg = new float[endBand - startBand];
  
  Client c;
  
  public SimpleSpectrum(PApplet window, FFT fft, Client c) {
    this.fft = fft;
    this.window = window;
    font = createFont("Arial", 16, true);
    this.c = c;
    
    c.sendPacket(PacketFactory.setRefreshTime(refreshRate));
  }
  
  private Color fadeBetween(double percent, Color fg, Color bg) {
    int r = (int)((fg.getRed() - bg.getRed()) * percent) + bg.getRed();
    int g = (int)((fg.getGreen() - bg.getGreen()) * percent) + bg.getGreen();
    int b = (int)((fg.getBlue() - bg.getBlue()) * percent) + bg.getBlue();
    return new Color(r,g,b);
  }
  private Color[] rainbow = {
     new Color(255,0,0),
     new Color(255, 127, 0),
     new Color(255,255,0),
     new Color(0,255,0),
     new Color(0,0,255),
     new Color(75,0,130),
     new Color(148,0,211),
     new Color(255,255,255)
  };
  private Color mapRainbow(float percent) {
    percent = constrain(percent,0,1);
    if(Math.abs(percent) < 0.00001) return rainbow[rainbow.length-1];
    double idx = map(1-percent, 0, 1, 0, (rainbow.length-1));
    int i = (int)idx;
    if(i >=7) System.out.println(percent);
    return fadeBetween(idx-i,rainbow[i+1],rainbow[i]);
  }

  @Override
  public void draw() {
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
      window.fill(c.getRed(), c.getGreen(), c.getBlue());
      window.rect(i*w, height-((float)val*scale), w, (float)val*scale);
    }
    
    c.sendFrame(frame);
    
    window.textFont(font);
    window.fill(0);
    window.text(String.format("Packet queue: %d", c.getQueue()), 10, 20);
    window.text(String.format("Packets per second: %.2f, Kbps:%.2f", (1000.0/refreshRate) * frame.length, (1000.0/refreshRate) * frame.length * 0.128), 10, 60);
    window.text(String.format("FPS: %.2f", frameRate), 10, 40);
    
    
  }
}
