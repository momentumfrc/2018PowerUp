public class Chooser {
  
  private class SoundF {
    final String filename;
    final String displayname;
    final int amp;
    public SoundF(String filename, String displayname, int amp) {
      this.filename = filename;
      this.displayname = displayname;
      this.amp = amp;
    }
  }
  
  SoundF currentFile = null;
  SoundFile currentSoundFile = null;
  
  Animation animation = null;
  
  boolean choosen = false;
  
  private ArrayList<SoundF> soundfileOptions = new ArrayList<SoundF>();
  
  private PFont font;
  
  private final int textheight = 25;
  private final int textborder = 4;
  
  private PApplet window;
  private Client c;
  
  public Chooser(PApplet window, Client c) {
    soundfileOptions.add(new SoundF("MEGALOVANIA.ogg", "MEGALOVANIA", 30));
    soundfileOptions.add(new SoundF("Goofy Goober.ogg", "Goofy Goober", 30));
    soundfileOptions.add(new SoundF("Mii Channel.ogg", "Wii Music", 20));
    soundfileOptions.add(new SoundF("The Arena.ogg", "The Arena", 15));
    soundfileOptions.add(new SoundF("Eiffel 65 - Blue.ogg", "Blue", 30));
    soundfileOptions.add(new SoundF("Silver Scrapes.ogg", "Silver Scrapes", 12));
    soundfileOptions.add(new SoundF("Hornet.ogg", "Hornet", 30));
    soundfileOptions.add(new SoundF("Mantis Lords.ogg", "Mantis Lords", 15));
    soundfileOptions.add(new SoundF("Radiance.ogg", "Radiance", 15));
    
    font = createFont("Arial", 14);
    
    this.window = window;
    this.c = c;
  }
  
  public void setup() {
    if(animation != null) {
      animation.setup();
    }
  }
  
  public void createAnimation() {
    //animation = new SimpleSpectrum(window, c, currentSoundFile, currentFile.amp);
    animation = new Amplitude(window, c, currentSoundFile, currentFile.amp);
    //animation = new BounceDance(window, c, currentSoundFile, currentFile.amp, Color.BLACK, Color.WHITE, 5, 30, 5);
    animation.setup();
  }
  
  public void draw() {
    if(choosen) {
      animation.draw();
    } else {
      textFont(font);
      noStroke();
      for(int i = 0; i < soundfileOptions.size(); i++) {
        if(mouseY > i* textheight && mouseY < (i+1) * textheight) {
          fill(230);
          if(mousePressed) {
            choosen = true;
            currentFile = soundfileOptions.get(i);
            currentSoundFile = new SoundFile(window, currentFile.filename);
            fill(200);
            currentSoundFile.loop();
            createAnimation();
          }
        } else {
          fill(255);
        }
        rect(0,i*textheight, width, textheight);
        fill(0);
        text(soundfileOptions.get(i).displayname, textborder, i*textheight+textborder, width-(2*textborder), textheight-(2*textborder));
        
      }
    }
    
  }
  
  public void reset() {
    
    if(choosen) {
      if(currentSoundFile != null) {
        currentSoundFile.stop();
      }
      choosen = false;
    }
    }
  
}
