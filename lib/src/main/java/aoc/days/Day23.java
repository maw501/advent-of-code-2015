package aoc.days;

import aoc.utils.ReadTextFile;

import java.util.ArrayList;
import java.util.HashMap;

public class Day23 {
  private static final int day = 23;
  private final HashMap<Integer, String> registers = new HashMap<>();
  private final HashMap<Integer, Integer> jumpValues = new HashMap<>();
  private final ArrayList<String> instructions = new ArrayList<>();

  public static void main(String[] args) {
    ArrayList<String> data = ReadTextFile.readFile(day);
    Day23 day23 = new Day23();
    day23.parseData(data);
    System.out.println("Day " + day + " star 1: " + day23.runComputer(0));
    System.out.println("Day " + day + " star 1: " + day23.runComputer(1));
  }

  private int runComputer(int a) {
    boolean programComplete = false;
    int instructionLength = instructions.size();
    int i = 0;
    int b = 0;
    while (!programComplete) {
      String instruction = instructions.get(i);
      if (instruction.equals("jio")) {
        if (a == 1) {
          i += jumpValues.get(i);
        } else {
          i++;
        }
      }
      if (instruction.equals("jie")) {
        if (a % 2 == 0) {
          i += jumpValues.get(i);
        } else {
          i++;
        }
      }
      if (instruction.equals("inc")) {
        if (registers.get(i).equals("a")) {
          a++;
        } else {
          b++;
        }
        i++;
      }
      if (instruction.equals("tpl")) {
        if (registers.get(i).equals("a")) {
          a = 3 * a;
        } else {
          b = 3 * b;
        }
        i++;
      }
      if (instruction.equals("hlf")) {
        if (registers.get(i).equals("a")) {
          a = a / 2;
        } else {
          b = b / 2;
        }
        i++;
      }
      if (instruction.equals("jmp")) {
        i += jumpValues.get(i);
      }
      if (i >= instructionLength) programComplete = true;
    }
    return b;
  }

  private void parseData(ArrayList<String> data) {
    int i = 0;
    for (String s : data) {
      String[] result = s.substring(3).split(",");
      String instruction = s.substring(0, 3);
      instructions.add(instruction);
      if (!instruction.equals("jmp")) registers.put(i, result[0].substring(1));
      if (instruction.equals("jio")) jumpValues.put(i, parseString(result[1].substring(1)));
      if (instruction.equals("jie")) jumpValues.put(i, parseString(result[1].substring(1)));
      if (instruction.equals("jmp")) jumpValues.put(i, parseString(s.substring(4)));
      i++;
    }
  }

  private Integer parseString(String s) {
    if (s.charAt(0) == '+') return Integer.parseInt(s.substring(1));
    if (s.charAt(0) == '-') return -Integer.parseInt(s.substring(1));
    return 0;
  }
}
