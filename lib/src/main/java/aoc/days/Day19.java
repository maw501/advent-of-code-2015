package aoc.days;

import aoc.utils.ReadTextFile;

import java.util.*;

public class Day19 {
  private static final int day = 19;
  private HashMap<String, ArrayList<String>> replacements = new HashMap<>();
  private String targetMolecule;

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
    int numRn = getAllIndicesFromMolecule(targetMolecule, "Rn").size();
    int numAr = getAllIndicesFromMolecule(targetMolecule, "Ar").size();
    int numY = getAllIndicesFromMolecule(targetMolecule, "Y").size();
    int count = 0;
    char prevChar = targetMolecule.charAt(0);
    for (int i = 1; i < targetMolecule.length(); i++) {
      char currChar = targetMolecule.charAt(i);
      if (Character.isUpperCase(prevChar) && Character.isUpperCase(currChar)) {
        count++;
      } else if (Character.isUpperCase(currChar) && Character.isLowerCase(prevChar)) {
        count++;
      }
    }
    int numElements = count + 1; // add 1 to account for last element
    return numElements - numRn - numAr - 2 * numY - 1;
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
