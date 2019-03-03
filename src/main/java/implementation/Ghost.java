package implementation;

import static interfaces.Direction.SOUTH;
import static processing.core.PApplet.second;

import interfaces.Direction;
import interfaces.GhostPersonality;
import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Ghost extends Mover {

  AStarSearch pathFinder;
  PImage otherImage;
  GhostPersonality personality;
  int scatterPathCornerCounter = 0;
  ArrayList<AStarNode> currentScatterPath;
  int lastRecordedTime;
  int chaseTime = 20;
  int scatterTime = 7;
  int behaviourSwitchCount = 1;

  public Ghost(PImage image, PImage otherImage, PVector mapPosition, Map map,
      GhostPersonality personality) {
    this.image = image;
    this.otherImage = otherImage;
    this.mapPosition = mapPosition;
    this.pixelPosition = new PVector();
    this.currentDirection = SOUTH;
    this.convertMapToPixelPosition();
    this.pathFinder = new AStarSearch(map.getGrid());
    this.personality = personality;
    this.lastRecordedTime = second();
  }

  public void draw(PApplet app, boolean edible) {
    app.imageMode(PApplet.CORNER);
    if (edible) {
      app.image(this.otherImage, this.pixelPosition.x, this.pixelPosition.y, Constants.SCALE,
          Constants.SCALE);
    } else {
      app.image(this.image, this.pixelPosition.x, this.pixelPosition.y, Constants.SCALE,
          Constants.SCALE);
    }
  }

  @Override
  public void update(Map map) {
    convertPixelToMapPosition();
    updatePixelPosition(map.getAIGrid());
    makeGhostDirectionChoice(map.getAIGrid());
  }

//  @Override
//  public void convertPixelToMapPosition() {
//    mapPosition.x = Math.round((pixelPosition.x)  / (float) Constants.SCALE);
//    mapPosition.y = Math.round((pixelPosition.y) / (float) Constants.SCALE);
//  }

  public void makeGhostDirectionChoice(int[][] map) {
    // TODO: Make smarter. Add A* decision sometimes. Add choosing random not including current dirrection
    Direction nextDirection;

    if (behaviourSwitchCount % 2 == 1) {
      // Scatter mode
      if (second() - scatterTime > lastRecordedTime) {
        behaviourSwitchCount++;
        lastRecordedTime = second();
      }

      nextDirection = scatterMode();
    } else {
      // Chase mode
      if (second() - chaseTime > lastRecordedTime) {
        behaviourSwitchCount++;
        lastRecordedTime = second();
      }

      nextDirection = chaseMode();
    }

    super.changeDirection(nextDirection, map);
  }

  public Direction chaseMode() {
    Random random = new Random();

    if (random.nextInt(10) == 1) {
      return Direction.randomDirection();
    } else {
      ArrayList<AStarNode> result = pathFinder
          .search((int) Game.player.mapPosition.x, (int) Game.player.mapPosition.y,
              (int) this.mapPosition.x, (int) this.mapPosition.y);

      return getDirectionToAStarNodeChase(result);
    }
  }

  public Direction getDirectionToAStarNodeScatter(ArrayList<AStarNode> result) {
    // failure is represented as a null return
    if (result != null && result.size() > 0) {
      AStarNode nextNode = result.get(result.size() - 1);

      if (nextNode.getRow() == (int) mapPosition.x && nextNode.getCol() == (int) mapPosition.y) {
        if (result.size() > 1) {
          result.remove(result.size() - 1);
          nextNode = result.get(result.size() - 1);
        } else {
          scatterPathCornerCounter++;
          return currentDirection;
        }
      }

      if (nextNode.getCol() < (int) mapPosition.y) {
        return Direction.NORTH;
      } else if (nextNode.getCol() > (int) mapPosition.y) {
        return Direction.SOUTH;
      } else if (nextNode.getRow() < (int) mapPosition.x) {
        return Direction.WEST;
      } else if (nextNode.getRow() > (int) mapPosition.x) {
        return Direction.EAST;
      }
    }

    scatterPathCornerCounter++;
    return currentDirection;
  }


  public Direction scatterMode() {
    switch (personality) {
      case RED:
        return getScatterDirection(Constants.redScatterCorners);
      case PINK:
        return getScatterDirection(Constants.pinkScatterCorners);
      case BLUE:
        return getScatterDirection(Constants.blueScatterCorners);
      case ORANGE:
        return getScatterDirection(Constants.orangeScatterCorners);
      default:
        return currentDirection;
    }
  }

  public Direction getDirectionToAStarNodeChase(ArrayList<AStarNode> result) {
    // failure is represented as a null return
    if (result != null && result.size() > 0) {
      AStarNode nextNode = result.get(0);

      if (nextNode.getRow() == (int) mapPosition.x && nextNode.getCol() == (int) mapPosition.y) {
        if (result.size() > 1) {
          nextNode = result.get(1);
        } else {
          return currentDirection;
        }
      }

      if (nextNode.getCol() < (int) mapPosition.y) {
        return Direction.NORTH;
      } else if (nextNode.getCol() > (int) mapPosition.y) {
        return Direction.SOUTH;
      } else if (nextNode.getRow() < (int) mapPosition.x) {
        return Direction.WEST;
      } else if (nextNode.getRow() > (int) mapPosition.x) {
        return Direction.EAST;
      }
    }

    return currentDirection;
  }

  public void die() {
    this.mapPosition.x = 10;
    this.mapPosition.y = 11;
    this.convertMapToPixelPosition();
  }


  public Direction getScatterDirection(int[][] scatterCorners) {
    try {
      currentScatterPath = pathFinder.search((int) mapPosition.x, (int) mapPosition.y,
          scatterCorners[scatterPathCornerCounter % 4][0],
          scatterCorners[scatterPathCornerCounter % 4][1]);
      return getDirectionToAStarNodeScatter(currentScatterPath);
    } catch (Exception e) {
      return currentDirection;
    }
  }
}