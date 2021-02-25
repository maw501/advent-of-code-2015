package aoc.days;

import java.util.ArrayList;
import java.util.HashMap;

public class Day20 {
  private static final int day = 20;
  private static final int target = 36000000;
  private final HashMap<Integer, Integer> elfVisitCount = new HashMap<>();

  public static void main(String[] args) {
    Day20 day20 = new Day20();
    System.out.println("Day " + day + " star 1: " + day20.starOne());
    System.out.println("Day " + day + " star 1: " + day20.starTwo());
  }

  private int starOne() {
    int numberOfPresents = 0;
    int houseNumber = 1;
    while (numberOfPresents < target) {
      ArrayList<Integer> visitors = calculateFactors(houseNumber);
      numberOfPresents = calculateHousePresents(visitors, 10);
      houseNumber++;
    }
    return houseNumber - 1;
  }

  private int starTwo() {
    int numberOfPresents = 0;
    int houseNumber = 1;
    while (numberOfPresents < target) {
      ArrayList<Integer> visitors = calculateFactors(houseNumber);
      updateElfVisitCount(visitors);
      visitors = checkElfCapacity(visitors);
      if (visitors.size() > 0) {
        numberOfPresents = calculateHousePresents(visitors, 11);
      }
      houseNumber++;
    }
    return houseNumber - 1;
  }

  private ArrayList<Integer> checkElfCapacity(ArrayList<Integer> visitors) {
    ArrayList<Integer> modifiedVisitors = new ArrayList<>();
    for (int i : visitors) {
      if (elfVisitCount.get(i) < 50) modifiedVisitors.add(i);
    }
    return modifiedVisitors;
  }

  private void updateElfVisitCount(ArrayList<Integer> visitors) {
    for (int i : visitors) {
      if (elfVisitCount.containsKey(i)) {
        int currentVisits = elfVisitCount.get(i);
        elfVisitCount.put(i, currentVisits + 1);
      } else {
        elfVisitCount.put(i, 1);
      }
    }
  }

  private int calculateHousePresents(ArrayList<Integer> visitors, int mult) {
    int numberOfPresents = 0;
    for (int i : visitors) numberOfPresents += i;
    return numberOfPresents * mult;
  }

  public ArrayList<Integer> calculateFactors(int num) {
    ArrayList<Integer> factors = new ArrayList<Integer>();
    // Skip two if the number is odd
    var incrementer = num % 2 == 0 ? 1 : 2;
    for (int i = 1; i <= Math.sqrt(num); i += incrementer) {
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
