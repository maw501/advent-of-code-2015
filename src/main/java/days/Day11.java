package main.java.days;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day11 {
  private static final int day = 11;
  private String bannedChars = "iol";
  private Pattern regex = Pattern.compile("([a-z])\1");

  public static void main(String[] args) {
    String start = "xx";
    Day11 day11 = new Day11();
    System.out.println("Day " + day + " star 1: " + day11.starOne());
    System.out.println("Day " + day + " star 1: " + day11.starTwo());
  }

  private int starOne() {
    String start = "yyazz";
    String incrementedString = incrementString(start);
    boolean c1 = hasNoBannedChars(incrementedString);
    Matcher matcher = regex.matcher(incrementedString);
    int count = (int) matcher.results().count();
    System.out.println(count);

    return 1;
  }

  private int starTwo() {
    return 1;
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
    System.out.println("Input string: " + s + " incremented: " + startString);
    return String.valueOf(startString);
  }

  private char incrementSingleChar(char c) {
    if (c == 'z') return 'a';
    return (char) (c + 1);
  }

  private boolean hasNoBannedChars(String s) {
    for (char bannedChar : bannedChars.toCharArray()) {
      if (s.indexOf(bannedChar) > 0) {
        return false;
      }
    }
    return true;
  }
}
