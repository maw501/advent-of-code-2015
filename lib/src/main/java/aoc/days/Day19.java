package aoc.days;

import aoc.utils.ReadTextFile;

import java.util.*;

public class Day19 {
  private static final int day = 19;
  private final HashMap<String, ArrayList<String>> replacements = new HashMap<>();
  private String targetMolecule;
  // private HashSet<Integer> steps = new HashSet<>();

  public static void main(String[] args) {
    ArrayList<String> data = ReadTextFile.readFile(day);
    Day19 day19 = new Day19();
    day19.parseData(data);
    System.out.println("Day " + day + " star 1: " + day19.starOne());
    System.out.println("Day " + day + " star 2: " + day19.starTwo());
  }

  private int starOne() {
    return generateSingleStepStates(targetMolecule).size();
  }

  private int starTwo() {
    System.out.println("Searching for: " + targetMolecule);
    return breadthFirstSearch("e");
  }

  private int breadthFirstSearch(String node) {
    LinkedList<String> frontier = new LinkedList<>();
    frontier.add(node);
    HashSet<String> explored = new HashSet<>();
    HashMap<String, Integer> levels = new HashMap<>();
    levels.put(node, 0);
    while (frontier.size() > 0) {
      String state = frontier.removeFirst();
      int stateLevel = levels.get(state);
      // System.out.println("State: " + state);
      explored.add(state);
      if (state.equals(targetMolecule)) {
        System.out.println("Woohoo");
        return levels.get(state);
      }
      HashSet<String> neighbours = generateSingleStepStates(state);
      // System.out.println("Neighbours: " + neighbours);
      for (String neighbour : neighbours) {
        if (!frontier.contains(neighbour) && !explored.contains(neighbour)) {
          frontier.addLast(neighbour);
          levels.put(neighbour, stateLevel + 1);
          // System.out.println("Adding: " + neighbour);
        }
      }
      // System.out.println("At level: " + stateLevel);
      // System.out.println("Frontier: " + frontier);
      // System.out.println("------------------------------------------------------------------");
      System.out.println("Frontier size: " + frontier.size());
      System.out.println("Num explored: " + explored.size());
      // if (count > 2000) break;
    }
    return -1;
  }

  private HashSet<String> generateSingleStepStates(String molecule) {
    HashSet<String> out = new HashSet<>();
    for (String key : replacements.keySet()) {
      ArrayList<Integer> indices = getAllIndicesFromMolecule(molecule, key);
      if (indices.size() > 0) {
        for (int i : indices) {
          ArrayList<String> substitutions = replacements.get(key);
          for (String sub : substitutions) {
            String newMolecule =
                getStartOfMolecule(molecule, i) + sub + getEndOfMolecule(molecule, i, key);
            out.add(newMolecule);
          }
        }
      }
    }
    return out;
  }

  private String getStartOfMolecule(String molecule, int i) {
    if (i == 0) return "";
    return molecule.substring(0, i);
  }

  private String getEndOfMolecule(String molecule, int i, String key) {
    if (i + key.length() >= molecule.length()) return "";
    return molecule.substring(i + key.length());
  }

  private ArrayList<Integer> getAllIndicesFromMolecule(String molecule, String findString) {
    int lastIndex = 0;
    ArrayList<Integer> indices = new ArrayList<>();
    while (lastIndex != -1) {
      lastIndex = molecule.indexOf(findString, lastIndex);
      if (lastIndex != -1) {
        lastIndex += findString.length();
        indices.add(lastIndex - findString.length());
      }
    }
    return indices;
  }

  private void parseData(ArrayList<String> data) {
    for (String line : data) {
      String[] result = line.split(" ");
      if (result.length == 3) {
        if (replacements.containsKey(result[0])) {
          ArrayList<String> item = replacements.get(result[0]);
          item.add(result[2]);
          replacements.put(result[0], item);
        } else {
          ArrayList<String> replacement = new ArrayList<>();
          replacement.add(result[2]);
          replacements.put(result[0], replacement);
        }
      }
      if (result.length == 1 && !line.equals("")) targetMolecule = result[0];
    }
  }
}
