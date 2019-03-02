package interfaces;

import processing.core.PVector;

public interface Player {

  PVector getPos();

  Direction getDirection();

  void move(Direction dir);

}
