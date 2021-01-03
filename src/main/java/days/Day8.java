package main.java.days;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {
  private static final int day = 8;
  private Pattern regexHex = Pattern.compile("\\\\x[0-9a-f]{2}");
  private Pattern regexDQorBS = Pattern.compile("\\\\|\\\"");

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    Day8 day8 = new Day8();
    System.out.println("Day " + day + " star 1: " + day8.starOne(data));
    // Star Two answer is actually 2046 but all solutions return 2045??? Including on Reddit...
    System.out.println("Day " + day + " star 2: " + day8.starTwo(data));
  }

  private int starOne(ArrayList<String> data) {
    int counter = 0;
    for (String s : data) {
      int numMatchesHex = getRegexCount(regexHex, s);
      int numMatchesDQorBS = getRegexCount(regexDQorBS, s);
      int cont = (numMatchesDQorBS - 2 - numMatchesHex) / 2 + numMatchesHex * 3 + 2;
      counter += cont;
    }
    return counter;
  }

  private int getRegexCount(Pattern regexPattern, String s) {
    Matcher matcher = regexPattern.matcher(s);
    return (int) matcher.results().count();
  }

  private int starTwo(ArrayList<String> data) {
    int counter = 0;
    for (String s : data) {
      int numMatchesDQorBS = getRegexCount(regexDQorBS, s);
      counter += numMatchesDQorBS + 2;
    }
    return counter;
  }
}
