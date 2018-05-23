import org.usfirst.frc.team4999.client.*;
import org.usfirst.frc.team4999.lights.*;
import processing.sound.*;

Client c;

PFont font;

Chooser chooser;

void setup() {
  c = new Client("localhost", 5800);
  
  size(1024, 360);
  background(255);
  
  chooser = new Chooser(this, c);
  
  
  font = createFont("Arial", 16, true);
}



void draw() {
  background(255);
  
  chooser.draw();
  textFont(font);
  if(c.getConnected()) {
      fill(0,255,0);
      text("Connected", width - 90, 20);
    } else {
      fill(255,0,0);
      text("No Connection", width - 120, 20);
    }
  
}

void keyPressed() {
  if(key == 'r') {
    if(c.reconnect()) {
      chooser.setup();
    }
  }
}
