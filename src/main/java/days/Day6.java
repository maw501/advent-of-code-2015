package main.java.days;

import java.util.ArrayList;
import java.util.Arrays;

public class Day6 {
    static int day = 6;
    static int gridDim = 1000;

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }

    public static int starOne(ArrayList<String> data) {
        Grid gridStarOne = new Grid(gridDim);
        for (String s : data) {
            String mode = parseModeFromInstruction(s);
            int[] numbers = parseNumbersFromInstruction(s);
            gridStarOne.updateGridFromInstruction(numbers, mode);
        }
        return gridStarOne.sumGrid();
    }

    public static int starTwo(ArrayList<String> data) {
        Grid gridStarTwo = new Grid(gridDim);
        for (String s : data) {
            String mode = parseModeFromInstruction(s);
            int[] numbers = parseNumbersFromInstruction(s);
            gridStarTwo.updateGridFromInstructionBrightness(numbers, mode);
        }
        return gridStarTwo.sumGrid();
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
            return "toggle";
        } else if (s.startsWith("turn on")) {
            return "turn on";
        } else if (s.startsWith("turn off")) {
            return "turn off";
        } else {
            return "Unknown mode";
        }
    }
}

class Grid {
    private int[][] grid;
    private String onMode = "turn on";
    private String offMode = "turn off";
    private String toggleMode = "toggle";

    Grid(int gridDim) {
        grid = new int[gridDim][gridDim];
    }

    public void updateGridFromInstruction(int[] numbers, String mode) {
        for (int i = numbers[0]; i <= numbers[2]; i++) {
            for (int j = numbers[1]; j <= numbers[3]; j++) {
                updateGrid(i, j, mode);
            }
        }
    }

    public void updateGridFromInstructionBrightness(int[] numbers, String mode) {
        for (int i = numbers[0]; i <= numbers[2]; i++) {
            for (int j = numbers[1]; j <= numbers[3]; j++) {
                updateGridBrightness(i, j, mode);
            }
        }
    }

    public void updateGrid(int i, int j, String mode) {
        int currValue = grid[i][j];
        if (mode.equals(toggleMode)) grid[i][j] = 1 - currValue;
        if (mode.equals(onMode)) grid[i][j] = 1;
        if (mode.equals(offMode)) grid[i][j] = 0;
    }

    public void updateGridBrightness(int i, int j, String mode) {
        int currValue = grid[i][j];
        if (mode.equals(toggleMode)) grid[i][j] = currValue + 2;
        if (mode.equals(onMode)) grid[i][j] = currValue + 1;
        if (mode.equals(offMode)) grid[i][j] = Math.max(0, currValue - 1);
    }

    public int sumGrid() {
        return Arrays.stream(grid)
                .flatMapToInt(Arrays::stream)
                .sum();
    }
}