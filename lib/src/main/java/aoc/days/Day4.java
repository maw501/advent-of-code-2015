package aoc.days;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Day4 {
  private static final int day = 4;
  private static final String data = "iwrupvqb";

  public static void main(String[] args) {
    try {
      System.out.println("Day " + day + " star 1: " + starOne());
      System.out.println("Day " + day + " star 2: " + starTwo());
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Error");
    }
  }

  private static int starOne() throws NoSuchAlgorithmException {
    return findMD5IntegerSuffix(false);
  }

  private static int starTwo() throws NoSuchAlgorithmException {
    return findMD5IntegerSuffix(true);
  }

  private static int findMD5IntegerSuffix(boolean starTwo) throws NoSuchAlgorithmException {
    int i = 0;
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] array;
    while (true) {
      array = md.digest(data.concat(Integer.toString(i++)).getBytes());
      if (array[0] == 0 && array[1] == 0 && (array[2] >> 4 & 0xf) == 0) {
        if (starTwo) {
          if (array[2] == 0) break;
        } else {
          break;
        }
      }
    }
    return i - 1;
  }
}
