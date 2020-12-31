package main.java.days;


import java.util.*;


public class Day7 {
    static int day = 7;
    private static HashMap<String, Integer> wireValues = new HashMap<>();
    private static HashMap<String, Set<String>> wireDependencyMap =
            new HashMap<>();
    private static HashMap<String, String> wireOperatorMap = new HashMap<>();

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
//        System.out.println("Day " + day + " star 1: " + starOne(data));
//        System.out.println("Day " + day + " star 2: " + starTwo(data));
        starOne(data);
    }

    public static int starOne(ArrayList<String> data) {
        buildWireDependencyMaps(data);

        String targetWire = "l";
        int wireValue = getWireValue(targetWire);
        wireValues.put(targetWire, wireValue);
        System.out.println(wireValues);
        return 1;
    }

    public static int getWireValue(String wireName) {
        // Recursively gets a wire's value
        Set<String> dependencies = wireDependencyMap.get(wireName);
        int numDependencies = dependencies.size();
        String operator = wireOperatorMap.get(wireName);
        Set<String> unresolvedDependencies = new HashSet<>();
        System.out.println(
                "Getting value for: " + wireName + " with deps: " + dependencies);
        for (String dep : dependencies) {
            boolean resolvableDependency = canGetWiresValue(dep);
            System.out.println(dep + " is resolvable: " + resolvableDependency);
            if (!resolvableDependency) {
                unresolvedDependencies.add(dep);
            }
        }
        boolean canResolveAllDependencies = unresolvedDependencies.size() == 0;

        // Resolve
        if (canResolveAllDependencies) {
            if (numDependencies == 1){

            } else if (numDependencies == 2) {

            }
            System.out.println(operator);
        } else {  // call recursively
            for (String dep : unresolvedDependencies){
                getWireValue(dep);
            }
        }
        return 9;
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
            wireDependencyMap.put(wireName, dependencies);
            wireOperatorMap.put(wireName, operator);
            System.out.println(wireName + " has deps: " + dependencies);
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

//    public static int starOneTmp(ArrayList<String> data) {
//        getAllWires(data);
//        HashMap<String, List<Integer>> wireToValuesMap = new HashMap<>();
// contains wire to current values
//        HashMap<String, Wire> wireMap = new HashMap<>();  // contains wire
//        to its dependents
//
//        // First pass through
//        for (String line : data) {
//            Wire wire = new Wire(line);
//            wire.processLine();
//            Set<String> inputStrings = wire.getWireDependencies();
//            for (String s : inputStrings) {
//                if (tryParse(s) != -99) {
//                    wireToValuesMap = updateWireValues(wireToValuesMap,
//                    wire.getName(), s);
//
//                }
//            }
//            wireMap.put(wire.getName(), wire);
//        }
//
//        boolean frustrated = true;
//        Wire targetWire = wireMap.get("h");
//        Set<Integer> base = new HashSet<>();
//        Set<String> dependencies = targetWire.getWireDependencies();
//        System.out.println("----------------------------");
//        System.out.println("Starting dep: " + dependencies);
//        System.out.println("----------------------------");
//        while (frustrated) {
//            Set<String> toRemove = new HashSet<>();
//            Set<String> toAdd = new HashSet<>();
//            for (String dep : dependencies) {
//                System.out.println("Processing dep: " + dep);
//                if (tryParse(dep) != -99) {
//                    base.add(tryParse(dep));
//                    toRemove.add(dep);
//                } else {
//                    Wire depWire = wireMap.get(dep);
//                    Set<String> addDep = depWire.getWireDependencies();
//                    System.out.println("Adding deps: " + addDep);
//                    toAdd.addAll(depWire.getWireDependencies());
//                }
//                toRemove.add(dep);
//            }
//            System.out.println("To add: " + toAdd);
//            System.out.println("To remove: " + toRemove);
//            dependencies.removeAll(toRemove);
//            dependencies.addAll(toAdd);
//            System.out.println("Bottom: " + dependencies);
//            System.out.println("----------------------------");
//            if (dependencies.size() == 0) frustrated = false;
//        }
//        System.out.println(base);
//        return 1;
//    }

    public static HashMap<String, List<Integer>> updateWireValues(
            HashMap<String, List<Integer>> h, String key, String s) {
        if (h.containsKey(key)) {
            h.get(key).add(tryParse(s));
        } else {
            h.put(key, new ArrayList());
            h.get(key).add(tryParse(s));
        }
        // System.out.println(key + " has a value: " + tryParse(s));
        return h;
    }

    public static int tryParse(String s) {
        try {
            return Integer.parseInt(s);
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


    public static int starTwo(ArrayList<String> data) {
        return 1;
    }
}
