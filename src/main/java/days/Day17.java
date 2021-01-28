package main.java.days;

import org.apache.commons.math3.util.Combinations;

import java.util.ArrayList;
import java.util.HashMap;

public class Day17 {
  private static final int day = 17;
  private final ArrayList<Integer> values = new ArrayList<>();
  private final HashMap<Integer, Integer> idToValue = new HashMap<>();
  private HashMap<Integer, Integer> successesPerCombSize = new HashMap<>();
  private final int target = 150;

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day17 day17 = new Day17();
    day17.buildMap(data);
    System.out.println("Day " + day + " star 1: " + day17.starOne());
    System.out.println("Day " + day + " star 2: " + day17.starTwo());
  }

  private int starOne() {
    int successes = 0;
    for (int k = 1; k <= values.size(); k++) {
      int combSuccess = successesForCombOfSizeK(k);
      successes += combSuccess;
      successesPerCombSize.put(k, combSuccess);
    }
    return successes;
  }

  private int starTwo() {
    for (int k = 1; k <= values.size(); k++) {
      if (successesPerCombSize.get(k) != 0) {
        return successesPerCombSize.get(k);
      }
    }
    return -1;
  }

  private int successesForCombOfSizeK(int k) {
    int successes = 0;
    Combinations comb = new Combinations(values.size(), k);
    for (int[] ints : comb) {
      int combValue = 0;
      for (int id : ints) {
        combValue = combValue + idToValue.get(id);
      }
      if (combValue == target) {
        successes++;
      }
    }
    return successes;
  }

  private void buildMap(ArrayList<String> data) {
    int i = 0;
    for (String line : data) {
      values.add(Integer.parseInt(line));
      idToValue.put(i, Integer.parseInt(line));
      i++;
    }
  }
}
