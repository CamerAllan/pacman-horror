package interfaces;

import java.util.List;

public interface Game {

  /*
  Called every frame, does all the stuff
   */
  void nextStep();

  Player getPlayer();

  List<Ghost> getGhosts();

  Map getMap();

  /*
  Return ghost if player has collided with a ghost, otherwise null
   */
  Ghost checkGhostCollision();

  /*
  Moves player in their current direction unless obstacle present
   */
  void movePlayer();

  /*
  Moves ghosts in their current direction or change dir if obstacle present
   */
  void moveGhosts();



}
