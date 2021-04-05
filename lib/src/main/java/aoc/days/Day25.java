package aoc.days;

public class Day25 {
  private static final int day = 25;

  public static void main(String[] args) {
    Day25 day25 = new Day25();
    System.out.println("Day " + day + " star 1: " + day25.starOne(20151125));
  }

  private long starOne(long code) {
    int rowNum = 2;
    int colNum = 1;
    while (true) {
      code = (code * 252533) % 33554393;
      if (rowNum == 2981 && colNum == 3075) {
        break;
      }
      rowNum--;
      colNum++;
      if (rowNum < 1) {
        rowNum = colNum;
        colNum = 1;
      }
    }
    return code;
  }
}
