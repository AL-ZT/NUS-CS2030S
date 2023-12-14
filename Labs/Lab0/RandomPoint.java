import java.util.Random;

// TODO
class RandomPoint extends Point {
  private double ranX;
  private double ranY;
  private static Random rng = new Random(1);

  public RandomPoint(int minX, int maxX, int minY, int maxY) {
    super();
    setX(generateDouble(minX, maxX));
    setY(generateDouble(minY, maxY));
  }

  public static void setSeed(int newSeed) {
    RandomPoint.rng = new Random(newSeed);
  }

  public double generateDouble(int min, int max) {
    return min + rng.nextDouble() * max;
  }	
}
