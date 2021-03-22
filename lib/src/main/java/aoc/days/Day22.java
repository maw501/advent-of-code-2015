package aoc.days;

import aoc.utils.Game;

import java.util.HashSet;
import java.util.LinkedList;

public class Day22 {
  private static final int day = 22;
  HashSet<String> spells = new HashSet<>();

  public static void main(String[] args) {
    Day22 day22 = new Day22();
    System.out.println("Day " + day + " star One: " + day22.play(false));
    System.out.println("Day " + day + " star One: " + day22.play(true));
  }

  private void buildSpells() {
    spells.add("missile");
    spells.add("drain");
    spells.add("shield");
    spells.add("poison");
    spells.add("recharge");
  }

  public int play(boolean hard) {
    buildSpells();
    LinkedList<Game> frontier = new LinkedList<>();
    for (String s : spells) {
      frontier.offer(new Game(hard, s));
    }
    return bfs(frontier, hard);
  }

  public int bfs(LinkedList<Game> frontier, boolean hard) {
    int bestCost = 100000;
    while (!frontier.isEmpty()) {
      Game game = frontier.poll();
      boolean gameOver = game.takeTurn();
      if (gameOver) {
        if (game.isPlayerWon()) {
          if (game.getTotalCost() < bestCost) {
            bestCost = game.getTotalCost();
          }
        }
      } else {
        if (game.getTotalCost() < bestCost) {
          for (String s : spells) {
            frontier.offer(new Game(game, s, hard));
          }
        }
      }
    }
    return bestCost;
  }
}
