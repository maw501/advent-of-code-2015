package aoc.utils;

import java.util.HashMap;
import java.util.HashSet;

public class Game {
  int mana = 500;
  int hp = 50;
  int armor = 0;
  int bossHP = 51;
  int bossDamage = 9;
  int totalCost = 0;
  boolean playerWon = false;
  boolean hard;
  boolean gameOver = false;
  String nextSpell;
  HashMap<String, Integer> activeSpells = new HashMap<>();
  HashSet<String> availableSpells = new HashSet<>();
  HashMap<String, Integer> costs = new HashMap<>();
  HashMap<String, Integer> durations = new HashMap<>();

  public Game(boolean hard, String nextSpell) {
    this.hard = hard;
    this.nextSpell = nextSpell;
    buildAvailableSpells();
    buildCosts();
    buildDurations();
  }

  public Game(Game initGame, String nextSpell, boolean hard) {
    this(hard, nextSpell);
    this.mana = initGame.mana;
    this.hp = initGame.hp;
    this.armor = initGame.armor;
    this.bossHP = initGame.bossHP;
    this.bossDamage = initGame.bossDamage;
    this.totalCost = initGame.totalCost;
    this.availableSpells = new HashSet<>(initGame.availableSpells);
    this.activeSpells.putAll(initGame.activeSpells);
  }

  public boolean takeTurn() {
    startOfTurn();
    checkTerminalState();
    if (gameOver) return true;
    playerTurn();
    checkTerminalState();
    if (gameOver) return true;
    updateGameStateFromCurrentSpell();
    checkTerminalState();
    if (gameOver) return true;
    bossTurn();
    checkTerminalState();
    return gameOver;
  }

  private void startOfTurn() {
    buildAvailableSpells();
    if (hard) hp--;
    if (activeSpells.containsKey("shield")) armor = 7;
    if (activeSpells.containsKey("poison")) bossHP -= 3;
    if (activeSpells.containsKey("recharge")) mana += 101;
    updateActiveSpells();
    if (!activeSpells.containsKey("shield")) armor = 0;
    availableSpells.removeAll(activeSpells.keySet());
  }

  private void playerTurn() {
    if (durations.get(nextSpell) > 1) {
      activeSpells.put(nextSpell, durations.get(nextSpell));
    }
    mana -= costs.get(nextSpell);
    totalCost += costs.get(nextSpell);
  }

  private void updateGameStateFromCurrentSpell() {
    if (nextSpell.equals("shield")) armor = 7;
    if (nextSpell.equals("drain")) hp += 2;
    if (nextSpell.equals("drain")) bossHP -= 2;
    if (nextSpell.equals("missile")) bossHP -= 4;
  }

  private void bossTurn() {
    hp -= Math.max(bossDamage - armor, 1);
    if (activeSpells.containsKey("poison")) bossHP -= 3;
    if (activeSpells.containsKey("recharge")) mana += 101;
    updateActiveSpells();
    if (!activeSpells.containsKey("shield")) armor = 0;
  }

  private void updateActiveSpells() {
    HashMap<String, Integer> tmp = new HashMap<>();
    for (String s : activeSpells.keySet()) {
      tmp.put(s, activeSpells.get(s) - 1);
      if (tmp.get(s) == 0) tmp.remove(s);
    }
    activeSpells = tmp;
  }

  public boolean isPlayerWon() {
    return playerWon;
  }

  public int getTotalCost() {
    return totalCost;
  }

  public void checkTerminalState() {
    if (hp <= 0) gameOver = true;
    if (mana <= 0) gameOver = true;
    if (availableSpells.size() == 0) gameOver = true;
    if (bossHP <= 0) {
      playerWon = true;
      gameOver = true;
    }
  }

  private void buildAvailableSpells() {
    availableSpells.add("missile");
    availableSpells.add("drain");
    availableSpells.add("shield");
    availableSpells.add("poison");
    availableSpells.add("recharge");
  }

  private void buildDurations() {
    durations.put("missile", 1);
    durations.put("drain", 1);
    durations.put("shield", 6);
    durations.put("poison", 6);
    durations.put("recharge", 5);
  }

  private void buildCosts() {
    costs.put("missile", 53);
    costs.put("drain", 73);
    costs.put("shield", 113);
    costs.put("poison", 173);
    costs.put("recharge", 229);
  }
}
