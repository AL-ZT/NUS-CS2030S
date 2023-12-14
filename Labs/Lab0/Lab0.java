import java.util.Scanner;

/**
 * CS2030S Lab 0: Estimating Pi with Monte Carlo
 * Semester 2, 2021/22
 *
 * <p>This program takes in two command line arguments: the 
 * number of points and a random seed.  It runs the
 * Monte Carlo simulation with the given argument and print
 * out the estimated pi value.
 *
 * @author Lim Zheng Ting 
 */

class Lab0 {

  // TODO 
  public static double estimatePi(int numOfPoints, int seed) {
    Circle c = new Circle(new Point(0.5, 0.5), 0.5);
    RandomPoint.setSeed(seed);
    double n = 0;
    for (int i = 0; i < numOfPoints; i++) {
      if (c.contains(new RandomPoint(0, 1, 0, 1))) {
        n++;
      }
    }
    return ((4.00 * n) / numOfPoints);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int numOfPoints = sc.nextInt();
    int seed = sc.nextInt();

    double pi = estimatePi(numOfPoints, seed);

    System.out.println(pi);
    sc.close();
  }
}
