import implementation.Game;
import implementation.Ghost;
import implementation.Map;
import implementation.Player;
import interfaces.IGame;
import interfaces.IMap;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {

  Game game;

  public static void main(String[] args) {
    PApplet.runSketch(new String[] { Main.class.getName() }, new Main());
  }

  public void settings() {
    size((int) displayWidth / 2, (int) displayHeight / 2);
  }

  public void setup() {
    Map map = new Map();
    Player player = new Player(this.loadImage("pacman-open.png"), new PVector(3, 3));
    List<Ghost> ghosts = new ArrayList<>();
    this.game = new Game(map, player, ghosts);

  }

  public void draw() {
    this.game.update(this);
    this.game.draw(this, 25);
  }

  public void keyPressed() {
    char upperKey = Character.toUpperCase(key);

  }

  public void keyReleased() {
    char upperKey = Character.toUpperCase(key);

  }
}
