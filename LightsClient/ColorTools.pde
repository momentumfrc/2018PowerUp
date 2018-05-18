public static class ColorTools {
  public static Color fadeBetween(double percent, Color fg, Color bg) {
    int r = (int)((fg.getRed() - bg.getRed()) * percent) + bg.getRed();
    int g = (int)((fg.getGreen() - bg.getGreen()) * percent) + bg.getGreen();
    int b = (int)((fg.getBlue() - bg.getBlue()) * percent) + bg.getBlue();
    return new Color(r,g,b);
  }
  public static Color[] rainbow = {
     new Color(255,0,0),
     new Color(255, 127, 0),
     new Color(255,255,0),
     new Color(0,255,0),
     new Color(0,0,255),
     new Color(75,0,130),
     new Color(148,0,211),
     new Color(255,255,255)
  };
  public static Color mapRainbow(float percent) {
    percent = constrain(percent,0,1);
    if(Math.abs(percent) < 0.00001) return rainbow[rainbow.length-1];
    double idx = map(1-percent, 0, 1, 0, (rainbow.length-1));
    int i = (int)idx;
    if(i >=7) System.out.println(percent);
    return fadeBetween(idx-i,rainbow[i+1],rainbow[i]);
  }
}
