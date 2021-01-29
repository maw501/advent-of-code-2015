package aoc.days;

import java.util.*;
import aoc.utils.ReadTextFile;

public class Day7 {
  private static final int day = 7;
  private final HashMap<String, Short> wireValues = new HashMap<>();
  private final HashMap<String, Set<String>> wireDependencyMap = new HashMap<>();
  private final HashMap<String, String> wireOperatorMap = new HashMap<>();
  private final HashMap<String, Integer> wireShiftMap = new HashMap<>();
  private final int defaultErrorValue = -99;

  public static void main(String[] args) {
    ArrayList<String> data = ReadTextFile.readFile(day);
    Day7 d7s1 = new Day7();
    short starOneAnswer = d7s1.starOne(data);
    Day7 d7s2 = new Day7();
    System.out.println("Day " + day + " star 1: " + starOneAnswer);
    System.out.println("Day " + day + " star 2: " + d7s2.starTwo(data, starOneAnswer));
  }

  private short starOne(ArrayList<String> data) {
    buildWireDependencyMaps(data);
    short finalValue = getWireValue("a");
    return processFinalValue(finalValue);
  }

  private short starTwo(ArrayList<String> data, short starOneAnswer) {
    buildWireDependencyMaps(data);
    wireValues.put("b", starOneAnswer);
    short finalValue = getWireValue("a");
    return processFinalValue(finalValue);
  }

  public static short processFinalValue(short x) {
    // Java doesn't support unsigned 16-bit integers
    if (x < 0) return (short) (65536 + x);
    return x;
  }

  private short getWireValue(String wireName) {
    short finalWireValue = defaultErrorValue;
    Set<String> dependencies = wireDependencyMap.get(wireName);
    int numDependencies = dependencies.size();
    String operator = wireOperatorMap.get(wireName);
    Set<String> unresolvedDependencies = new HashSet<>();
    for (String dep : dependencies) {
      boolean resolvableDependency = canGetWiresValue(dep);
      if (!resolvableDependency) {
        unresolvedDependencies.add(dep);
      }
    }
    boolean canResolveAllDependencies = unresolvedDependencies.size() == 0;

    // call recursively to resolve the value of each dependency
    if (!canResolveAllDependencies) {
      for (String depName : unresolvedDependencies) {
        wireValues.put(depName, getWireValue(depName));
      }
    }

    // Now we've resolved all dependencies, calculate the wire value
    if (numDependencies == 1) {
      String depName = dependencies.iterator().next();
      finalWireValue = resolveSingleDep(wireName, getDepValue(depName), operator);
    } else if (numDependencies == 2) {
      finalWireValue = resolveTwoDep(dependencies, operator);
    }
    wireValues.put(wireName, finalWireValue);
    return finalWireValue;
  }

  private short getDepValue(String depName) {
    if (wireValues.containsKey(depName)) return wireValues.get(depName);
    return tryParse(depName);
  }

  private short resolveSingleDep(String wireName, short depValue, String operator) {
    if (operator.equals("ASSIGN")) return depValue;
    if (operator.equals("LSHIFT")) return (short) (depValue << wireShiftMap.get(wireName));
    if (operator.equals("RSHIFT")) return (short) (depValue >>> wireShiftMap.get(wireName));
    if (operator.equals("NOT")) return (short) ~depValue;
    return defaultErrorValue;
  }

  private short resolveTwoDep(Set<String> dependencies, String operator) {
    ArrayList<String> depNames = new ArrayList<String>(dependencies);
    if (operator.equals("AND")) {
      return (short) (getDepValue(depNames.get(0)) & getDepValue(depNames.get(1)));
    }
    if (operator.equals("OR")) {
      return (short) (getDepValue(depNames.get(0)) | getDepValue(depNames.get(1)));
    }
    return defaultErrorValue;
  }

  private boolean canGetWiresValue(String wire) {
    boolean alreadyHasValue = wireValues.containsKey(wire);
    boolean canBeConvertedToValue = canParseToInteger(wire);
    return alreadyHasValue || canBeConvertedToValue;
  }

  private void buildWireDependencyMaps(ArrayList<String> data) {
    for (String line : data) {
      String[] result = line.split(" ");
      String wireName = result[result.length - 1];
      wireDependencyMap.put(wireName, extractWireDependencies(line));
      wireOperatorMap.put(wireName, extractWireOperators(line));
      wireShiftMap.put(wireName, extractWireShiftOperators(line));
    }
  }

  private Set<String> extractWireDependencies(String line) {
    Set<String> inputStrings = new HashSet<>();
    String[] result = line.split(" ");
    if ((line.contains("AND")) || (line.contains("OR"))) {
      inputStrings.add(result[0]);
      inputStrings.add(result[2]);
      return inputStrings;
    }
    if (line.contains("NOT")) {
      inputStrings.add(result[1]);
      return inputStrings;
    }
    inputStrings.add(result[0]);
    return inputStrings;
  }

  private String extractWireOperators(String line) {
    if (line.contains("AND")) return "AND";
    if (line.contains("OR")) return "OR";
    if (line.contains("NOT")) return "NOT";
    if (line.contains("LSHIFT")) return "LSHIFT";
    if (line.contains("RSHIFT")) return "RSHIFT";
    return "ASSIGN";
  }

  private int extractWireShiftOperators(String line) {
    String[] result = line.split(" ");
    if (line.contains("SHIFT")) return Integer.parseInt(result[2]);
    return 0;
  }

  private short tryParse(String s) {
    try {
      return (short) Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return defaultErrorValue;
    }
  }

  private boolean canParseToInteger(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }
}
