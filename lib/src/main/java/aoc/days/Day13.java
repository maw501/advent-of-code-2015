package aoc.days;

import aoc.utils.Permutations;
import aoc.utils.ReadTextFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day13 {
  private static final int day = 13;
  HashMap<String, Integer> seatingHappiness = new HashMap<>();
  Set<String> allGuests = new HashSet<>();

  public static void main(String[] args) {
    ArrayList<String> data = ReadTextFile.readFile(day);
    Day13 day13 = new Day13();
    System.out.println("Day " + day + " star 1: " + day13.starOne(data));
    Day13 d13s2 = new Day13();
    System.out.println("Day " + day + " star 2: " + d13s2.starTwo(data));
  }

  private int starOne(ArrayList<String> data) {
    buildDataMaps(data);
    return calculateHappiness();
  }

  private int starTwo(ArrayList<String> data) {
    buildDataMaps(data);
    updateDataMaps("Me", 0);
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
        planHappiness += guestHappiness(currGuest, neighbour1, neighbour2);
      }
      if (planHappiness > maxHappiness) {
        maxHappiness = planHappiness;
      }
    }
    return maxHappiness;
  }

  private int guestHappiness(String guest, String n1, String n2) {
    return seatingHappiness.get(buildKey(guest, n1)) + seatingHappiness.get(buildKey(guest, n2));
  }

  private void updateDataMaps(String newGuest, int value) {
    allGuests.add(newGuest);
    for (String guest : allGuests) {
      seatingHappiness.put(buildKey(guest, newGuest), value);
      seatingHappiness.put(buildKey(newGuest, guest), value);
    }
  }

  private void buildDataMaps(ArrayList<String> data) {
    // Builds guest list and guest to guest happiness rating
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
    return Permutations.buildPermutations(guests, currArray, true);
  }

  private String buildKey(String s1, String s2) {
    return s1 + "_" + s2;
  }
}
