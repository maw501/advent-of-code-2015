package main.java.days;

import java.util.ArrayList;


public class Day2 {
    static int day = 2;

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        int answerStarOne = starOne(data.get(0));
        System.out.println("Day " + day + " star 1: " + answerStarOne);
        int answerStarTwo = starTwo(data.get(0));
        System.out.println("Day " + day + " star 2: " + answerStarTwo);
    }

    public static int starOne(String line) {
        return 1;
    }

    public static int starTwo(String line) {
        return 1;
    }
}
