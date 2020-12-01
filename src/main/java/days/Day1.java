package main.java.days;

import java.util.ArrayList;


public class Day1 {
    static int day = 1;

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);

        // Star 1:
        int answerStarOne = starOne(data.get(0));
        System.out.println("Day " + day + " star 1: " + answerStarOne);

        // Star 2:
        int answerStarTwo = starTwo(data.get(0));
        System.out.println("Day " + day + " star 2: " + answerStarTwo);
    }

    public static int starOne(String line) {
        int currentFloor = 0;
        for (int position = 0; position < line.length(); position++) {
            currentFloor = nextFloor(line, position, currentFloor);
        }
        return currentFloor;
    }

    public static int starTwo(String line) {
        int currentFloor = 0;
        int position = 0;
        while (currentFloor != -1) {
            currentFloor = nextFloor(line, position, currentFloor);
            position++;
        }
        return position;
    }

    public static int nextFloor(String line, int position,
                                int currentFloor) {
        char c = line.charAt(position);
        if (c == '(') return currentFloor + 1;
        if (c == ')') return currentFloor - 1;
        throw new java.lang.RuntimeException("What the fluff");
    }

}
