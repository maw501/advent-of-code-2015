package aoc.days;

import aoc.utils.Fighter;

public class Day21 {
  private static final int day = 21;

  public static void main(String[] args) {
    Day21 day21 = new Day21();
    System.out.println("Day " + day + " star 1: " + day21.starOne());
    System.out.println("Day " + day + " star 2: " + day21.starTwo());
  }

  private int starOne() {
    Fighter player = new Fighter("Player", 8, 5, 5);
    Fighter boss = new Fighter("Boss", 12, 7, 2);
    fight(player, boss);
    return 1;
  }

  private void fight(Fighter player, Fighter boss) {
    boolean gameOver = player.zeroHitPoints() || boss.zeroHitPoints();
    boolean playerWins = false;
    while (!gameOver) {
      boss.takeDamage(player.getDamage());
      player.takeDamage(boss.getDamage());
      gameOver = boss.zeroHitPoints() || player.zeroHitPoints();
      if (boss.zeroHitPoints()) playerWins = true;
    }
    System.out.println("Player won: " + playerWins);
  }

  private int starTwo() {
    return 1;
  }
}
