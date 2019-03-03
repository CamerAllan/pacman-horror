package implementation;

import static interfaces.Direction.SOUTH;

import interfaces.Direction;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.DeflaterInputStream;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Ghost extends Mover {

  AStarSearch pathFinder;

  public Ghost(PImage image, PVector mapPosition, Map map) {
    this.image = image;
    this.mapPosition = mapPosition;
    this.pixelPosition = new PVector();
    this.currentDirection = SOUTH;
    this.convertMapToPixelPosition();
    this.pathFinder = new AStarSearch(map.getGrid());
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
    Direction nextDirection = pursueMode();

    super.changeDirection(nextDirection, map);
  }

  public Direction pursueMode() {
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
}
