package main.java.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day9<out> {
  private static final int day = 9;
  HashMap<String, Integer> distances = new HashMap<>();

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day9 day9 = new Day9();
    day9.starOne(data);
    //System.out.println("Day " + day + " star 1: " + day9.starOne(data));
  }

  private int starOne(ArrayList<String> data) {
    buildDistanceMap(data);
    buildPermutations();
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

  private void buildPermutations() {
    ArrayList<String> arr = new ArrayList<>();
    arr.add("a");
    arr.add("b");
    arr.add("c");
    int n = 3;
    ArrayList<String[]> outArray = new ArrayList<>();
    String [] e1 = {arr.get(0), arr.get(1)};
    String [] e2 = {arr.get(1), arr.get(0)};
    outArray.add(e1);
    outArray.add(e2);
    for (String[] s : outArray){
      System.out.println(Arrays.toString(s));
    }
    int currIndex = 2;
  }
}
