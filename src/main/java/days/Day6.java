package main.java.days;

import java.util.ArrayList;

public class Day6 {
    static int day = 6;
    static int[][] grid = new int[1000][1000];

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
        starOne(data);
    }

    public static int starOne(ArrayList<String> data) {
        for (String s : data) {
            System.out.println(s);
            parse(s);
            System.out.println("--------------");
        }
        return 1;
    }

    public static int starTwo(ArrayList<String> data) {
        return 1;
    }

    public static void parse(String s) {
        if (s.startsWith("toggle")) {
            System.out.println("TOGGLE");
        } else if (s.startsWith("turn on")) {
            System.out.println("TURN ON");
        } else if (s.startsWith("turn off")) {
            System.out.println("TURN OFF");
        }
        String out = "";
        int j = 0;
        int[] numbers = new int[4];
        for (int i = 1; i < s.length(); i++) {
            boolean currCharIsDigit = Character.isDigit(s.charAt(i));
            boolean prevCharIsDigit = Character.isDigit(s.charAt(i - 1));

            if (currCharIsDigit) {
                out = out + s.charAt(i);
                if (i == s.length() - 1) {
                    numbers[3] = Integer.parseInt(out);
                }
            } else if (!currCharIsDigit && prevCharIsDigit) {
                numbers[j] = Integer.parseInt(out);
                j++;
                out = "";
            }
        }
        System.out.println(java.util.Arrays.toString(numbers));
    }
}
