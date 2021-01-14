package main.java.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day13 {
  private static final int day = 13;
  HashMap<String, Integer> seatingHappiness = new HashMap<>();
  Set<String> allGuests = new HashSet<>();

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day13 day13 = new Day13();
    day13.buildDataMaps(data);
    day13.buildSeatingArrangements();
    System.out.println("Day " + day + " star 1: " + day13.starOne());
    // System.out.println("Day " + day + " star 2: " + day9.starTwo());
  }

  private int starOne() {
    return calculateHappiness();
  }

  private int calculateHappiness() {
    ArrayList<ArrayList<String>> seatingPlan = buildSeatingArrangements();
    int numGuests = allGuests.size();
    int maxHappiness = 0;
    for (ArrayList<String> arrangement : seatingPlan) {
      int planHappiness = 0;
      for (int i = 0; i < arrangement.size(); i++) {
        String currGuest = arrangement.get(i);
        String neighbour1 = arrangement.get((i + numGuests - 1) % numGuests);
        String neighbour2 = arrangement.get((i + numGuests + 1) % numGuests);
        String k1 = currGuest + "_" + neighbour1;
        String k2 = currGuest + "_" + neighbour2;
        int currGuestHappiness = seatingHappiness.get(k1) + seatingHappiness.get(k2);
        planHappiness += currGuestHappiness;
      }
      if (planHappiness > maxHappiness) {
        maxHappiness = planHappiness;
      }
    }
    return maxHappiness;
  }

  private void buildDataMaps(ArrayList<String> data) {
    for (String line : data) {
      String[] result = line.split(" ");
      allGuests.add(result[0]);
      String key = result[0] + "_" + result[10].replace(".", "");
      if (result[2].equals("gain")) {
        seatingHappiness.put(key, Integer.parseInt(result[3]));
      } else {
        seatingHappiness.put(key, -Integer.parseInt(result[3]));
      }
    }
  }

  private ArrayList<ArrayList<String>> buildSeatingArrangements() {
    ArrayList<String> guests = new ArrayList<>(allGuests);
    ArrayList<ArrayList<String>> currArray = new ArrayList<>();
    ArrayList<String> e1 = new ArrayList<>();
    e1.add(guests.get(0));
    e1.add(guests.get(1));
    e1.add(guests.get(2));
    currArray.add(e1);

    int currIndex = 3;
    int currLen = 0;
    long finalLength = factorial(guests.size() - 1) / 2;
    ArrayList<ArrayList<String>> finalArray = new ArrayList<>();

    while (currLen < finalLength) {
      finalArray = new ArrayList<>();
      String currString = guests.get(currIndex);
      for (ArrayList<String> element : currArray) {
        for (int i = 0; i < currIndex; i++) {
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
