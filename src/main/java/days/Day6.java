package main.java.days;

import java.util.ArrayList;

public class Day6 {
    static int day = 5;
    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }
    public static int starOne(ArrayList<String> data) {
        return 1;
    }
    public static int starTwo(ArrayList<String> data) {
        return 1;
    }
}
