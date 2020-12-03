package main.java.days;

import java.util.HashMap;
import java.util.ArrayList;

public class Day3 {
    static int day = 3;

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }

    public static int starOne(ArrayList<String> data) {

        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        hmap.put("0_0", 1);
        String line = data.get(0);
        Integer currX = 0;
        Integer currY = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '^') {
                currY++;
            } else if (c == '>') {
                currX++;
            } else if (c == 'v') {
                currY--;
            } else if (c == '<') {
                currX--;
            } else {
                throw new java.lang.RuntimeException("What the fluff");
            }
            String currentPositionKey = createHashMapKey(currX, currY);
            if (hmap.containsKey(currentPositionKey)) {
                hmap.put(currentPositionKey, hmap.get(currentPositionKey) + 1);
            } else {
                hmap.put(currentPositionKey, 1);
            }
        }
        return hmap.size();
    }

    public static String createHashMapKey(Integer x, Integer y) {
        return Integer.toString(x) + "_" + Integer.toString(y);
    }

    public static int starTwo(ArrayList<String> data) {
        return 0;
    }
}
