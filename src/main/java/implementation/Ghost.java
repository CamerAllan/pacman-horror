package implementation;

import static interfaces.Direction.SOUTH;

import interfaces.Direction;
import interfaces.GhostPersonality;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Ghost extends Mover {

  AStarSearch pathFinder;
  GhostPersonality personality;
  int scatterPathCornerCounter = 0;
  ArrayList<AStarNode> currentScatterPath;

  public Ghost(PImage image, PVector mapPosition, Map map, GhostPersonality personality) {
    this.image = image;
    this.mapPosition = mapPosition;
    this.pixelPosition = new PVector();
    this.currentDirection = SOUTH;
    this.convertMapToPixelPosition();
    this.pathFinder = new AStarSearch(map.getGrid());
    this.personality = personality;
  }

  public void draw(PApplet app) {
    app.imageMode(PApplet.CORNER);
    app.image(this.image, this.pixelPosition.x, this.pixelPosition.y, Constants.SCALE, Constants.SCALE);
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
    Direction nextDirection = scatterMode();

    super.changeDirection(nextDirection, map);
  }

  public Direction chaseMode() {
    Random random = new Random();

    if (random.nextInt(10) == 1) {
      return Direction.randomDirection();
    } else {
      ArrayList<AStarNode> result = pathFinder.search((int) Game.player.mapPosition.x, (int) Game.player.mapPosition.y, (int) this.mapPosition.x, (int) this.mapPosition.y) ;

      return getDirectionToAStarNode(result);
    }
  }

  public Direction getDirectionToAStarNode(ArrayList<AStarNode> result) {
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


  public Direction scatterMode() {
    switch (personality) {
      case RED:
        getScatterDirection(Constants.redScatterCorners);
        break;
      case PINK:
        getScatterDirection(Constants.pinkScatterCorners);
        break;
      case BLUE:
        getScatterDirection(Constants.blueScatterCorners);
        break;
      case ORANGE:
        getScatterDirection(Constants.orangeScatterCorners);
        break;
      default:
        return currentDirection;
    }
    return currentDirection;
  }

  public Direction getScatterDirection(int[][] scatterCorners) {
    if (currentScatterPath == null || currentScatterPath.size() == 0) {
      currentScatterPath = pathFinder.search((int) mapPosition.x, (int) mapPosition.y,
          scatterCorners[scatterPathCornerCounter % 4][0], scatterCorners[scatterPathCornerCounter % 4][1]);
        scatterPathCornerCounter++;
    }

    if (currentScatterPath != null && currentScatterPath.size() > 0) {
      AStarNode nextNode = currentScatterPath.get(0);
      if (nextNode.getRow() == (int) mapPosition.x && nextNode.getCol() == (int) mapPosition.y) {
        currentScatterPath.remove(0);
      }
    }

    return getDirectionToAStarNode(currentScatterPath);
  }

}
