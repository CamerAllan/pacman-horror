package implementation;

import static interfaces.Direction.NORTH;

import interfaces.Direction;
import interfaces.IGhost;
import interfaces.Mover;
import processing.core.PImage;
import processing.core.PVector;

public class Ghost extends Mover {

  @Override
  public void update(int[][] map) {
    convertPixelToMapPosition(DEFAULT_SCALE);
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
      default:
        System.out.println("I want to die :))");
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
    if (mapPosition.x < map[0].length - 1 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.x += DEFAULT_SPEED;
    } else {
      makeGhostDirectionChoice();
    }
  }

  @Override
  public void southMovement(int[][] map) {
    if (mapPosition.y < map.length - 1 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
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
