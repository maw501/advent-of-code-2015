package main.java.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
    ArrayList<Integer> distances = Reindeer.getAllReindeerDistancesAfterTime(raceLength, reindeers);
    return Collections.max(distances);
  }

  private int starTwo() {
    ArrayList<Integer> distances;
    HashMap<String, Integer> scores = initializeScoreMap(reindeers);
    for (int t = 1; t <= raceLength; t++) {
      distances = Reindeer.getAllReindeerDistancesAfterTime(t, reindeers);
      updateScores(scores, distances);
    }
    return Collections.max(scores.values());
  }

  private void updateScores(HashMap<String, Integer> scores, ArrayList<Integer> distances) {
    int currMaxDistance = Collections.max(distances);
    for (int i = 0; i < reindeers.size(); i++) {
      if (distances.get(i) == currMaxDistance) {
        String reindeerName = reindeers.get(i).getName();
        Integer newScore = scores.get(reindeerName) + 1;
        scores.put(reindeerName, newScore);
      }
    }
  }

  private HashMap<String, Integer> initializeScoreMap(ArrayList<Reindeer> reindeers) {
    var emptyScores = new HashMap<String, Integer>();
    for (Reindeer r : reindeers) {
      emptyScores.put(r.getName(), 0);
    }
    return emptyScores;
  }
}

class Reindeer {
  private final String name;
  private final int speed;
  private final int stamina;
  private final int burstLength;

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
    this.burstLength = stamina + restingDuration;
  }

  public String getName() {
    return name;
  }

  public int getDistanceAfterTime(int time) {
    int completeBursts = time / burstLength;
    int remainder = time % burstLength;
    int extraFlyingTime = Math.min(remainder, stamina);
    int totalFlyingTime = completeBursts * stamina + extraFlyingTime;
    return totalFlyingTime * speed;
  }

  public static ArrayList<Integer> getAllReindeerDistancesAfterTime(
      int time, ArrayList<Reindeer> reindeers) {
    ArrayList<Integer> distances = new ArrayList<>();
    for (Reindeer reindeer : reindeers) distances.add(reindeer.getDistanceAfterTime(time));
    return distances;
  }
}
