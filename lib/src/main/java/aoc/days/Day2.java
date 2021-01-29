package aoc.days;

import java.util.ArrayList;
import aoc.utils.ReadTextFile;

public class Day2 {
  private static final int day = 2;

  public static void main(String[] args) {
    var data = ReadTextFile.readFile(day);
    Day2 day2 = new Day2();
    System.out.println("Day " + day + " star 1: " + day2.starOne(data));
    System.out.println("Day " + day + " star 2: " + day2.starTwo(data));
  }

  private int starOne(ArrayList<String> data) {
    return calculateAllPresentsPaper(data);
  }

  private int starTwo(ArrayList<String> data) {
    return calculateAllPresentRibbon(data);
  }

  private String[] splitPresentString(String s) {
    return s.split("x");
  }

  private int[] convertStringDimsToIntegers(String s) {
    String[] presentString = splitPresentString(s);
    int[] array = new int[3];
    array[0] = Integer.parseInt(presentString[0]);
    array[1] = Integer.parseInt(presentString[1]);
    array[2] = Integer.parseInt(presentString[2]);
    return array;
  }

  private int calculateAllPresentRibbon(ArrayList<String> data) {
    int totalRibbon = 0;
    for (String s : data) {
      totalRibbon = totalRibbon + calculateSinglePresentRibbon(s);
    }
    return totalRibbon;
  }

  private int calculateSinglePresentRibbon(String s) {
    int[] array = convertStringDimsToIntegers(s);
    java.util.Arrays.sort(array);
    return 2 * array[0] + 2 * array[1] + array[0] * array[1] * array[2];
  }

  private int calculateAllPresentsPaper(ArrayList<String> data) {
    int totalPaper = 0;
    for (String s : data) {
      totalPaper = totalPaper + calculateSinglePresentPaper(s);
    }
    return totalPaper;
  }

  private int calculateSinglePresentPaper(String s) {
    int[] array = convertStringDimsToIntegers(s);
    int lw = array[0] * array[1];
    int wh = array[1] * array[2];
    int hl = array[2] * array[0];
    int smallestSide = Math.min(lw, Math.min(wh, hl));
    return 2 * lw + 2 * wh + 2 * hl + smallestSide;
  }
}
