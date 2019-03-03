package implementation;

import implementation.Map;
import interfaces.Direction;
import java.nio.file.attribute.PosixFileAttributes;
import processing.core.PImage;
import processing.core.PVector;

public  abstract class Mover {

  public static final float FLUFF = ((float) Constants.SCALE) / 5f;
  public final float DEFAULT_SPEED = 1f;

  public PImage image;
  public PVector pixelPosition;
  public PVector mapPosition;
  public Direction currentDirection;
  public Direction lastDirection;

  public PVector getPixelPosition() {
    return pixelPosition;
  }

  public PVector getMapPosition() {
    return mapPosition;
  }

  public Direction getCurrentDirection() {
    return currentDirection;
  }

  public void setCurrentDirection(Direction newDirection) {
    if (currentDirection != Direction.NONE) {
      this.lastDirection = currentDirection;
    }
    this.currentDirection = newDirection;
  }

  public void update(Map map) {
    convertPixelToMapPosition();
    updatePixelPosition(map.getGrid());
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

  public void changeDirection(Direction moveDirection, int[][] map) {
    switch(moveDirection) {
      case NORTH:
        attemptNorthTurn(map);
        break;
      case EAST:
        attemptEastTurn(map);
        break;
      case SOUTH:
        attemptSouthTurn(map);
        break;
      case WEST:
        attemptWestTurn(map);
        break;
      default:
    }
  }

  public void attemptNorthTurn(int[][] map) {
    PVector northSpacePVector = new PVector(p2M(pixelPosition.x), p2M(pixelPosition.y));

    if (currentDirection == Direction.NONE
        || currentDirection == Direction.SOUTH
        || ((map[(int) northSpacePVector.x][(int) northSpacePVector.y - 1] != 1) && pixelPosition.x > (northSpacePVector.x * Constants.SCALE) - FLUFF && pixelPosition.x < (northSpacePVector.x * Constants.SCALE) + FLUFF)
    ) {
      setCurrentDirection(Direction.NORTH);
    }
  }

  public void attemptSouthTurn(int[][] map) {
    PVector southSpacePVector = new PVector(p2M(pixelPosition.x), p2M(pixelPosition.y));

    if (currentDirection == Direction.NONE
        || currentDirection == Direction.NORTH
        || (map[(int) southSpacePVector.x][(int) southSpacePVector.y + 1] != 1 && pixelPosition.x > (southSpacePVector.x * Constants.SCALE) - FLUFF && pixelPosition.x < (southSpacePVector.x * Constants.SCALE) + FLUFF)
    ) {
      setCurrentDirection(Direction.SOUTH);
    }
  }

  public void attemptEastTurn(int[][] map) {
    PVector eastSpacePVector = new PVector(p2M(pixelPosition.x), p2M(pixelPosition.y));

    if (currentDirection == Direction.NONE
        || currentDirection == Direction.WEST
        || (map[(int) eastSpacePVector.x + 1][(int) eastSpacePVector.y] != 1 && pixelPosition.y > (eastSpacePVector.y * Constants.SCALE) - FLUFF && pixelPosition.y < (eastSpacePVector.y * Constants.SCALE) + FLUFF)
    ) {
      setCurrentDirection(Direction.EAST);
    }
  }

  public void attemptWestTurn(int[][] map) {
    PVector westSpacePVector = new PVector(p2M(pixelPosition.x), p2M(pixelPosition.y));

    if (
        currentDirection == Direction.NONE
        || currentDirection == Direction.EAST
        || (map[(int) westSpacePVector.x - 1][(int) westSpacePVector.y] != 1 && pixelPosition.y > (westSpacePVector.y * Constants.SCALE) - FLUFF && pixelPosition.y < (westSpacePVector.y * Constants.SCALE) + FLUFF)
    ) {
      setCurrentDirection(Direction.WEST);
    }
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
    if (!checkNorthCollision(map)) {
      pixelPosition.y -= DEFAULT_SPEED;
    }
  }

  public void southMovement(int[][] map) {
    if (!checkSouthCollision(map)) {
      pixelPosition.y += DEFAULT_SPEED;
    }
  }




  public void eastMovement(int[][] map) {
    if (!checkEastCollision(map)) {
      pixelPosition.x += DEFAULT_SPEED;
    }
  }

  public void westMovement(int[][] map) {
    if (!checkWestCollision(map)) {
      pixelPosition.x -= DEFAULT_SPEED;
    }
  }

  public boolean checkNorthCollision(int[][] map) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 1) {
          PVector wallPVector = new PVector(i * Constants.SCALE, j * Constants.SCALE);

          if (pixelPosition.y < wallPVector.y + Constants.SCALE + FLUFF
              && pixelPosition.y > wallPVector.y
              && pixelPosition.x > wallPVector.x - FLUFF
              && pixelPosition.x + Constants.SCALE < wallPVector.x + Constants.SCALE + FLUFF
          ) {
            pixelPosition.y = (j + 1) * Constants.SCALE;
            setCurrentDirection(Direction.NONE);
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

          if (pixelPosition.y + Constants.SCALE > wallPVector.y - FLUFF
              && pixelPosition.y + Constants.SCALE < wallPVector.y + Constants.SCALE
              && pixelPosition.x > wallPVector.x - FLUFF
              && pixelPosition.x + Constants.SCALE < wallPVector.x + Constants.SCALE + FLUFF
          ) {
            pixelPosition.y = (j - 1) * Constants.SCALE;
            setCurrentDirection(Direction.NONE);
            return true;
          }
        }
      }
    }

    return false;
  }

  public boolean checkWestCollision(int[][] map) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 1) {
          PVector wallPVector = new PVector(i * Constants.SCALE, j * Constants.SCALE);

          if (pixelPosition.x < wallPVector.x + Constants.SCALE + FLUFF
              && pixelPosition.x > wallPVector.x
              && pixelPosition.y > wallPVector.y - FLUFF
              && pixelPosition.y + Constants.SCALE < wallPVector.y + Constants.SCALE + FLUFF
          ) {
            pixelPosition.x = (i + 1) * Constants.SCALE;
            setCurrentDirection(Direction.NONE);
            return true;
          }
        }
      }
    }

    return false;
  }

  public boolean checkEastCollision(int[][] map) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == 1) {
          PVector wallPVector = new PVector(i * Constants.SCALE, j * Constants.SCALE);

          if (pixelPosition.x + Constants.SCALE > wallPVector.x - FLUFF
              && pixelPosition.x + Constants.SCALE < wallPVector.x + Constants.SCALE
              && pixelPosition.y > wallPVector.y - FLUFF
              && pixelPosition.y + Constants.SCALE < wallPVector.y + Constants.SCALE + FLUFF
          ) {
            pixelPosition.x = (i - 1) * Constants.SCALE;
            setCurrentDirection(Direction.NONE);
            return true;
          }
        }
      }
    }

    return false;
  }

}
