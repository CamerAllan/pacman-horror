package implementation;

import static interfaces.Direction.SOUTH;

import interfaces.Mover;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Player extends Mover {

  public Player(PImage image, PVector mapPosition) {
    this.image = image;
    this.mapPosition = mapPosition;
    this.pixelPosition = new PVector();
    this.currentDirection = SOUTH;
  }

  public void draw(PApplet app, int scale) {
    app.image(this.image, this.pixelPosition.x, this.pixelPosition.y, scale , scale );
  }

}
