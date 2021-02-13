package aoc.days;

import aoc.utils.ReadTextFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Day19 {
  private static final int day = 19;
  private final HashMap<String, ArrayList<String>> replacements = new HashMap<>();
  private String targetMolecule;
  private HashSet<Integer> steps = new HashSet<>();

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
    System.out.println("-----------------------------------------------");
    findTargetMolecule(generateSingleStepStates("e"), 1);
    return Collections.min(steps);
  }

  private void findTargetMolecule(HashSet<String> molecules, int numSteps) {
    int maxLength = targetMolecule.length() + 5;
    for (String molecule : molecules) {
      if (molecule.length() > maxLength || numSteps > 10) continue;
      HashSet<String> transitions = generateSingleStepStates(molecule);
      for (String candidateMolecule : transitions) {
        if (candidateMolecule.equals(targetMolecule)) {
          int finalSteps = numSteps + 1;
          steps.add(finalSteps);
        }
      }
      findTargetMolecule(transitions, numSteps + 1);
    }
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
