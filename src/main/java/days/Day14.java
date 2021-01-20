package main.java.days;

import java.util.ArrayList;
import java.util.Collections;

public class Day14 {
  private static final int day = 14;
  private static final int raceLength = 2503;
  ArrayList<Reindeer> reindeers = new ArrayList<>();

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day14 day14 = new Day14();
    day14.getReindeers(data);
    System.out.println("Day " + day + " star 1: " + day14.starOne());
    System.out.println("Day " + day + " star 2: " + day14.starTwo());
  }

  private void getReindeers(ArrayList<String> data) {
    for (String line : data) {
      String[] result = line.split(" ");
      Reindeer reindeer = new Reindeer(result[0], result[3], result[6], result[13]);
      reindeers.add(reindeer);
    }
  }

  private int starOne() {
    ArrayList<Integer> distances = new ArrayList<>();
    for (Reindeer reindeer : reindeers) {
      distances.add(reindeer.getDistanceAfterTime(raceLength));
    }
    return Collections.max(distances);
  }

  private int starTwo() {
    ArrayList<Integer> distances = new ArrayList<>();
    for (Reindeer reindeer : reindeers) {
      distances.add(reindeer.getDistanceAfterTime(raceLength));
    }
    return Collections.max(distances);
  }
}

class Reindeer {
  String name;
  int speed;
  int stamina;
  int restingDuration;
  int burstLength;

  public Reindeer(String name, String speed, String stamina, String restingDuration) {
    this(
        name,
        Integer.parseInt(speed),
        Integer.parseInt(stamina),
        Integer.parseInt(restingDuration));
  }

  public Reindeer(String name, int speed, int stamina, int restingDuration) {
    this.name = name;
    this.speed = speed;
    this.stamina = stamina;
    this.restingDuration = restingDuration;
    this.burstLength = stamina + restingDuration;
  }

  public int getDistanceAfterTime(int time) {
    int completeBursts = time / burstLength;
    int remainder = time % burstLength;
    int extraFlyingTime = Math.min(remainder, stamina);
    int totalFlyingTime = completeBursts * stamina + extraFlyingTime;
    return totalFlyingTime * speed;
  }
}
