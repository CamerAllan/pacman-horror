package implementation;

import implementation.Map;
import interfaces.Direction;
import java.nio.file.attribute.PosixFileAttributes;
import processing.core.PImage;
import processing.core.PVector;

public  abstract class Mover {
  public final float DEFAULT_SPEED = 0.5f;

  public PImage image;
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
    convertPixelToMapPosition();
    updatePixelPosition(map);
  }

  public int p2M(float pixelFloat) {
    return (int) ((pixelFloat) / Constants.SCALE);
  }

  public float m2P(int mapInt) {
    return mapInt * Constants.SCALE;
  }

  public void convertPixelToMapPosition() {
    mapPosition.x = (pixelPosition.x)  / Constants.SCALE;
    mapPosition.y = (pixelPosition.y) / Constants.SCALE;
  }


  public void convertMapToPixelPosition() {
    pixelPosition.x = mapPosition.x * Constants.SCALE;
    pixelPosition.y = mapPosition.y * Constants.SCALE;
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
//        System.out.println("I want to live :))");
    }
  }

  public void northMovement(int[][] map) {
    System.out.println("NORTH");
    if (!checkNorthCollision(map)) {
      pixelPosition.y -= DEFAULT_SPEED;
    }
  }

  public void southMovement(int[][] map) {
    System.out.println("SOUTH");
    if (!checkSouthCollision(map)) {
      pixelPosition.y += DEFAULT_SPEED;
    }
  }




  public void eastMovement(int[][] map) {
    System.out.println("EAST");
    if (mapPosition.x + 1 < map.length) {
      if (map[(int)mapPosition.x + 1][(int)mapPosition.y] == 1 && pixelPosition.x < m2P((int) mapPosition.x + 1) + 5) {
        pixelPosition.x = (mapPosition.x)  * Constants.SCALE - 0.5f;
        this.currentDirection = Direction.NONE;
      } else {
        pixelPosition.x += DEFAULT_SPEED;
      }
    }
  }

  public void westMovement(int[][] map) {
    System.out.println("WEST");
    if (mapPosition.x - 1 > 0) {
      if (map[(int)mapPosition.x - 1][(int)mapPosition.y] == 1 && pixelPosition.x > m2P((int) mapPosition.x - 1) - 5) {
        pixelPosition.x = (mapPosition.x) * Constants.SCALE + 0.5f;
        this.currentDirection = Direction.NONE;
      } else {
        pixelPosition.x -= DEFAULT_SPEED;
      }
    }
  }

  public boolean checkNorthCollision(int[][] map) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 1) {
          PVector wallPVector = new PVector(i * Constants.SCALE, j * Constants.SCALE);

          if (pixelPosition.y < wallPVector.y + Constants.SCALE + 5
              && pixelPosition.y > wallPVector.y
              && pixelPosition.x > wallPVector.x - 5
              && pixelPosition.x + Constants.SCALE < wallPVector.x + Constants.SCALE + 5
          ) {
            pixelPosition.y = (j + 1) * Constants.SCALE;
            currentDirection = Direction.NONE;
            System.out.println("COLLISION NORTH");
            return true;
          }
        }
      }
    }

    return false;
  }

  public boolean checkSouthCollision(int[][] map) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 1) {
          PVector wallPVector = new PVector(i * Constants.SCALE, j * Constants.SCALE);

          if (pixelPosition.y + Constants.SCALE > wallPVector.y - 5
              && pixelPosition.y + Constants.SCALE < wallPVector.y + Constants.SCALE
              && pixelPosition.x > wallPVector.x - 5
              && pixelPosition.x + Constants.SCALE < wallPVector.x + Constants.SCALE + 5
          ) {
            pixelPosition.y = (j - 1) * Constants.SCALE;
            currentDirection = Direction.NONE;
            System.out.println("COLLISION SOUTH");
            return true;
          }
        }
      }
    }

    return false;
  }

}
