import interfaces.Game;
import processing.core.PApplet;

public class Main extends PApplet {

  Game game;

  public static void main(String[] args) {
    PApplet.runSketch(new String[] { Main.class.getName() }, new Main());
  }

  public void settings() {
  }

  public void setup() {
//    this.game = new GameImpl();
  }

  public void draw() {
  }

  public void keyPressed() {
    char upperKey = Character.toUpperCase(key);

  }

  public void keyReleased() {
    char upperKey = Character.toUpperCase(key);

  }
}
