package main.java.days;

import java.util.*;

public class Day7 {
  static int day = 7;
  private HashMap<String, Short> wireValues = new HashMap<>();
  private HashMap<String, Set<String>> wireDependencyMap = new HashMap<>();
  private HashMap<String, String> wireOperatorMap = new HashMap<>();
  private HashMap<String, Integer> wireShiftMap = new HashMap<>();

  public static void main(String[] args) {
    Day7 d7s1 = new Day7();
    Day7 d7s2 = new Day7();
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    System.out.println("Day " + day + " star 1: " + d7s1.starOne(data));
    System.out.println("Day " + day + " star 2: " + d7s2.starTwo(data));
  }

  private short starOne(ArrayList<String> data) {
    buildWireDependencyMaps(data);
    String targetWire = "a";
    short finalValue = getWireValue(targetWire);
    finalValue = processFinalValue(finalValue);
    return finalValue;
  }

  private short starTwo(ArrayList<String> data) {
    short starOneValue = starOne(data);
    flushMaps();
    buildWireDependencyMaps(data);
    String targetWire = "a";
    wireValues.put("b", starOneValue);
    short finalValue = getWireValue(targetWire);
    finalValue = processFinalValue(finalValue);
    return finalValue;
  }

  private void flushMaps() {
    wireValues = new HashMap<>();
    wireDependencyMap = new HashMap<>();
    wireOperatorMap = new HashMap<>();
    wireShiftMap = new HashMap<>();
  }

  public static short processFinalValue(short x) {
    // Java doesn't support unsigned 16-bit integers
    if (x < 0) {
      return (short) (65536 + x);
    }
    return x;
  }

  private short getWireValue(String wireName) {
    short finalWireValue = -99;
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
        short depValue = getWireValue(depName);
        wireValues.put(depName, depValue);
      }
    }

    // Now we've resolved all dependencies, calculate the wire value
    if (numDependencies == 1) {
      String depName = dependencies.iterator().next();
      finalWireValue = resolveSingleDep(wireName, getDepValue(depName), operator);
      wireValues.put(wireName, finalWireValue);
    } else if (numDependencies == 2) {
      finalWireValue = resolveTwoDep(dependencies, operator);
      wireValues.put(wireName, finalWireValue);
    }
    return finalWireValue;
  }

  private short getDepValue(String depName) {
    if (wireValues.containsKey(depName)) {
      return wireValues.get(depName);
    } else {
      return tryParse(depName);
    }
  }

  private short resolveSingleDep(String wireName, short depValue, String operator) {
    short finalWireValue = -99;
    if (operator.equals("ASSIGN")) {
      finalWireValue = depValue;
    } else if (operator.equals("LSHIFT")) {
      int shiftValue = wireShiftMap.get(wireName);
      finalWireValue = (short) (depValue << shiftValue);
    } else if (operator.equals("RSHIFT")) {
      int shiftValue = wireShiftMap.get(wireName);
      finalWireValue = (short) (depValue >>> shiftValue);
    } else if (operator.equals("NOT")) {
      finalWireValue = (short) ~depValue;
    }
    return finalWireValue;
  }

  private short resolveTwoDep(Set<String> dependencies, String operator) {
    ArrayList<String> depNames = new ArrayList<String>(dependencies);
    short depValue1 = getDepValue(depNames.get(0));
    short depValue2 = getDepValue(depNames.get(1));
    if (operator.equals("AND")) {
      return (short) (depValue1 & depValue2);
    } else if (operator.equals("OR")) {
      return (short) (depValue1 | depValue2);
    } else {
      return -99;
    }
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
      Set<String> dependencies = extractWireDependencies(line);
      String operator = extractWireOperators(line);
      int shiftValue = extractWireShiftOperators(line);
      wireDependencyMap.put(wireName, dependencies);
      wireOperatorMap.put(wireName, operator);
      wireShiftMap.put(wireName, shiftValue);
    }
  }

  private Set<String> extractWireDependencies(String line) {
    Set<String> inputStrings = new HashSet<>();
    String[] result = line.split(" ");
    if (line.contains("AND")) {
      inputStrings.add(result[0]);
      inputStrings.add(result[2]);
    } else if (line.contains("OR")) {
      inputStrings.add(result[0]);
      inputStrings.add(result[2]);
    } else if (line.contains("NOT")) {
      inputStrings.add(result[1]);
    } else if (line.contains("LSHIFT")) {
      inputStrings.add(result[0]);
    } else if (line.contains("RSHIFT")) {
      inputStrings.add(result[0]);
    } else {
      inputStrings.add(result[0]);
    }
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
    if (line.contains("SHIFT")) {
      return Integer.parseInt(result[2]);
    } else {
      return 0;
    }
  }

  private short tryParse(String s) {
    try {
      return (short) Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return -99;
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
