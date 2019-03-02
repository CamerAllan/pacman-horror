package interfaces;

import processing.core.PVector;

public interface Ghost {

  PVector getPos();

  Direction getDirection();

  void move(Direction dir);

}
