import implementation.Game;
import implementation.Ghost;
import implementation.Map;
import implementation.Player;
import implementation.Light;
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
    size(displayHeight / 2, displayHeight / 2);
  }

  public void setup() {
    Map map = new Map();
    Player player = new Player(this.loadImage("pacman-open.png"), new PVector(2, 5));
    List<Ghost> ghosts = new ArrayList<>();
    ghosts.add(new Ghost(this.loadImage("ghost-blue.png"), new PVector(3, 3), map));
    Light light = new Light(this.loadImage("light.png"), player.getPixelPosition());
    this.game = new Game(map, player, ghosts, light);
  }

  public void draw() {
    this.game.update(this);
    this.game.draw(this);
  }
}
