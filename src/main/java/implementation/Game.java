package implementation;

import interfaces.Direction;
import interfaces.GameStatus;
import java.util.List;
import processing.core.PApplet;

public class Game {

  Map map;
  Player player;
  List<Ghost> ghosts;
  GameStatus gameStatus;

  public Game(Map map, Player player, List<Ghost> ghosts) {
    this.map = map;
    this.player = player;
    this.ghosts = ghosts;
  }

  public void update(PApplet app) {
    handleInput(app);
    updatePlayer();
    updateGhosts();
    updateGameState();
  }

  private void handleInput(PApplet app) {
    switch (app.keyCode){
      case PApplet.UP: {
        player.changeDirection(Direction.NORTH);
      }
      case PApplet.DOWN: {
        player.changeDirection(Direction.SOUTH);
      }
      case PApplet.LEFT: {
        player.changeDirection(Direction.WEST);
      }
      case PApplet.RIGHT: {
        player.changeDirection(Direction.EAST);
      }
    }
  }

  private void updatePlayer() {
    player.update(this.map.getGrid());
  }

  private void updateGhosts() {
    for (Ghost ghost: this.ghosts) {
      ghost.update(this.map.getGrid());
    }
  }

  private void updateGameState() {
    for (Ghost ghost: this.ghosts) {
      if (ghost.getMapPosition().x == player.getMapPosition().x
          && ghost.getMapPosition().y == player.getMapPosition().y) {
        this.gameStatus = GameStatus.LOST;
      }
    }
  }

  public void draw(PApplet app) {
    this.map.draw(app);
    this.player.draw(app);
    for (Ghost ghost: this.ghosts) {
      ghost.draw(app);
    }
  }
}