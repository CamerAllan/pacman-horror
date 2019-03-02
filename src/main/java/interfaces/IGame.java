package interfaces;

import java.util.List;

public interface IGame extends Drawable {

  /*
  Called every frame, does all the stuff
   */
  void nextStep();

  IPlayer getPlayer();

  List<IGhost> getGhosts();

  IMap getMap();

  /*
  Return ghost if player has collided with a ghost, otherwise null
   */
  IGhost checkGhostCollision();

  /*
  Moves player in their current direction unless obstacle present
   */
  void movePlayer();

  /*
  Moves ghosts in their current direction or change dir if obstacle present
   */
  void moveGhosts();



}
