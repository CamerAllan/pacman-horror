package interfaces;

import processing.core.PVector;

public interface IGhost extends Drawable {

  PVector getPos();

  Direction getDirection();

  void move(Direction dir);

}
