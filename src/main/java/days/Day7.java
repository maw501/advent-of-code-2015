package main.java.days;

import java.util.*;

public class Day7 {
  static int day = 7;
  private static HashMap<String, Short> wireValues;
  private static HashMap<String, Set<String>> wireDependencyMap;
  private static HashMap<String, String> wireOperatorMap;
  private static HashMap<String, Integer> wireShiftMap;

  public static void main(String[] args) {
    ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
    System.out.println("Day " + day + " star 1: " + starOne(data));
    System.out.println("Day " + day + " star 2: " + starTwo(data));
  }

  public static short starOne(ArrayList<String> data) {
    initialize();
    buildWireDependencyMaps(data);
    String targetWire = "a";
    short finalValue = getWireValue(targetWire);
    finalValue = processFinalValue(finalValue);
    return finalValue;
  }

  public static short starTwo(ArrayList<String> data) {
    initialize();
    short starOneValue = starOne(data);
    initialize();
    buildWireDependencyMaps(data);
    String targetWire = "a";
    wireValues.put("b", starOneValue);
    short finalValue = getWireValue(targetWire);
    finalValue = processFinalValue(finalValue);
    return finalValue;
  }

  public static void initialize() {
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

  public static short getWireValue(String wireName) {
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

  public static short getDepValue(String depName) {
    if (wireValues.containsKey(depName)) {
      return wireValues.get(depName);
    } else {
      return tryParse(depName);
    }
  }

  public static short resolveSingleDep(String wireName, short depValue, String operator) {
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

  public static short resolveTwoDep(Set<String> dependencies, String operator) {
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

  public static boolean canGetWiresValue(String wire) {
    boolean alreadyHasValue = wireValues.containsKey(wire);
    boolean canBeConvertedToValue = canParseToInteger(wire);
    return alreadyHasValue || canBeConvertedToValue;
  }

  public static void buildWireDependencyMaps(ArrayList<String> data) {
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

  public static Set<String> extractWireDependencies(String line) {
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

  public static String extractWireOperators(String line) {
    if (line.contains("AND")) return "AND";
    if (line.contains("OR")) return "OR";
    if (line.contains("NOT")) return "NOT";
    if (line.contains("LSHIFT")) return "LSHIFT";
    if (line.contains("RSHIFT")) return "RSHIFT";
    return "ASSIGN";
  }

  public static int extractWireShiftOperators(String line) {
    String[] result = line.split(" ");
    if (line.contains("SHIFT")) {
      return Integer.parseInt(result[2]);
    } else {
      return 0;
    }
  }

  public static short tryParse(String s) {
    try {
      return (short) Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return -99;
    }
  }

  public static boolean canParseToInteger(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }
}
