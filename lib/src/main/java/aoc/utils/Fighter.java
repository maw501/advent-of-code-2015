package aoc.utils;

public class Fighter {
  private final String name;
  private int hitPoints;
  private final int damage;
  private final int armor;

  public Fighter(String name, int hitPoints, int damage, int armor) {
    this.name = name;
    this.hitPoints = hitPoints;
    this.damage = damage;
    this.armor = armor;
  }

  public void takeDamage(int damageTaken) {
    int damageToDeduct = Math.max(1, damageTaken - armor);
    setHitPoints(damageToDeduct);
  }

  private void setHitPoints(int damageToDeduct) {
    hitPoints -= damageToDeduct;
    System.out.println(
        name + " took hit of: " + damageToDeduct + ", hitPoints remaining: " + hitPoints);
  }

  public boolean zeroHitPoints() {
    return hitPoints <= 0;
  }

  public int getDamage() {
    return damage;
  }
}
