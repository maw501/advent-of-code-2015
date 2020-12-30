package main.java.days;


import java.util.*;

import static java.util.Collections.*;

public class Day7 {
    static int day = 7;
    static Set<String> allWires = new HashSet<>();

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
//        System.out.println("Day " + day + " star 1: " + starOne(data));
//        System.out.println("Day " + day + " star 2: " + starTwo(data));
        starOne(data);
    }


    public static int starOne(ArrayList<String> data) {
        getAllWires(data);
        //sort(allWires);
        //System.out.println(allWires);
        HashMap<String, List<Integer>> wireToValuesMap = new HashMap<>();  // contains wire to current values
        HashMap<String, Wire> wireMap = new HashMap<>();  // contains wire to its dependents

        // First pass through
        for (String line : data) {
            System.out.println(line);
            Wire wire = new Wire(line);
            wire.processLine();

            List<String> toRemove = new ArrayList();
            Set<String> inputStrings = wire.getWireDependencies();
            for (String s : inputStrings) {
                if (tryParse(s) != -99) {
                    wireToValuesMap = updateWireValues(wireToValuesMap, wire.getName(), s);
                    System.out.println(wire.getName() + " has a value: " + tryParse(s));
                    toRemove.add(s);
                }
            }
            for (String s : toRemove) {
                wire.removeString(s);
                System.out.println("Removed: " + s + " from inputs of: " + wire.getName());
            }
            wireMap.put(wire.getName(), wire);
            Set<String> newInputStrings = wire.getWireDependencies();
            System.out.println("New dependents: " + newInputStrings);
            System.out.println("-------------------");
        }

        // Now do what?
        // Get all wires with no dependents
//        Set<String> resolvedWires = new HashSet<>();
//        for (String w : allWires) {
//            Wire wMap = wireMap.get(w);
//            Set<String> blah = wMap.getWireDependencies();
//            int l = wMap.getWireDependencies().size();
//            if (l == 0) resolvedWires.add(w);
//            System.out.println(w + " STILL DEPENDS ON : " + blah + " len: " + l);
//        }
//        System.out.println(resolvedWires);

        boolean stillResolving = true;
        Set<String> unresolvedWires = new HashSet<>();
        unresolvedWires.addAll(allWires);
        Set<String> resolvedWires = new HashSet<>();
        int counter = 0;
        while (stillResolving) {
            System.out.println("Unresolved: " + unresolvedWires);
            for (String wire : unresolvedWires){
                Wire wMap = wireMap.get(wire);
                Set<String> dependencies = wMap.getWireDependencies();
                int numRemainingDependencies = wMap.getWireDependencies().size();
                if (numRemainingDependencies == 0) resolvedWires.add(wire);
                //System.out.println(wire + " STILL DEPENDS ON : " + dependencies + " len: " + numRemainingDependencies);

                // Loop over each dependency:
                for (String dep : dependencies) {
                    System.out.println();
                }
            }
            System.out.println("Resolved: " + resolvedWires);

            // Remove resolved wires:
            for (String resolvedWire: resolvedWires){
                if (unresolvedWires.contains(resolvedWire)) {
                    unresolvedWires.remove(resolvedWire);
                }
            }



            // Loop termination
            if (unresolvedWires.size() == 0){
                stillResolving = false;
            }
            counter++;
            if (counter > 1) stillResolving = false;
        }

        return 1;
    }

    public static HashMap<String, List<Integer>> updateWireValues(HashMap<String, List<Integer>> h, String key, String s) {
        if (h.containsKey(key)) {
            h.get(key).add(tryParse(s));
        } else {
            h.put(key, new ArrayList());
            h.get(key).add(tryParse(s));
        }
        System.out.println(key + " has a value: " + tryParse(s));
        return h;
    }

    public static int tryParse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return -99;
        }
    }

    public static void getAllWires(ArrayList<String> data) {
        for (String line : data) {
            String[] result = line.split(" ");
            allWires.add(result[result.length - 1]);
        }
    }

    public static int starTwo(ArrayList<String> data) {
        return 1;
    }
}

class Wire {
    // An individual wire that
    private String line;
    Set<String> inputStrings = new HashSet<>();
    private String name;

    Wire(String s) {
        line = s;
    }

    public void processLine() {
        String[] result = line.split(" ");
        name = result[result.length - 1];
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
        System.out.println(name + " DEPENDS ON : " + inputStrings);
    }

    public Set<String> getWireDependencies() {
        return inputStrings;
    }

    public String getName() {
        return name;
    }

    public void removeString(String s) {
        inputStrings.remove(s);
    }
}