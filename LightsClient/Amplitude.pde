public class Amplitude implements Animation {
  private PApplet window;
  private Client c;
  private processing.sound.Amplitude amp;
  
  float[] hist;
  int idx = 0;
  
  int w = 2;
  
  public Amplitude(PApplet window, Client c, processing.sound.Amplitude amp) {
    this.window = window;
    this.c = c;
    this.amp = amp;
    c.sendPacket(PacketFactory.setRefreshTime(50));
    hist  = new float[(int)(width/w)];
  }
  
  @Override
  public void draw() {
    float data = amp.analyze();
    Color c = ColorTools.mapRainbow(data);
    this.c.sendFrame(new Command[] { CommandFactory.makeStride(0, c, 1, 1) });
    hist[idx] = data;
     
     
    
    for(int i = hist.length-1, j = idx; i >= 0; i--, j = (j-1+hist.length) % hist.length) {
      window.noStroke();
      c = ColorTools.mapRainbow(hist[j]);
      window.fill(c.getRed(), c.getGreen(), c.getBlue());
      window.rect(i * w, height - (int)(hist[j] * height), w, (int)(hist[j] * height));
    }
    idx = (idx + 1) % hist.length;
  }
}
