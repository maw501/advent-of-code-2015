package main.java.days;

import java.util.ArrayList;


public class Day2 {
    static int day = 2;

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }

    public static int starOne(ArrayList<String> data) {
        return calculateAllPresents(data);
    }

    public static int starTwo(ArrayList<String> data) {
        return 1;
    }

    public static String[] splitPresentString(String s) {
        return s.split("x");
    }

    public static int[] convertStringDimsToIntegers(String s) {
        String[] presentString = splitPresentString(s);
        int array[] = new int[3];
        array[0] = Integer.parseInt(presentString[0]);
        array[1] = Integer.parseInt(presentString[1]);
        array[2] = Integer.parseInt(presentString[2]);
        return array;
    }

    public static int calculateAllPresents(ArrayList<String> data) {
        int totalPaper = 0;
        for (String s : data) {
            totalPaper = totalPaper + calculateSinglePresent(s);
        }
        return totalPaper;
    }

    public static int calculateSinglePresent(String s) {
        int[] array = convertStringDimsToIntegers(s);
        int lw = array[0]*array[1];
        int wh = array[1]*array[2];
        int hl = array[2]*array[0];
        int smallestSide = Math.min(lw, Math.min(wh, hl));
        int requiredPaper = 2*lw + 2*wh + 2*hl + smallestSide;
        return requiredPaper;
    }

}
