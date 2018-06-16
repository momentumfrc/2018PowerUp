public class BounceDance implements Animation {
  
  private PApplet window;
  private Client c;
  private processing.sound.Amplitude amp;
  private float amplification;
  
  private Color background;
  private Color[] run;
  private float[] pos, v;
  private int area;
  
  private float[] hist;
  private int idx = 0;
  
  private int avgsamples = 3;
  
  private int w = 2;
  
  private float maxv;
  
  public BounceDance(PApplet window, Client c, AudioIn mic, float amp, Color background, Color foreground, int fadelength, int bounceArea, float maxv) {
    buildrun(background, foreground, fadelength, bounceArea);
    this.window = window;
    this.c = c;
    this.amp = new processing.sound.Amplitude(window);
    this.amp.input(mic);
    this.amplification = amp/20;
    this.maxv = maxv;
    
    hist  = new float[(int)(width/w)];
  }
  
  public BounceDance(PApplet window, Client c, SoundFile mic, float amp,  Color background, Color foreground, int fadelength, int bounceArea, float maxv) {
    buildrun(background, foreground, fadelength, bounceArea);
    this.window = window;
    this.c = c;
    this.amp = new processing.sound.Amplitude(window);
    this.amp.input(mic);
    this.amplification = amp/20;
    this.maxv = maxv;
    
    hist  = new float[(int)(width/w)];
  }
  
  private void buildrun(Color background, Color foreground, int fadelength, int bounceArea) {
    this.background = background;
    run = new Color[fadelength+1];
    pos = new float[run.length];
    v = new float[run.length];
    for(int i = 0; i < run.length; i++) {
      run[run.length-1-i] = ColorTools.fadeBetween((i+1)/(double)(run.length), background, foreground);
    }
    for(int i = run.length - 1; i >= 0; i--) {
      pos[i] = i;
    }
    
    for(int i = 0; i < v.length; i++) {
      v[i] = 1;
    }
    area = bounceArea;
    
  }
  
  public void setup() {
    c.sendPacket(PacketFactory.setRefreshTime(50));
  }
  
  public void draw() {
    
    float data = constrain(amp.analyze() * amplification, 0, maxv);
    
    hist[idx] = data;
    
    float avg = 0;
    for(int i = 0, j = idx; i < avgsamples; i++, j = (j-1+hist.length) % hist.length) {
      avg += hist[j];
    }
    avg /= avgsamples;
    
    for(int i = hist.length-1, j = idx; i >= 0; i--, j = (j-1+hist.length) % hist.length) {
      window.noStroke();
      Color co = ColorTools.mapRainbow(hist[j]);
      window.fill(co.getRed(), co.getGreen(), co.getBlue());
      window.rect(i * w, height - (int)(hist[j] * height), w, (int)(hist[j] * height));
    }
    idx = (idx + 1) % hist.length;
    
    Command[] frame = new Command[1+pos.length];
    frame[0] = CommandFactory.makeStride(0, background, 1, 1);
    for(int i = 0; i < pos.length; i++) {
      frame[i+1] = CommandFactory.makeStride((int)(pos[i]), run[i], 1, area);
    }
    for(int i = 0; i < v.length; i++) {
      if(pos[i] >= area)
        v[i] = -Math.abs(v[i]);
      else if(pos[i] <= 0)
        v[i] = Math.abs(v[i]);
      pos[i] = pos[i] + v[i] * avg;
    }
    
    c.sendFrame(frame);
  }
}
