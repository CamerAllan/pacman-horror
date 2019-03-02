package interfaces;

import processing.core.PVector;

public interface IPlayer extends Drawable{

  PVector getPos();

  Direction getDirection();

  void move(Direction dir);

}
