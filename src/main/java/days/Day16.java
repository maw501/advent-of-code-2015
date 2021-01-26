package main.java.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day16 {
  private static final int day = 16;
  private final int numSues = 500;
  private HashMap<Integer, HashMap<String, Integer>> sueMap = new HashMap<>();
  private HashMap<String, Integer> realSue = new HashMap<>();

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day16 day16 = new Day16();
    day16.buildMap(data);
    day16.buildRealSueMap();
    System.out.println("Day " + day + " star 1: " + day16.starOne());
    System.out.println("Day " + day + " star 2: " + day16.starTwo());
  }

  private int starOne() {
    int whoIsTheRealSue = -1;
    for (int i = 1; i <= numSues; i++) {
      HashMap<String, Integer> singleSue = sueMap.get(i);
      boolean eliminated = isThisSueEliminatedExact(singleSue);
      if (!eliminated) {
        whoIsTheRealSue = i;
      }
    }
    return whoIsTheRealSue;
  }

  private int starTwo() {
    HashSet<String> requireExactValue = new HashSet<>();
    requireExactValue.add("children");
    requireExactValue.add("samoyeds");
    requireExactValue.add("akitas");
    requireExactValue.add("vizslas");
    requireExactValue.add("cars");
    requireExactValue.add("perfumes");
    int whoIsTheRealSue = -1;
    for (int i = 1; i <= numSues; i++) {
      HashMap<String, Integer> singleSue = sueMap.get(i);
      boolean eliminated = isThisSueEliminatedRange(singleSue, requireExactValue);
      if (!eliminated) {
        whoIsTheRealSue = i;
      }
    }
    return whoIsTheRealSue;
  }

  private boolean isThisSueEliminatedExact(HashMap<String, Integer> singleSue) {
    for (String key : singleSue.keySet()) {
      int singleSueValue = singleSue.get(key);
      int realSueValue = realSue.get(key);
      if (singleSueValue != realSueValue) return true;
    }
    return false;
  }

  private boolean isThisSueEliminatedRange(
      HashMap<String, Integer> singleSue, HashSet<String> requireExactValue) {
    for (String key : singleSue.keySet()) {
      int singleSueValue = singleSue.get(key);
      int realSueValue = realSue.get(key);
      if (key.equals("cats") && singleSueValue <= realSueValue) return true;
      if (key.equals("trees") && singleSueValue <= realSueValue) return true;
      if (key.equals("pomeranians") && singleSueValue >= realSueValue) return true;
      if (key.equals("goldfish") && singleSueValue >= realSueValue) return true;
      if (requireExactValue.contains(key) && singleSueValue != realSueValue) return true;
    }
    return false;
  }

  private void buildRealSueMap() {
    realSue.put("children", 3);
    realSue.put("cats", 7);
    realSue.put("samoyeds", 2);
    realSue.put("pomeranians", 3);
    realSue.put("akitas", 0);
    realSue.put("vizslas", 0);
    realSue.put("goldfish", 5);
    realSue.put("trees", 3);
    realSue.put("cars", 3);
    realSue.put("perfumes", 1);
  }

  private void buildMap(ArrayList<String> data) {
    int i = 1;
    for (String line : data) {
      HashMap<String, Integer> singleSue = new HashMap<>();
      String[] result = line.split(" ");
      String item1 = removeTrailingChar(result[2]);
      String item2 = removeTrailingChar(result[4]);
      String item3 = removeTrailingChar(result[6]);
      int value1 = Integer.parseInt(removeTrailingChar(result[3]));
      int value2 = Integer.parseInt(removeTrailingChar(result[5]));
      int value3 = Integer.parseInt(result[7]);
      singleSue.put(item1, value1);
      singleSue.put(item2, value2);
      singleSue.put(item3, value3);
      sueMap.put(i, singleSue);
      i++;
    }
  }

  private String removeTrailingChar(String s) {
    return s.substring(0, s.length() - 1);
  }
}
