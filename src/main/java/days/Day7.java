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
        HashMap<String, List<Integer>> wireToValuesMap = new HashMap<>();  // contains wire to current values
        HashMap<String, Wire> wireMap = new HashMap<>();  // contains wire to its dependents

        // First pass through
        for (String line : data) {
            Wire wire = new Wire(line);
            wire.processLine();
            Set<String> inputStrings = wire.getWireDependencies();
            for (String s : inputStrings) {
                if (tryParse(s) != -99) {
                    wireToValuesMap = updateWireValues(wireToValuesMap, wire.getName(), s);

                }
            }

            wireMap.put(wire.getName(), wire);
        }
        boolean frustrated = true;
        Wire targetWire = wireMap.get("h");
        Set<Integer> base = new HashSet<>();
        Set<String> dependencies = targetWire.getWireDependencies();
        System.out.println("----------------------------");
        System.out.println("Starting dep: " + dependencies);
        System.out.println("----------------------------");
        while (frustrated){
            Set<String> toRemove = new HashSet<>();
            Set<String> toAdd = new HashSet<>();
            for (String dep : dependencies){
                System.out.println("Processing dep: " + dep);
                if (tryParse(dep) != -99){
                    base.add(tryParse(dep));
                    toRemove.add(dep);
                } else {
                    Wire depWire = wireMap.get(dep);
                    Set<String> addDep = depWire.getWireDependencies();
                    System.out.println("Adding deps: " + addDep);
                    toAdd.addAll(depWire.getWireDependencies());
                }
                toRemove.add(dep);
            }
            System.out.println("To add: " + toAdd);
            System.out.println("To remove: " + toRemove);
            dependencies.removeAll(toRemove);
            dependencies.addAll(toAdd);
            System.out.println("Bottom: " + dependencies);
            System.out.println("----------------------------");
            if (dependencies.size() == 0) frustrated = false;
        }
        System.out.println(base);
        return 1;
    }

    public static HashMap<String, List<Integer>> updateWireValues(HashMap<String, List<Integer>> h, String key, String s) {
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