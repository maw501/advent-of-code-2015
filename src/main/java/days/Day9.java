package main.java.days;

import java.util.*;

public class Day9 {
  private static final int day = 9;
  HashMap<String, Integer> cityDistances = new HashMap<>();
  ArrayList<Integer> distances = new ArrayList<>();
  Set<String> allCities = new HashSet<>();

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day9 day9 = new Day9();
    day9.buildDistanceMap(data);
    day9.calculateDistances();
    System.out.println("Day " + day + " star 1: " + day9.starOne());
    System.out.println("Day " + day + " star 2: " + day9.starTwo());
  }

  private int starOne() {
    return Collections.min(distances);
  }

  private int starTwo() {
    return Collections.max(distances);
  }

  private void calculateDistances() {
    ArrayList<ArrayList<String>> perms = buildPermutations();
    for (ArrayList<String> journey : perms) {
      int distance = calculateJourneyDistance(journey);
      distances.add(distance);
    }
  }

  private int calculateJourneyDistance(ArrayList<String> journey) {
    String prevLocation = journey.get(0);
    int totalJourneyDistance = 0;
    for (int i = 1; i < journey.size(); i++) {
      String currLocation = journey.get(i);
      String key = prevLocation + "_" + currLocation;
      int distance = cityDistances.get(key);
      totalJourneyDistance += distance;
      prevLocation = currLocation;
    }
    return totalJourneyDistance;
  }

  private void buildDistanceMap(ArrayList<String> data) {
    for (String s : data) {
      String[] result = s.split(" ");
      String key1 = result[0] + "_" + result[2];
      String key2 = result[2] + "_" + result[0];
      cityDistances.put(key1, Integer.parseInt(result[4]));
      cityDistances.put(key2, Integer.parseInt(result[4]));
      allCities.add(result[0]);
      allCities.add(result[2]);
    }
  }

  private ArrayList<ArrayList<String>> buildPermutations() {
    ArrayList<String> cities = new ArrayList<>(allCities);
    ArrayList<ArrayList<String>> currArray = new ArrayList<>();
    ArrayList<String> e1 = new ArrayList<>();
    e1.add(cities.get(0));
    currArray.add(e1);

    int currIndex = 1;
    int currLen = 0;
    long finalLength = factorial(cities.size());
    ArrayList<ArrayList<String>> finalArray = new ArrayList<>();

    while (currLen < finalLength) {
      finalArray = new ArrayList<>();
      String currString = cities.get(currIndex);
      for (ArrayList<String> element : currArray) {
        for (int i = 0; i <= currIndex; i++) {
          ArrayList<String> newElement = new ArrayList<>(element);
          newElement.add(i, currString);
          finalArray.add(newElement);
        }
      }
      currLen = finalArray.size();
      currIndex++;
      currArray = new ArrayList<>(finalArray);
    }
    return finalArray;
  }

  private long factorial(long x) {
    long result = 1;
    for (long i = x; i > 0; i--) {
      result *= i;
    }
    return result;
  }
}
