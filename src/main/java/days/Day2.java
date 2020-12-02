package main.java.days;

import java.util.ArrayList;


public class Day2 {
    static int day = 2;

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        int answerStarOne = starOne(data);
        System.out.println("Day " + day + " star 1: " + answerStarOne);
        int answerStarTwo = starTwo(data.get(0));
        System.out.println("Day " + day + " star 2: " + answerStarTwo);
        calculateAllPresents(data);
    }

    public static int starOne(ArrayList<String> data) {
        return calculateAllPresents(data);
    }

    public static int starTwo(String line) {
        return 1;
    }

    public static String[] splitPresentString(String s) {
        return s.split("x");
    }

    public static int calculateAllPresents(ArrayList<String> data) {
        int totalPaper = 0;
        for (String s : data) {
            totalPaper = totalPaper + calculateSinglePresent(s);
        }
        return totalPaper;
    }

    public static int calculateSinglePresent(String s) {
        String[] presentString = splitPresentString(s);
        int presentLength = Integer.parseInt(presentString[0]);
        int presentWidth = Integer.parseInt(presentString[1]);
        int presentHeight = Integer.parseInt(presentString[2]);
        int lw = presentLength*presentWidth;
        int wh = presentWidth*presentHeight;
        int hl = presentHeight*presentLength;
        int smallestSide = Math.min(lw, Math.min(wh, hl));
        int requiredPaper = 2*lw + 2*wh + 2*hl + smallestSide;
        return requiredPaper;
    }

}
