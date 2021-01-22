package main.java.days;

public class Day15 {
  public static void main(String[] args) {
    long bestScore = 0;
    for (int i = 1; i <= 100; i++) {
      for (int j = 1; j <= 100 - i; j++) {
        for (int k = 1; k <= 100 - i - j; k++) {
          int l = 100 - i - j - k;
          int capacity = Math.max(0, i * 3 + j * -3 + k * -1);
          int durability = Math.max(0, 3 * j);
          int flavour = Math.max(0, 4 * k - 2 * l);
          int texture = Math.max(0, -3 * i + 2 * l);
          int calories = i * 2 + j * 9 + k + l * 8;
          long score = (long) capacity * durability * flavour * texture;
          if (score > bestScore && calories == 500) {
            bestScore = score;
          }
        }
      }
    }
    System.out.println(bestScore);
  }
}
