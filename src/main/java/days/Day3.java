package main.java.days;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    static int day = 3;

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }

    public static int starOne(ArrayList<String> data) {
        return calculateVisits(data.get(0)).size();
    }

    public static int starTwo(ArrayList<String> data) {
        String line = data.get(0);

        List santaTurns = parseSantaTurns(line, 0);
        List roboSantaTurns = parseSantaTurns(line, 1);

        HashMap s = calculateVisits(String.valueOf(santaTurns));
        HashMap rs = calculateVisits(String.valueOf(roboSantaTurns));
        s.putAll(rs);
        return s.size();
    }

    public static List parseSantaTurns(String line, int startIndex) {
        List<Character> santaTurns = new ArrayList<>();
        for (int i = startIndex; i < line.length(); i = i + 2) {
            santaTurns.add(line.charAt(i));
        }
        return santaTurns;
    }

    public static HashMap<String, Integer> calculateVisits(String line) {
        HashMap<String, Integer> hmap = new HashMap<>();
        hmap.put("0_0", 1);
        Integer currX = 0;
        Integer currY = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            currX = updateX(c, currX);
            currY = updateY(c, currY);
            String currentPositionKey = createHashMapKey(currX, currY);
            updateHashMap(hmap, currentPositionKey);
        }
        return hmap;
    }

    public static void updateHashMap(HashMap hmap, String key) {
        if (hmap.containsKey(key)) {
            int i = (int) hmap.get(key);
            hmap.put(key, i + 1);
        } else {
            hmap.put(key, 1);
        }
    }

    public static Integer updateX(char c, Integer i) {
        if (c == '>') return i + 1;
        if (c == '<') return i - 1;
        return i;
    }

    public static Integer updateY(char c, Integer i) {
        if (c == '^') return i + 1;
        if (c == 'v') return i - 1;
        return i;
    }

    public static String createHashMapKey(Integer x, Integer y) {
        return x + "_" + y;
    }


}
