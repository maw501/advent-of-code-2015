package main.java.days;

public class Day1 {
  private static final int day = 1;

  public static void main(String[] args) {
    var data = main.java.utils.ReadTextFile.readFile(day);
    Day1 day1 = new Day1();
    System.out.println("Day " + day + " star 1: " + day1.starOne(data.get(0)));
    System.out.println("Day " + day + " star 2: " + day1.starTwo(data.get(0)));
  }

  private int starOne(String line) {
    int currentFloor = 0;
    for (int position = 0; position < line.length(); position++) {
      currentFloor = nextFloor(line, position, currentFloor);
    }
    return currentFloor;
  }

  private int starTwo(String line) {
    int currentFloor = 0;
    int position = 0;
    while (currentFloor != -1) {
      currentFloor = nextFloor(line, position, currentFloor);
      position++;
    }
    return position;
  }

  private int nextFloor(String line, int position, int currentFloor) {
    if (line.charAt(position) == '(') return ++currentFloor;
    if (line.charAt(position) == ')') return --currentFloor;
    throw new java.lang.RuntimeException("What the fluff");
  }
}
