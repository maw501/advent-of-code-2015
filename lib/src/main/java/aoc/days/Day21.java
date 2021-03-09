package aoc.days;

import aoc.utils.Fighter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Day21 {
  private static final int day = 21;
  private final ArrayList<ArrayList<Integer>> weapons = new ArrayList<>();
  private final ArrayList<ArrayList<Integer>> armors = new ArrayList<>();
  private final ArrayList<ArrayList<Integer>> rings = new ArrayList<>();

  public static void main(String[] args) {
    Day21 day21 = new Day21();
    day21.buildWeapons();
    day21.buildArmor();
    day21.buildRings();
    day21.allFights();
  }

  private void allFights() {
    int bestCost = 1000;
    int worstCost = 0;
    for (ArrayList<Integer> weapon : weapons) {
      for (ArrayList<Integer> armor : armors) {
        for (ArrayList<Integer> ring : rings) {
          int costVal = weapon.get(0) + armor.get(0) + ring.get(0);
          int damageVal = weapon.get(1) + armor.get(1) + ring.get(1);
          int armorVal = weapon.get(2) + armor.get(2) + ring.get(2);
          Fighter player = new Fighter(100, damageVal, armorVal);
          Fighter boss = new Fighter(100, 8, 2);
          boolean win = fight(player, boss);
          if (win && costVal < bestCost) {
            bestCost = costVal;
          }
          if (!win && costVal > worstCost) {
            worstCost = costVal;
          }
        }
      }
    }
    System.out.println("Day " + day + " star 1: " + bestCost);
    System.out.println("Day " + day + " star 2: " + worstCost);
  }

  private boolean fight(Fighter player, Fighter boss) {
    boolean gameOver = player.zeroHitPoints() || boss.zeroHitPoints();
    boolean playerWins = false;
    while (!gameOver) {
      boss.takeDamage(player.getDamage());
      player.takeDamage(boss.getDamage());
      gameOver = boss.zeroHitPoints() || player.zeroHitPoints();
      if (boss.zeroHitPoints()) playerWins = true;
    }
    return playerWins;
  }

  private ArrayList<ArrayList<Integer>> getRingIndices() {
    ArrayList<ArrayList<Integer>> out = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      out.add(new ArrayList<>(Collections.singletonList(i)));
      for (int j = i + 1; j < 6; j++) {
        out.add(new ArrayList<>(Arrays.asList(i, j)));
      }
    }
    return out;
  }

  private void buildWeapons() {
    weapons.add(new ArrayList<>(Arrays.asList(8, 4, 0)));
    weapons.add(new ArrayList<>(Arrays.asList(10, 5, 0)));
    weapons.add(new ArrayList<>(Arrays.asList(25, 6, 0)));
    weapons.add(new ArrayList<>(Arrays.asList(40, 7, 0)));
    weapons.add(new ArrayList<>(Arrays.asList(74, 8, 0)));
  }

  private void buildArmor() {
    armors.add(new ArrayList<>(Arrays.asList(13, 0, 1)));
    armors.add(new ArrayList<>(Arrays.asList(31, 0, 2)));
    armors.add(new ArrayList<>(Arrays.asList(53, 0, 3)));
    armors.add(new ArrayList<>(Arrays.asList(75, 0, 4)));
    armors.add(new ArrayList<>(Arrays.asList(102, 0, 5)));
    armors.add(new ArrayList<>(Arrays.asList(0, 0, 0)));
  }

  private void buildInitialRings() {
    rings.add(new ArrayList<>(Arrays.asList(25, 1, 0)));
    rings.add(new ArrayList<>(Arrays.asList(50, 2, 0)));
    rings.add(new ArrayList<>(Arrays.asList(100, 3, 0)));
    rings.add(new ArrayList<>(Arrays.asList(20, 0, 1)));
    rings.add(new ArrayList<>(Arrays.asList(40, 0, 2)));
    rings.add(new ArrayList<>(Arrays.asList(80, 0, 3)));
  }

  private void buildRings() {
    buildInitialRings();
    ArrayList<ArrayList<Integer>> ringIndices = getRingIndices();
    for (ArrayList<Integer> i : ringIndices) {
      if (i.size() == 2) rings.add(combineRings(i));
    }
    rings.add(new ArrayList<>(Arrays.asList(0, 0, 0)));
  }

  private ArrayList<Integer> combineRings(ArrayList<Integer> indices) {
    ArrayList<Integer> combinedRing = new ArrayList<>();
    int cost = 0;
    int damage = 0;
    int armor = 0;
    for (int i : indices) {
      cost += rings.get(i).get(0);
      damage += rings.get(i).get(1);
      armor += rings.get(i).get(2);
    }
    combinedRing.add(cost);
    combinedRing.add(damage);
    combinedRing.add(armor);
    return combinedRing;
  }
}
