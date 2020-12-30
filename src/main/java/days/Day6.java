package main.java.days;

import java.util.ArrayList;
import java.util.Arrays;

public class Day6 {
    static int day = 6;
    static int gridDim = 1000;
    static int[][] grid = new int[gridDim][gridDim];

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
        starOne(data);
    }

    public static int starOne(ArrayList<String> data) {
        for (String s : data) {
            String mode = parseModeFromInstruction(s);
            int[] numbers = parseNumbersFromInstruction(s);
            updateGridFromInstruction(numbers, mode);
        }
        return sum2DArray(grid);
    }

    public static int starTwo(ArrayList<String> data) {
        return 1;
    }


    public static void updateGridFromInstruction(int[] numbers, String mode) {
        for (int i = numbers[0]; i <= numbers[2]; i++) {
            for (int j = numbers[1]; j <= numbers[3]; j++) {
                updateGrid(i, j, mode);
            }
        }
    }

    public static void updateGrid(int i, int j, String mode) {
        int currValue = grid[i][j];
        if (mode.equals("TOGGLE")) grid[i][j] = 1 - currValue;
        if (mode.equals("TURN ON")) grid[i][j] = 1;
        if (mode.equals("TURN OFF")) grid[i][j] = 0;
    }


    public static int[] parseNumbersFromInstruction(String s) {
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
        return numbers;
    }

    public static String parseModeFromInstruction(String s) {
        if (s.startsWith("toggle")) {
            return "TOGGLE";
        } else if (s.startsWith("turn on")) {
            return "TURN ON";
        } else if (s.startsWith("turn off")) {
            return "TURN OFF";
        } else {
            return "Unknown mode";
        }
    }

    public static int sum2DArray(int[][] array) {
        return Arrays.stream(array)
                .flatMapToInt(Arrays::stream)
                .sum();
    }
}
