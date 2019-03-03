import implementation.Game;
import implementation.Ghost;
import implementation.Map;
import implementation.Player;
import implementation.Light;
import interfaces.GhostPersonality;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.core.PVector;

import ddf.minim.*;

public class Main extends PApplet {

  Game game;

  Minim minim;
  AudioPlayer menuPlayer;
  AudioPlayer background1Player;
  AudioPlayer background2Player;
  AudioPlayer background3Player;
  AudioPlayer background4Player;
  AudioPlayer movePlayer;
  AudioPlayer deathPlayer;
  AudioPlayer yumPlayer;
  AudioPlayer ghostDeathPlayer;
  AudioPlayer screamPlayer;
  AudioPlayer screechPlayer;
  AudioPlayer surprisePlayer;



  public static void main(String[] args) {
    PApplet.runSketch(new String[] { Main.class.getName() }, new Main());
  }

  public void settings() {
    size(displayHeight / 2, displayHeight / 2);
  }

  public void setup() {
    Map map = new Map();
    Player player = new Player(this.loadImage("pacman-closed.png"),
            this.loadImage("pacman-middle-n.png"),
            this.loadImage("pacman-open-n.png"),
            this.loadImage("pacman-middle-s.png"),
            this.loadImage("pacman-open-s.png"),
            this.loadImage("pacman-middle-e.png"),
            this.loadImage("pacman-open-e.png"),
            this.loadImage("pacman-open-w.png"),
            this.loadImage("pacman-middle-w.png"),
            new PVector(2, 5));

    List<Ghost> ghosts = new ArrayList<>();
    ghosts.add(new Ghost(this.loadImage("ghost-blue.png"), new PVector(3, 3), map, GhostPersonality.RED));
    ghosts.add(new Ghost(this.loadImage("ghost-blue.png"), new PVector(9, 11), map, GhostPersonality.PINK));
    ghosts.add(new Ghost(this.loadImage("ghost-orange.png"), new PVector(11, 11), map, GhostPersonality.BLUE));
    ghosts.add(new Ghost(this.loadImage("ghost-pink.png"), new PVector(10, 11), map, GhostPersonality.ORANGE));
    Light light = new Light(this.loadImage("light.png"), player.getPixelPosition());
    this.game = new Game(this, map, player, ghosts, light);

    System.out.println(sketchPath());
    minim = new Minim(this);

    menuPlayer = minim.loadFile("mainmenu.mp3");
    background1Player = minim.loadFile("bg1.mp3");
    background2Player = minim.loadFile("bg2.mp3");
    background3Player = minim.loadFile("bg3.mp3");
    background4Player = minim.loadFile("breathe.mp3");
    movePlayer = minim.loadFile("move.mp3");
    deathPlayer = minim.loadFile("death.mp3");
    yumPlayer = minim.loadFile("yum.mp3");
    ghostDeathPlayer = minim.loadFile("ghostiedie.mp3");
    screamPlayer = minim.loadFile("scream.mp3");
    screechPlayer = minim.loadFile("screech.mp3");
    surprisePlayer = minim.loadFile("surprise.mp3");

//    deathPlayer.rewind();
//    deathPlayer.play();

   // menuPlayer.loop();

    movePlayer.loop();

  }

  public void draw() {
    this.game.update(this, menuPlayer, background1Player, background2Player, background3Player, background4Player, movePlayer, deathPlayer, yumPlayer, ghostDeathPlayer, screamPlayer, screechPlayer, surprisePlayer);
    this.game.draw(this);
  }
}
