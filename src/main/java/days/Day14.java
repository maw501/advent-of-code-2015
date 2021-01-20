package main.java.days;

import java.util.ArrayList;
import java.util.Collections;

public class Day14 {
  private static final int day = 14;
  ArrayList<Reindeer> reindeers = new ArrayList<>();

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day14 day14 = new Day14();
    day14.getReindeers(data);
    System.out.println("Day " + day + " star 1: " + day14.starOne());
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
    int raceLength = 2503;
    for (Reindeer reindeer : reindeers) {
      int completeBursts = raceLength / reindeer.getBurstLength();
      int remainder = raceLength % reindeer.getBurstLength();
      int extraFlyingTime = Math.min(remainder, reindeer.getStamina());
      int totalFlyingTime = completeBursts * reindeer.getStamina() + extraFlyingTime;
      int distance = totalFlyingTime * reindeer.getSpeed();
      distances.add(distance);
      System.out.println(reindeer.getName() + " travelled: " + distance);
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

  public String getName() {
    return name;
  }

  public int getBurstLength() {
    return burstLength;
  }

  public int getSpeed() {
    return speed;
  }

  public int getStamina() {
    return stamina;
  }

  public int getRestingDuration() {
    return restingDuration;
  }
}
