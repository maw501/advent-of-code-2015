package main.java.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Integer.parseInt;

public class Day14 {
  private static final int day = 14;

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day14 day14 = new Day14();
    System.out.println("Day " + day + " star 1: " + day14.starOne(data));
  }

  private int starOne(ArrayList<String> data) {
    ArrayList<Integer> distances = new ArrayList<>();
    int raceLength = 2503;
    for (String line : data) {
      String[] result = line.split(" ");
      String reindeer = result[0];
      int speed = parseInt(result[3]);
      int stamina = parseInt(result[6]);
      int restingDuration = parseInt(result[13]);
      int burstLength = stamina + restingDuration;
      int completeBursts = raceLength / burstLength;
      int remainder = raceLength % burstLength;
      int extraFlyingTime = Math.min(remainder, stamina);
      int totalFlyingTime = completeBursts * stamina + extraFlyingTime;
      int distance = totalFlyingTime * speed;
      distances.add(distance);
      System.out.println(reindeer + " travelled: " + distance);
    }
    return Collections.max(distances);
  }
}
