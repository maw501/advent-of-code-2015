package main.java.days;

public class Day10 {
  private static final int day = 10;

  public static void main(String[] args) {
    String start = "3113322113";
    Day10 day10 = new Day10();
    System.out.println("Day " + day + " star 1: " + day10.starOne(start, 40));
    System.out.println("Day " + day + " star 1: " + day10.starTwo(start, 50));
  }

  private int starOne(String inputString, int nRuns) {
    return runLookAndSayNTimes(inputString, nRuns);
  }

  private int starTwo(String inputString, int nRuns) {
    return runLookAndSayNTimes(inputString, nRuns);
  }

  private int runLookAndSayNTimes(String start, int nRuns) {
    for (int i = 0; i < nRuns; i++) {
      start = lookAndSay(start);
    }
    return start.length();
  }

  private String lookAndSay(String s) {
    StringBuilder output = new StringBuilder();
    int n = s.length();
    Character prevChar = s.charAt(0);
    int currRun = 1;
    for (int i = 1; i < n; i++) {
      char currChar = s.charAt(i);
      if (prevChar == currChar) {
        currRun++;
      } else {
        output.append(currRun);
        output.append(prevChar);
        currRun = 1;
      }
      prevChar = currChar;
    }
    output.append(currRun);
    output.append(s.charAt(n - 1));
    return output.toString();
  }
}
