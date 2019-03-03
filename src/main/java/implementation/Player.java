package implementation;

import static interfaces.Direction.SOUTH;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Player extends Mover {

  int score;

  public Player(PImage image, PVector mapPosition) {
    this.image = image;
    this.mapPosition = mapPosition;
    this.pixelPosition = new PVector();
    this.currentDirection = SOUTH;
    this.convertMapToPixelPosition();
    this.score = 0;
  }

  public void draw(PApplet app) {
    app.imageMode(PApplet.CORNER);
    app.image(this.image, this.pixelPosition.x, this.pixelPosition.y, Constants.SCALE,
        Constants.SCALE);
  }

  @Override
  public void update(Map map) {

    super.update(map);
    if (map.getGrid()[(int) this.mapPosition.x][(int) this.mapPosition.y] == 2) {
      map.eat((int) this.mapPosition.x, (int) this.mapPosition.y);
      score++;
    }

    if ((int) this.mapPosition.x == 0 && (int) this.mapPosition.y == 11) {
      this.mapPosition.x = map.getGrid()[0].length - 2;
      this.convertMapToPixelPosition();
    }

    if ((int) this.mapPosition.x == map.getGrid()[0].length - 1 && (int) this.mapPosition.y == 11) {
      this.mapPosition.x = 1;
      this.convertMapToPixelPosition();
    }
  }
}
