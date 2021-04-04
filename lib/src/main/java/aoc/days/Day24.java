package aoc.days;

import aoc.utils.ReadTextFile;
import org.apache.commons.math3.util.Combinations;

import java.util.ArrayList;
import java.util.HashSet;

public class Day24 {
  private static final int day = 24;
  private final ArrayList<Integer> weights = new ArrayList<>();

  public static void main(String[] args) {
    ArrayList<String> data = ReadTextFile.readFile(day);
    Day24 day24 = new Day24();
    day24.parseWeights(data);
    System.out.println("Day " + day + " star 1: " + day24.starOne());
    System.out.println("Day " + day + " star 2: " + day24.starTwo());
  }

  private long starOne() {
    ArrayList<int[]> solutions = balanceSleigh(false);
    return minQuantumEntanglement(solutions);
  }

  private long starTwo() {
    ArrayList<int[]> solutions = balanceSleigh(true);
    return minQuantumEntanglement(solutions);
  }

  private long minQuantumEntanglement(ArrayList<int[]> solutions) {
    long currentMin = Long.MAX_VALUE;
    for (int[] arr : solutions) {
      long product = 1;
      for (int x : arr) {
        product = product * x;
      }
      if (product < currentMin) {
        currentMin = product;
      }
    }
    return currentMin;
  }

  private ArrayList<int[]> balanceSleigh(boolean useTrunk) {
    boolean found_solution_of_size_k = false;
    boolean can_solve_partition;
    int targetWeight;
    if (useTrunk) {
      targetWeight = sum(weights) / 4;
    } else {
      targetWeight = sum(weights) / 3;
    }
    ArrayList<int[]> solutions = new ArrayList<>();

    for (int k = 2; k < weights.size() - 1; k++) {
      solutions = new ArrayList<>();
      Combinations subsets_size_k = new Combinations(weights.size(), k);
      int[] subset;
      for (int[] subset_ids : subsets_size_k) {
        subset = new int[subset_ids.length];
        for (int i = 0; i < subset_ids.length; i++) {
          subset[i] = weights.get(subset_ids[i]);
        }
        if (sum(subset) == targetWeight) {
          if (useTrunk) {
            can_solve_partition = partition3(removeSubsetFromWeights(subset_ids));
          } else {
            can_solve_partition = partition(removeSubsetFromWeights(subset_ids));
          }
          if (can_solve_partition) {
            found_solution_of_size_k = true;
            solutions.add(subset);
          }
        }
      }
      if (found_solution_of_size_k) break;
    }
    return solutions;
  }

  private boolean partition(ArrayList<Integer> array) {
    int target = sum(array);
    return canSolveSubsetSum(array, array.size() - 1, target / 2);
  }

  private boolean partition3(ArrayList<Integer> array) {
    if (array.size() < 3) return false;
    return (sum(array) % 3 == 0)
        && canSolveSubsetSum(
            array, array.size() - 1, sum(array) / 3, sum(array) / 3, sum(array) / 3);
  }

  private boolean canSolveSubsetSum(ArrayList<Integer> array, int nextIndex, int remainingSum) {
    if (remainingSum == 0) return true;
    if (nextIndex < 0 || remainingSum < 0) return false;
    boolean canSolveByIncluding =
        canSolveSubsetSum(array, nextIndex - 1, remainingSum - array.get(nextIndex));
    if (canSolveByIncluding) return true;
    return canSolveSubsetSum(array, nextIndex - 1, remainingSum);
  }

  private boolean canSolveSubsetSum(
      ArrayList<Integer> array,
      int nextIndex,
      int bucketOneSum,
      int bucketTwoSum,
      int bucketThreeSum) {
    if (bucketOneSum == 0 && bucketTwoSum == 0 && bucketThreeSum == 0) return true;
    if (nextIndex < 0) return false;

    boolean solvedInBucketOne = false;
    if (bucketOneSum - array.get(nextIndex) >= 0) {
      solvedInBucketOne =
          canSolveSubsetSum(
              array,
              nextIndex - 1,
              bucketOneSum - array.get(nextIndex),
              bucketTwoSum,
              bucketThreeSum);
    }
    boolean solvedInBucketTwo = false;
    if (!solvedInBucketOne && bucketTwoSum - array.get(nextIndex) >= 0) {
      solvedInBucketTwo =
          canSolveSubsetSum(
              array,
              nextIndex - 1,
              bucketOneSum,
              bucketTwoSum - array.get(nextIndex),
              bucketThreeSum);
    }
    boolean solvedInBucketThree = false;
    if ((!solvedInBucketOne && !solvedInBucketTwo) && bucketThreeSum - array.get(nextIndex) >= 0) {
      solvedInBucketThree =
          canSolveSubsetSum(
              array,
              nextIndex - 1,
              bucketOneSum,
              bucketTwoSum,
              bucketThreeSum - array.get(nextIndex));
    }
    return solvedInBucketOne || solvedInBucketTwo || solvedInBucketThree;
  }

  private ArrayList<Integer> removeSubsetFromWeights(int[] subset_ids) {
    ArrayList<Integer> setToCheck = new ArrayList<>();
    HashSet<Integer> subset_ids_set = new HashSet<>();
    for (int i : subset_ids) subset_ids_set.add(i);
    for (int i = 0; i < weights.size(); i++) {
      if (!subset_ids_set.contains(i)) setToCheck.add(weights.get(i));
    }
    return setToCheck;
  }

  private int sum(int[] array) {
    int s = 0;
    for (int x : array) s += x;
    return s;
  }

  private int sum(ArrayList<Integer> array) {
    int s = 0;
    for (int x : array) s += x;
    return s;
  }

  private void parseWeights(ArrayList<String> data) {
    for (String s : data) {
      weights.add(Integer.parseInt(s));
    }
  }
}
