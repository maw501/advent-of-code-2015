package main.java.utils;

import java.util.ArrayList;

public class Permutations {
  /**
   * Build all "permutations" of an ArrayList of Strings.
   *
   * @param arrayToPermute e.g [A, B, C, D, E]
   * @param currArray starting array e.g. [[A, B, C]]
   * @param cyclic if false, permutations. If true this will return "permutations" per a round table
   *     (i.e. ways of seating ppl) & capturing the symmetry of which side someone sits i.e. table:
   *     CABD is the same as ABDC as the nodes are connected the same way.
   * @return e.g. [[A, B, C], [A, C, B], [B, C, A], [B, A, C], [C, A, B], [C, B, A]] for an input
   *     arrayToPermute of [A, B, C] with cyclic = false. If cyclic = true will return [[A, B, C]]
   *     (or equiv) as there is only one way to seat 3 ppl at a round table if we don't care about
   *     which side someone sits on, just who they are next to.
   */
  public static ArrayList<ArrayList<String>> buildPermutations(
      ArrayList<String> arrayToPermute, ArrayList<ArrayList<String>> currArray, boolean cyclic) {
    int startIndex = 0;
    long finalLength;
    int currIndex = currArray.get(0).size();
    int currLen = 0;
    ArrayList<ArrayList<String>> finalArray = new ArrayList<>();

    finalLength = calculateFinalLength(cyclic, arrayToPermute.size());
    if (cyclic) startIndex = 1;

    while (currLen < finalLength) {
      finalArray = new ArrayList<>();
      String currString = arrayToPermute.get(currIndex);
      for (ArrayList<String> element : currArray) {
        for (int i = startIndex; i <= currIndex; i++) {
          ArrayList<String> newElement = new ArrayList<>(element);
          newElement.add(i, currString);
          finalArray.add(newElement);
        }
      }
      currLen = finalArray.size();
      currIndex++;
      currArray = new ArrayList<>(finalArray);
    }
    return finalArray;
  }

  private static long calculateFinalLength(boolean cyclic, int n) {
    if (!cyclic) return Factorial.factorial(n);
    return Factorial.factorial(n - 1) / 2;
  }
}
