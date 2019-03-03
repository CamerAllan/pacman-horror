package implementation;

import static interfaces.Direction.*;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Player extends Mover {

  int score;

  public Player(PImage image, PVector mapPosition) {
  PImage imageMiddleN;
  PImage imageOpenN;
  PImage imageMiddleS;
  PImage imageOpenS;
  PImage imageMiddleE;
  PImage imageOpenE;
  PImage imageMiddleW;
  PImage imageOpenW;

  int imageCounter;

  public Player(PImage image,  PImage imageMiddleN, PImage imageOpenN, PImage imageMiddleS,
                PImage imageOpenS, PImage imageMiddleE, PImage imageOpenE, PImage imageMiddleW,
                PImage imageOpenW, PVector mapPosition) {
    this.image = image;
    this.mapPosition = mapPosition;
    this.pixelPosition = new PVector();
    this.currentDirection = SOUTH;
    this.convertMapToPixelPosition();
    this.imageMiddleN = imageMiddleN;
    this.imageOpenN = imageOpenN;
    this.imageMiddleS = imageMiddleS;
    this.imageOpenS = imageOpenS;
    this.imageMiddleE = imageMiddleE;
    this.imageOpenE = imageOpenE;
    this.imageMiddleW = imageMiddleW;
    this.imageOpenW = imageOpenW;
    imageCounter = 0;
    this.score = 0;
  }

  public void draw(PApplet app) {
    app.imageMode(PApplet.CORNER);
    if(currentDirection == SOUTH) {
      if(imageCounter / 10 == 1) app.image(this.image, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
      else if(imageCounter / 10 == 2) app.image(imageMiddleS, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
      else app.image(imageOpenS, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
    }
    else if(currentDirection == NORTH) {
      if(imageCounter / 10 == 1) app.image(this.image, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
      else if(imageCounter / 10 == 2) app.image(imageMiddleN, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
      else app.image(imageOpenN, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);;
    }
    else if(currentDirection == WEST) {
      if(imageCounter / 10 == 1) app.image(this.image, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
      else if(imageCounter / 10 == 2) app.image(imageMiddleW, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
      else app.image(imageOpenW, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
    }
    else if(currentDirection == EAST) {
      if(imageCounter / 10 == 1) app.image(this.image, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
      else if(imageCounter / 10 == 2) app.image(imageMiddleE, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
      else app.image(imageOpenE, pixelPosition.x, pixelPosition.y, Constants.SCALE, Constants.SCALE);
    }
    imageCounter++;
    if(imageCounter > 30) imageCounter = 0;
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
