package main.java.days;

import java.util.ArrayList;
import java.util.HashMap;

public class Day9 {
  private static final int day = 9;
  HashMap<String, Integer> distances = new HashMap<>();

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day9 day9 = new Day9();
    day9.starOne(data);
    System.out.println("Day " + day + " star 1: " + day9.starOne(data));
  }

  private int starOne(ArrayList<String> data) {
    buildDistanceMap(data);
    return 1;
  }

  private void buildDistanceMap(ArrayList<String> data) {
    for (String s : data) {
      String[] result = s.split(" ");
      String key1 = result[0] + "_" + result[2];
      String key2 = result[2] + "_" + result[0];
      distances.put(key1, Integer.parseInt(result[4]));
      distances.put(key2, Integer.parseInt(result[4]));
    }
  }
}
