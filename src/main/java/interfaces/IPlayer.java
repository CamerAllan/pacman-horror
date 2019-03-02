package interfaces;

import processing.core.PVector;

public interface IPlayer {

  PVector getPos();

  Direction getDirection();

  void move(Direction dir);

}
