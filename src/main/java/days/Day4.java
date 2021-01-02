package main.java.days;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Day4 {
  private static final int day = 4;
  private static final String data = "iwrupvqb";

  public static void main(String[] args) {
    System.out.println("Day " + day + " star 1: " + starOne());
    System.out.println("Day " + day + " star 2: " + starTwo());
  }

  private static int starOne() {
    return findMD5IntegerSuffix(5);
  }

  private static int starTwo() {
    return findMD5IntegerSuffix(6);
  }

  private static int findMD5IntegerSuffix(int length) {
    int i = 0;
    while (true) {
      String hash = getMD5Hash(Day4.data + i);
      String sub = hash.substring(0, length);
      if (subStringToInt(sub, length) == 0) {
        return i;
      }
      i++;
    }
  }

  private static int subStringToInt(String inputString, int stringLength) {
    String sub = inputString.substring(0, stringLength);
    try {
      return Integer.parseInt(sub);
    } catch (NumberFormatException e) {
      return 99;
    }
  }

  private static String getMD5Hash(String hashInput) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] messageDigest = md.digest(hashInput.getBytes());
      BigInteger no = new BigInteger(1, messageDigest);

      StringBuilder hashtext = new StringBuilder(no.toString(16));
      while (hashtext.length() < 32) {
        hashtext.insert(0, "0");
      }
      return hashtext.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
