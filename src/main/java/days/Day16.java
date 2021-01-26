package main.java.days;

import java.util.*;

public class Day16 {
  private static final int day = 16;
  private final HashMap<Integer, HashMap<String, Integer>> sueMap = new HashMap<>();
  private final HashMap<String, Integer> realSue = new HashMap<>();

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day16 day16 = new Day16();
    day16.buildMap(data);
    day16.buildRealSueMap();
    System.out.println("Day " + day + " star 1: " + day16.starOne());
    System.out.println("Day " + day + " star 2: " + day16.starTwo());
  }

  private int starOne() {
    for (int i = 1; i <= 500; i++) {
      if (!isThisSueEliminatedExact(sueMap.get(i))) return i;
    }
    return -1;
  }

  private int starTwo() {
    HashSet<String> requireExactValue =
        new HashSet<>(
            Arrays.asList("children", "samoyeds", "akitas", "vizslas", "cars", "perfumes"));
    for (int i = 1; i <= 500; i++) {
      HashMap<String, Integer> singleSue = sueMap.get(i);
      boolean eliminated = isThisSueEliminatedRange(singleSue, requireExactValue);
      if (!eliminated) return i;
    }
    return -1;
  }

  private boolean isThisSueEliminatedExact(HashMap<String, Integer> singleSue) {
    for (String key : singleSue.keySet()) {
      if (!singleSue.get(key).equals(realSue.get(key))) return true;
    }
    return false;
  }

  private boolean isThisSueEliminatedRange(
      HashMap<String, Integer> singleSue, HashSet<String> requireExactValue) {
    for (String key : singleSue.keySet()) {
      int singleSueValue = singleSue.get(key);
      int realSueValue = realSue.get(key);
      if ((key.equals("cats") || key.equals("trees")) && singleSueValue <= realSueValue)
        return true;
      if ((key.equals("pomeranians") || key.equals("goldfish")) && singleSueValue >= realSueValue)
        return true;
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
      for (int j = 2; j < 8; j += 2) {
        String item = result[j].split(":")[0];
        int value = Integer.parseInt(result[j + 1].split(",")[0]);
        singleSue.put(item, value);
      }
      sueMap.put(i, singleSue);
      i++;
    }
  }
}
