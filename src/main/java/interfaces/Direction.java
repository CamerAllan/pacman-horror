package interfaces;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Direction {
  NORTH, SOUTH, EAST, WEST;

  private static final List<Direction> DIRECTIONS =
      Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = DIRECTIONS.size();
  private static final Random RANDOM = new Random();

  public static Direction randomDirection()  {
    return DIRECTIONS.get(RANDOM.nextInt(SIZE));
  }
}
