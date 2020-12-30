package main.java.days;


import java.util.*;

import static java.util.Collections.*;

public class Day7 {
    static int day = 7;
    static ArrayList<String> allWires = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
//        System.out.println("Day " + day + " star 1: " + starOne(data));
//        System.out.println("Day " + day + " star 2: " + starTwo(data));
        starOne(data);
    }

    public static int starOne(ArrayList<String> data) {
        getAllWires(data);
        sort(allWires);
        //System.out.println(allWires);
        HashMap<String, List<Integer>> hmap = new HashMap<String, List<Integer>>();
        HashMap<String, Wire> wireMap = new HashMap<String, Wire>();
        // First pass through
        for (String line : data) {
            System.out.println(line);
            Wire wire = new Wire(line);
            wire.processLine();
            List<String> toRemove = new ArrayList();
            Set<String> inputStrings = wire.getInputStrings();
            for (String s : inputStrings){
                if (tryParse(s) != -99) {
                    if (hmap.containsKey(wire.getName())) {
                        hmap.get(wire.getName()).add(tryParse(s));
                    } else {
                        hmap.put(wire.getName(), new ArrayList());
                        hmap.get(wire.getName()).add(tryParse(s));
                    }
                    System.out.println(wire.getName() + " has a value: " + tryParse(s));
                    toRemove.add(s);
                }
            }
            for (String s : toRemove){
                wire.removeString(s);
                System.out.println("Removed: " + s + " from inputs of: " + wire.getName());
            }
            wireMap.put(wire.getName(), wire);
            Set<String> newInputStrings = wire.getInputStrings();
            System.out.println("New dependents: " + newInputStrings);
            System.out.println("-------------------");
        }

        // Now do what?
        // Get all wires with no dependents
        for (String w : allWires){
            //System.out.println(w);
            Wire wMap = wireMap.get(w);
            Set<String> blah = wMap.getInputStrings();
            int l = wMap.getInputStrings().size();
            System.out.println(w + " STILL DEPENDS ON : " + blah + " len: " + l);
        }

        return 1;
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
    private String line;
    //private int[] inputValues;
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

    public Set<String> getInputStrings(){
        return inputStrings;
    }

    public String getName(){
        return name;
    }

    public void removeString(String s){
        inputStrings.remove(s);
    }
}