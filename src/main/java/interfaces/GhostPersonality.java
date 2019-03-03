package interfaces;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum GhostPersonality {
  RED, PINK, BLUE, ORANGE;

  private static final List<GhostPersonality> PERSONALITIES =
      Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = PERSONALITIES.size();
  private static final Random RANDOM = new Random();

  public static GhostPersonality randomPersonality()  {
    return PERSONALITIES.get(RANDOM.nextInt(SIZE));
  }
}
