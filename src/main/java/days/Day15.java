package main.java.days;

public class Day15 {
  private static final int day = 15;

  public static void main(String[] args) {
    Day15 day15 = new Day15();
    System.out.println("Day " + day + " star 1: " + day15.calculateBestScore(false));
    System.out.println("Day " + day + " star 1: " + day15.calculateBestScore(true));
  }

  private long calculateBestScore(boolean calorieRestriction) {
    long bestScore = 0;
    for (int i = 1; i <= 100; i++) {
      for (int j = 1; j <= 100 - i; j++) {
        for (int k = 1; k <= 100 - i - j; k++) {
          int l = 100 - i - j - k;
          long score = calculateScore(i, j, k, l);
          if (calorieRestriction) {
            int calories = calculateCalories(i, j, k, l);
            if (score > bestScore && calories == 500) {
              bestScore = score;
            }
          } else if (score > bestScore) {
            bestScore = score;
          }
        }
      }
    }
    return bestScore;
  }

  private int calculateCalories(
      int weightSugar, int weightSprinkles, int weightCandy, int weightChocolate) {
    return weightSugar * 2 + weightSprinkles * 9 + weightCandy + weightChocolate * 8;
  }

  private long calculateScore(
      int weightSugar, int weightSprinkles, int weightCandy, int weightChocolate) {
    int capacity = Math.max(0, weightSugar * 3 + weightSprinkles * -3 + weightCandy * -1);
    int durability = Math.max(0, 3 * weightSprinkles);
    int flavour = Math.max(0, 4 * weightCandy - 2 * weightChocolate);
    int texture = Math.max(0, -3 * weightSugar + 2 * weightChocolate);
    return (long) capacity * durability * flavour * texture;
  }
}
