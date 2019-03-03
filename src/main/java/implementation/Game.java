package implementation;

import ddf.minim.AudioPlayer;
import interfaces.Direction;
import interfaces.GameStatus;
import java.util.List;
import processing.core.PApplet;
import processing.core.PConstants;

public class Game {

  public static final int POWERUP_TIME = 5000;

  public Map map;
  public static Player player;
  private final int SIGHT_DISTANCE = 4;
  public boolean startedGame = false;


  List<Ghost> ghosts;
  GameStatus gameStatus;
  Light light;

  long gameStart;
  HomePage homePage;
  boolean done = false;

  int randomTimer;

  public Game(PApplet app, Map map, Player player, List<Ghost> ghosts, Light light, HomePage homePage) {


    this.map = map;
    this.player = player;
    this.ghosts = ghosts;
    this.light = light;
    seenTimer = app.millis() - 10000;
    this.homePage = homePage;
    randomTimer = app.millis();
    gameStart = System.currentTimeMillis();
  }

  public void update(PApplet app, AudioPlayer menuPlayer, AudioPlayer background1Player, AudioPlayer background2Player, AudioPlayer background3Player, AudioPlayer background4Player, AudioPlayer movePlayer, AudioPlayer deathPlayer, AudioPlayer yumPlayer, AudioPlayer ghostDeathPlayer, AudioPlayer screamPlayer, AudioPlayer screechPlayer, AudioPlayer surprisePlayer) {

    if (System.currentTimeMillis() - this.gameStart > 5000) {
      this.map.unleash();
    }

    handleInput(app, menuPlayer, movePlayer);
    if(startedGame) {
      updatePlayer(app, screamPlayer);
      updateLight();
      updateGhosts();
      updateGameState(deathPlayer, ghostDeathPlayer);
      int timer = app.millis() - randomTimer; //every 60 seconds the wind changes
      if (timer >= 10000) {
        randomTimer = app.millis();
        int range = (6 - 1) + 1;
        int randomNum = (int) (Math.random() * range) + 1;
        switch (randomNum) {
          case 1:
            background1Player.rewind();
            background1Player.play();
            break;
          case 2:
            background2Player.rewind();
            background2Player.play();
            break;
          case 3:
            background3Player.rewind();
            background3Player.play();
            break;
          case 4:
            background4Player.rewind();
            background4Player.play();
            break;
          case 5:
            screechPlayer.rewind();
            screechPlayer.play();
            break;
          case 6:
            surprisePlayer.rewind();
            surprisePlayer.play();
            break;
        }
      }
    }
  }

  private void handleInput(PApplet app, AudioPlayer menuPlayer, AudioPlayer movePlayer) {
    if (app.keyPressed && startedGame) {
      switch (app.keyCode) {
        case PApplet.UP: {
          player.changeDirection(Direction.NORTH, map.getGrid());
          break;
        }
        case PApplet.DOWN: {
          player.changeDirection(Direction.SOUTH, map.getGrid());
          break;
        }
        case PApplet.LEFT: {
          player.changeDirection(Direction.WEST, map.getGrid());
          break;
        }
        case PApplet.RIGHT: {
          player.changeDirection(Direction.EAST, map.getGrid());
          break;
        }
      }
    }
    else if(app.keyPressed && app.key == ' ') {
        menuPlayer.pause();
        startedGame = true;
        movePlayer.loop();
    }
  }

  int seenTimer;

  private void updatePlayer(PApplet app, AudioPlayer screamPlayer) {

    player.update(this.map);
    for (Ghost ghost : this.ghosts) {
      double distance = Math.sqrt(Math.pow(ghost.getMapPosition().x - player.getMapPosition().x, 2) + Math.pow(ghost.getMapPosition().y - player.getMapPosition().y, 2));
      if (distance < SIGHT_DISTANCE) {
        int timer = app.millis()-seenTimer;
        if (timer >= 10000) {
          seenTimer = app.millis();
          screamPlayer.rewind();
          screamPlayer.play();
        }
      }
    }
  }

  private void updateGhosts() {
    for (Ghost ghost: this.ghosts) {
      if (System.currentTimeMillis() - player.powerUpGot < POWERUP_TIME) {
        // CHANGE BEHAVIOUR BASED ON EDIBILITY
      }
      ghost.update(this.map);
    }
  }

  private void updateLight() {
    light.update(this.player.getPixelPosition());
  }


  private void updateGameState(AudioPlayer deathPlayer, AudioPlayer ghostDeath) {
    for (Ghost ghost: this.ghosts) {
      if (ghost.pixelPosition.x > player.pixelPosition.x - Constants.SCALE + 5
          && ghost.pixelPosition.y > player.pixelPosition.y - Constants.SCALE + 5
          && ghost.pixelPosition.x < player.pixelPosition.x + Constants.SCALE - 5
          && ghost.pixelPosition.y < player.pixelPosition.y + Constants.SCALE - 5) {
        if (System.currentTimeMillis() - player.powerUpGot < POWERUP_TIME) {
          ghostDeath.rewind();
          ghostDeath.play();
          ghost.die();
          map.leash();
          gameStart = System.currentTimeMillis();
          player.score += 10;
        } else {
          this.gameStatus = GameStatus.LOST;
          if (!done) {
            deathPlayer.rewind();
            deathPlayer.play();
            done = true;
          }
        }

      }
    }
  }

  public void draw(PApplet app) {
    if(!startedGame) {
      homePage.draw(app);
    }
    else {
        this.map.draw(app);
        this.player.draw(app);
        for (Ghost ghost: this.ghosts) {
            ghost.draw(app, System.currentTimeMillis() - player.powerUpGot < POWERUP_TIME);
        }

    this.light.draw(app);

        app.color(255);
        app.textSize(30);
        app.textMode(PConstants.CENTER);
        app.text(this.player.score, app.width / 2, 100);
        if (this.gameStatus == GameStatus.LOST){
            app.text("You lose!", app.width / 2, 200);
        }
    }
  }
}
