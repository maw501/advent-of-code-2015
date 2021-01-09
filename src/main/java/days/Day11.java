package main.java.days;

import java.util.HashSet;
import java.util.Set;

public class Day11 {
  private static final int day = 11;
  private final String bannedChars = "iol";
  private final String regex = "(?<=(.))(?!\\1)";

  public static void main(String[] args) {
    String starOneInput = "vzbxkghb";
    Day11 day11 = new Day11();
    String starOneAnswer = day11.starOne(starOneInput);
    System.out.println("Day " + day + " star 1: " + starOneAnswer);
    System.out.println("Day " + day + " star 2: " + day11.starOne(starOneAnswer));
  }

  private String starOne(String start) {
    String incrementedString = start;
    boolean validPassword = false;
    while (!validPassword) {
      incrementedString = incrementString(start);
      boolean c1 = hasNoBannedChars(incrementedString);
      boolean c2 = hasDoubleChars(incrementedString);
      boolean c3 = hasIncreasingChars(incrementedString);
      validPassword = c1 && c2 && c3;
      start = incrementedString;
    }
    return incrementedString;
  }

  private String incrementString(String s) {
    StringBuilder startString = new StringBuilder(s);
    int stringLen = startString.length();
    for (int i = stringLen - 1; i >= 0; i--) {
      char currChar = startString.charAt(i);
      char incrementedChar = incrementSingleChar(currChar);
      startString.setCharAt(i, incrementedChar);
      if (currChar != 'z') {
        break;
      }
    }
    return String.valueOf(startString);
  }

  private char incrementSingleChar(char c) {
    if (c == 'z') return 'a';
    return (char) (c + 1);
  }

  private boolean hasIncreasingChars(String s) {
    int count = 1;
    int prevChar = s.charAt(0);
    for (int i = 1; i < s.length(); i++) {
      int currChar = s.charAt(i);
      if (currChar - prevChar == 1) {
        count++;
      } else {
        count = 1;
      }
      if (count == 3) return true;
      prevChar = currChar;
    }
    return false;
  }

  private boolean hasNoBannedChars(String s) {
    for (char bannedChar : bannedChars.toCharArray()) {
      if (s.indexOf(bannedChar) > 0) {
        return false;
      }
    }
    return true;
  }

  private boolean hasDoubleChars(String s) {
    Set<String> doubleChars = new HashSet<>();
    String[] matches = s.split(regex);
    for (String match : matches) {
      if (match.length() == 2) {
        doubleChars.add(match);
      }
    }
    return doubleChars.size() >= 2;
  }
}
