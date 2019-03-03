package implementation;

import static interfaces.Direction.SOUTH;

import interfaces.Direction;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Ghost extends Mover {

  public Ghost(PImage image, PVector mapPosition) {
    this.image = image;
    this.mapPosition = mapPosition;
    this.pixelPosition = new PVector();
    this.currentDirection = SOUTH;
    this.convertMapToPixelPosition();
  }

  public void draw(PApplet app) {
    app.image(this.image, this.pixelPosition.x, this.pixelPosition.y);
  }

  @Override
  public void update(int[][] map) {
    convertPixelToMapPosition();
    updatePixelPosition(map);
  }

  @Override
  public void updatePixelPosition(int[][] map) {
    switch(currentDirection) {
      case NORTH:
        northMovement(map);
        break;
      case EAST:
        eastMovement(map);
        break;
      case SOUTH:
        southMovement(map);
        break;
      case WEST:
        westMovement(map);
        break;
    }
  }

  @Override
  public void northMovement(int[][] map) {
    if (mapPosition.y > 0 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.y -= DEFAULT_SPEED;
    } else {
      makeGhostDirectionChoice();
    }
  }

  @Override
  public void eastMovement(int[][] map) {
    if (mapPosition.x + Constants.SCALE < map[0].length - 1 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.x += DEFAULT_SPEED;
    } else {
      makeGhostDirectionChoice();
    }
  }

  @Override
  public void southMovement(int[][] map) {
    if (mapPosition.y + Constants.SCALE < map.length - 1 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.y += DEFAULT_SPEED;
    } else {
      makeGhostDirectionChoice();
    }
  }

  @Override
  public void westMovement(int[][] map) {
    if (mapPosition.x > 0 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.x -= DEFAULT_SPEED;
    } else {
      makeGhostDirectionChoice();
    }
  }

  public void makeGhostDirectionChoice() {
    // TODO: Make smarter. Add A* decision sometimes. Add choosing random not including current dirrection
    currentDirection = Direction.randomDirection();
  }
}
