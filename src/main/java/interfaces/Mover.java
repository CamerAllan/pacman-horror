package interfaces;

import implementation.Map;
import processing.core.PVector;

public class Mover {
  public final int DEFAULT_SCALE = 50;
  public final float DEFAULT_SPEED = 10;

  public PVector pixelPosition;
  public PVector mapPosition;
  public Direction currentDirection;

  public PVector getPixelPosition() {
    return pixelPosition;
  }

  public PVector getMapPosition() {
    return mapPosition;
  }

  public Direction getCurrentDirection() {
    return currentDirection;
  }

  public void update(int[][] map) {

    convertPixelToMapPosition(DEFAULT_SCALE);
    updatePixelPosition(map);

    // MOVE BITCH

    // GET OUT THE WAY

    // GET OUT THE WAY BITCH

    // GET OUT THE WAYYY
  }

  public void convertPixelToMapPosition(int scale) {
    mapPosition.x = pixelPosition.x / scale;
    mapPosition.y = pixelPosition.y / scale;
  }

  public void changeDirection(Direction moveDirection) {
    currentDirection = moveDirection;
  }

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

  public void northMovement(int[][] map) {
    if (mapPosition.y > 0 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.y -= DEFAULT_SPEED;
    }
  }

  public void eastMovement(int[][] map) {
    if (mapPosition.x < map[0].length - 1 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.x += DEFAULT_SPEED;
    }
  }

  public void southMovement(int[][] map) {
    if (mapPosition.y < map.length - 1 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.y += DEFAULT_SPEED;
    }
  }

  public void westMovement(int[][] map) {
    if (mapPosition.x > 0 && map[(int) mapPosition.x][(int) mapPosition.y] == 0) {
      pixelPosition.x -= DEFAULT_SPEED;
    }
  }
}
