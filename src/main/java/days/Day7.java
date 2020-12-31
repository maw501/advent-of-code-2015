package main.java.days;


import java.util.*;


public class Day7 {
    static int day = 7;
    private static HashMap<String, Short> wireValues = new HashMap<>();
    private static HashMap<String, Set<String>> wireDependencyMap =
            new HashMap<>();
    private static HashMap<String, String> wireOperatorMap = new HashMap<>();
    private static HashMap<String, Integer> wireShiftMap = new HashMap<>();

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
//        System.out.println("Day " + day + " star 1: " + starOne(data));
//        System.out.println("Day " + day + " star 2: " + starTwo(data));
        starOne(data);
    }

    public static short starOne(ArrayList<String> data) {
        buildWireDependencyMaps(data);

        String targetWire = "f";
        short wireValue = getWireValue(targetWire);
        System.out.println("----------------------");
        System.out.println(wireValues);
        return 1;
    }

    public static short getWireValue(String wireName) {
        short finalWireValue = -99;
        Set<String> dependencies = wireDependencyMap.get(wireName);
        int numDependencies = dependencies.size();
        String operator = wireOperatorMap.get(wireName);
        Set<String> unresolvedDependencies = new HashSet<>();
        System.out.println(
                "Getting value for: " + wireName + " with deps: " + dependencies + " operator: " + operator);
        for (String dep : dependencies) {
            boolean resolvableDependency = canGetWiresValue(dep);
            System.out.println(dep + " is resolvable: " + resolvableDependency);
            if (!resolvableDependency) {
                unresolvedDependencies.add(dep);
            }
        }
        boolean canResolveAllDependencies = unresolvedDependencies.size() == 0;

        if (canResolveAllDependencies) {
            if (numDependencies == 1) {
                for (String dep : dependencies) {
                    finalWireValue = resolveSingleDep(wireName, dep, operator);
                }
            } else if (numDependencies == 2) {
                System.out.println("woohoo");
            }
        } else {  // call recursively to resolve each dependency
            for (String dep : unresolvedDependencies) {
                System.out.println("Getting value for dep: " + dep);
                short depValue = getWireValue(dep);
                System.out.println("Recursive call for: " + dep + " and " +
                        "value: " + depValue);
                wireValues.put(dep, depValue);
                finalWireValue = resolveSingleDep(wireName, dep, operator);
            }
        }
        wireValues.put(wireName, finalWireValue);
        return finalWireValue;
    }

    public static short resolveSingleDep(String wireName,
                                         String dep,
                                         String operator) {
        short finalWireValue = -99;
        short depValue;
        if (wireValues.containsKey(dep)) {
            System.out.println("Retrieving value for: " + dep);
            depValue = wireValues.get(dep);
        } else {
            depValue = tryParse(dep);
        }

        if (operator.equals("ASSIGN")) {
            finalWireValue = depValue;
        } else if (operator.equals("LSHIFT")) {
            int shiftValue = wireShiftMap.get(wireName);
            finalWireValue = (short) (depValue << shiftValue);
            System.out.println(
                    "LSHIFT " + depValue + " by " + shiftValue + " = " + finalWireValue);
        } else if (operator.equals("RSHIFT")) {
            int shiftValue = wireShiftMap.get(wireName);
            finalWireValue = (short) (depValue >>> shiftValue);
            System.out.println("RSHIFT " + depValue + " by " + shiftValue +
                    " = " + finalWireValue);
        } else if (operator.equals("NOT")) {
            finalWireValue = (short) ~depValue;
            System.out.println("NOT " + depValue + " = " + finalWireValue);
        }
        System.out.println(
                "Assigning value: " + finalWireValue + " to: " + wireName +
                        " operator: " + operator);
        return finalWireValue;
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
            System.out.println(wireName + " has deps: " + dependencies);
        }
        System.out.println("===================================");
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
        if (line.contains("AND")) {
            return "AND";
        } else if (line.contains("OR")) {
            return "OR";
        } else if (line.contains("NOT")) {
            return "NOT";
        } else if (line.contains("LSHIFT")) {
            return "LSHIFT";
        } else if (line.contains("RSHIFT")) {
            return "RSHIFT";
        } else {
            return "ASSIGN";
        }
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

    public static short starTwo(ArrayList<String> data) {
        return 1;
    }
}
