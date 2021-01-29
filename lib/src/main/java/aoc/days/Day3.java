package aoc.days;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import aoc.utils.ReadTextFile;

public class Day3 {
  private static final int day = 3;

  public static void main(String[] args) {
    var data = ReadTextFile.readFile(day);
    Day3 day3 = new Day3();
    System.out.println("Day " + day + " star 1: " + day3.starOne(data));
    System.out.println("Day " + day + " star 2: " + day3.starTwo(data));
  }

  private int starOne(ArrayList<String> data) {
    return calculateVisits(data.get(0)).size();
  }

  private int starTwo(ArrayList<String> data) {
    String line = data.get(0);

    List<Character> santaTurns = parseSantaTurns(line, 0);
    List<Character> roboSantaTurns = parseSantaTurns(line, 1);

    HashMap<String, Integer> s = calculateVisits(String.valueOf(santaTurns));
    HashMap<String, Integer> rs = calculateVisits(String.valueOf(roboSantaTurns));
    s.putAll(rs);
    return s.size();
  }

  private List<Character> parseSantaTurns(String line, int startIndex) {
    List<Character> santaTurns = new ArrayList<>();
    for (int i = startIndex; i < line.length(); i = i + 2) {
      santaTurns.add(line.charAt(i));
    }
    return santaTurns;
  }

  private HashMap<String, Integer> calculateVisits(String line) {
    HashMap<String, Integer> hmap = new HashMap<>();
    hmap.put("0_0", 1);
    Integer currX = 0;
    Integer currY = 0;
    for (int i = 0; i < line.length(); i++) {
      currX = updateX(line.charAt(i), currX);
      currY = updateY(line.charAt(i), currY);
      String currentPositionKey = createHashMapKey(currX, currY);
      updateHashMap(hmap, currentPositionKey);
    }
    return hmap;
  }

  private void updateHashMap(HashMap<String, Integer> hmap, String key) {
    if (hmap.containsKey(key)) {
      int i = hmap.get(key);
      hmap.put(key, --i);
    } else {
      hmap.put(key, 1);
    }
  }

  private Integer updateX(char c, Integer i) {
    if (c == '>') return ++i;
    if (c == '<') return --i;
    return i;
  }

  private Integer updateY(char c, Integer i) {
    if (c == '^') return ++i;
    if (c == 'v') return --i;
    return i;
  }

  private String createHashMapKey(Integer x, Integer y) {
    return x + "_" + y;
  }
}
