package aoc.days;

import java.util.ArrayList;

public class Day20 {
  private static final int day = 20;
  private static final int starOneTarget = 36000000;

  public static void main(String[] args) {
    Day20 day20 = new Day20();
    System.out.println("Day " + day + " star 1: " + day20.starOne());
  }

  private int starOne() {
    int numberOfPresents = 0;
    int houseNumber = 1;
    while (numberOfPresents < starOneTarget) {
      ArrayList<Integer> visitors = calculateFactors(houseNumber);
      numberOfPresents = calculateHousePresents(visitors);
      houseNumber++;
    }
    return houseNumber - 1;
  }

  private int calculateHousePresents(ArrayList<Integer> visitors) {
    int numberOfPresents = 0;
    for (int i : visitors) numberOfPresents += i;
    return numberOfPresents * 10;
  }

  public ArrayList<Integer> calculateFactors(int num) {
    ArrayList<Integer> factors = new ArrayList<Integer>();
    // Skip two if the number is odd
    int incrementer = num % 2 == 0 ? 1 : 2;
    for (int i = 1; i <= Math.sqrt(num); i += incrementer) {
      // If there is no remainder, then the number is a factor.
      if (num % i == 0) {
        factors.add(i);
        // Skip duplicates
        if (i != num / i) {
          factors.add(num / i);
        }
      }
    }
    return factors;
  }
}
