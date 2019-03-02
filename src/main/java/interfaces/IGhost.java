package interfaces;

import processing.core.PVector;

public interface IGhost {

  PVector getPos();

  Direction getDirection();

  void move(Direction dir);

}
