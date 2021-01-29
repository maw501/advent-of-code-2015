package aoc.utils;

public class Factorial {
  public static long factorial(long n) {
    long result = 1;
    for (long i = n; i > 0; i--) {
      result *= i;
    }
    return result;
  }
}
