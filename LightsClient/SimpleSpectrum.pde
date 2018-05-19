public class SimpleSpectrum implements Animation {
  
  private FFT fft;
  private PApplet window;
  
  private PFont font;
  
  private int bands = 512;
  private int startBand = 2;
  private int endBand = 33;
  
  private float amp;
  
  private int scale = 300;
  
  private int refreshRate = 60;
  
  private int oldest = 0;
  
  private float[][] spectrum = new float[endBand - startBand][4];
  private float[] avg = new float[endBand - startBand];
 
 
  private Client c;
  
  public SimpleSpectrum(PApplet window, Client c, AudioIn mic, float amp) {
    this.fft =  new FFT(window, bands);
    fft.input(mic);
    this.window = window;
    font = createFont("Arial", 16, true);
    this.c = c;
    
    this.amp = amp;
    
  }
  
  public SimpleSpectrum(PApplet window, Client c, SoundFile mic, float amp) {
    this.fft =  new FFT(window, bands);
    fft.input(mic);
    this.window = window;
    font = createFont("Arial", 16, true);
    this.c = c;
    
    this.amp = amp;
    
  }
  
  @Override
  public void setup() {
    c.sendPacket(PacketFactory.setRefreshTime(refreshRate));
  }
  

  @Override
  public void draw() {
    window.noStroke();
    
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
    Command[] frame = new Command[avg.length];
    for(int i = 0; i < avg.length; i++) {
      float val = (float)(avg[i] * amp);
      Color c = ColorTools.mapRainbow(val);
      frame[i] = CommandFactory.makeStride(i*2, c, 2, frame.length);
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
