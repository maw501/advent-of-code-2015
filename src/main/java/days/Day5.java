package main.java.days;

import java.util.ArrayList;
import java.util.HashMap;

public class Day5 {
  private static final int day = 5;
  private static final char[] vowels = {'a', 'e', 'i', 'o', 'u'};

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    System.out.println("Day " + day + " star 1: " + starOne(data));
    System.out.println("Day " + day + " star 2: " + starTwo(data));
  }

  private static int starOne(ArrayList<String> data) {
    int numberNiceStings = 0;
    for (String s : data) {
      boolean niceString =
          stringHasThreeVowels(s)
              && stringHasTwoConsecutiveLetters(s)
              && stringHasNoBannedSubstring(s);
      if (niceString) numberNiceStings++;
    }
    return numberNiceStings;
  }

  private static int starTwo(ArrayList<String> data) {
    int numberNiceStings = 0;
    for (String s : data) {
      if (stringHasTwoPairs(s) && stringLetterRepeatsByOne(s)) numberNiceStings++;
    }
    return numberNiceStings;
  }

  private static boolean stringHasTwoPairs(String s) {
    HashMap<String, Integer> hmap = new HashMap<>();
    for (int i = 0; i < s.length() - 1; i++) {
      String sub = s.substring(i, i + 2);
      if (hmap.containsKey(sub)) {
        int prevIndex = hmap.get(sub);
        if (i - prevIndex > 1) return true;
      } else {
        hmap.put(sub, i);
      }
    }
    return false;
  }

  private static boolean stringLetterRepeatsByOne(String s) {
    for (int i = 2; i < s.length(); i++) {
      if (s.charAt(i) == s.charAt(i - 2)) return true;
    }
    return false;
  }

  private static boolean stringHasTwoConsecutiveLetters(String s) {
    char prevChar = s.charAt(0);
    for (char c : s.substring(1).toCharArray()) {
      if (c == prevChar) {
        return true;
      } else {
        prevChar = c;
      }
    }
    return false;
  }

  private static boolean stringHasNoBannedSubstring(String s) {
    char prevChar = s.charAt(0);
    for (char currChar : s.substring(1).toCharArray()) {
      if (prevChar == 'a' && currChar == 'b') return false;
      if (prevChar == 'c' && currChar == 'd') return false;
      if (prevChar == 'p' && currChar == 'q') return false;
      if (prevChar == 'x' && currChar == 'y') return false;
      prevChar = currChar;
    }
    return true;
  }

  private static boolean stringHasThreeVowels(String s) {
    int counter = 0;
    for (char c : vowels) {
      counter = counter + countCharInString(s, c);
    }
    return counter >= 3;
  }

  private static int countCharInString(String s, char c) {
    int counter = 0;
    for (int index = s.indexOf(c); index >= 0; index = s.indexOf(c, index + 1)) {
      counter++;
    }
    return counter;
  }
}
