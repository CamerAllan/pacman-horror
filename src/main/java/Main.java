import implementation.Map;
import interfaces.IGame;
import interfaces.IMap;
import processing.core.PApplet;

public class Main extends PApplet {

  IGame game;
  IMap map;

  public static void main(String[] args) {
    PApplet.runSketch(new String[] { Main.class.getName() }, new Main());
  }

  public void settings() {
  }

  public void setup() {
//    this.game = new GameImpl();
    this.map = new Map();
  }

  public void draw() {
   // map.draw();
  }

  public void keyPressed() {
    char upperKey = Character.toUpperCase(key);

  }

  public void keyReleased() {
    char upperKey = Character.toUpperCase(key);

  }
}
