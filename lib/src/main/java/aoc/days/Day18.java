package aoc.days;

import aoc.utils.ReadTextFile;

import java.util.ArrayList;
import java.util.Arrays;

public class Day18 {
  private static final int day = 18;
  private final int dim = 100;
  private final int timeSteps = 100;

  public static void main(String[] args) {
    ArrayList<String> data = ReadTextFile.readFile(day);
    Day18 day18 = new Day18();
    int[][] gridStarOne = day18.buildGridFromInputData(data, false);
    int[][] gridStarTwo = day18.buildGridFromInputData(data, true);
    System.out.println("Day " + day + " star 1: " + day18.starOne(gridStarOne, false));
    System.out.println("Day " + day + " star 2: " + day18.starOne(gridStarTwo, true));
  }

  private int[][] buildGridFromInputData(ArrayList<String> data, boolean fixCorners) {
    int[][] grid = new int[dim][dim];
    for (int i = 0; i < dim; i++) {
      char[] line = data.get(i).toCharArray();
      for (int j = 0; j < dim; j++) {
        if (checkIfLightIsACorner(i, j) && fixCorners) {
          grid[i][j] = 1;
          continue;
        }
        char c = line[j];
        if (c == '#') grid[i][j] = 1;
        if (c == '.') grid[i][j] = 0;
      }
    }
    return grid;
  }

  private int starOne(int[][] grid, boolean fixCorners) {
    for (int t = 0; t < timeSteps; t++) {
      int[][] nextGrid = new int[dim][dim];
      for (int i = 0; i < dim; i++) {
        for (int j = 0; j < dim; j++) {
          if (checkIfLightIsACorner(i, j) && fixCorners) {
            nextGrid[i][j] = 1;
            continue;
          }
          ArrayList<ArrayList<Integer>> neighbours = getNeighbours(i, j);
          int neighboursValue = getNeighboursValue(grid, neighbours);
          int currentLightValue = grid[i][j];
          if (currentLightValue == 1) {
            if (neighboursValue == 2 || neighboursValue == 3) {
              nextGrid[i][j] = 1;
            } else {
              nextGrid[i][j] = 0;
            }
          }
          if (currentLightValue == 0) {
            if (neighboursValue == 3) {
              nextGrid[i][j] = 1;
            } else {
              nextGrid[i][j] = 0;
            }
          }
        }
      }
      grid = nextGrid;
    }
    return Arrays.stream(grid).flatMapToInt(Arrays::stream).sum();
  }

  private boolean checkIfLightIsACorner(int i, int j) {
    if (i == 0 && j == dim - 1) return true;
    if (i == dim - 1 && j == dim - 1) return true;
    if (i == dim - 1 && j == 0) return true;
    if (i == 0 && j == 0) return true;
    return false;
  }

  private int getNeighboursValue(int[][] grid, ArrayList<ArrayList<Integer>> neighbours) {
    int value = 0;
    for (ArrayList<Integer> neighbour : neighbours) {
      value += getLightValue(grid, neighbour);
    }
    return value;
  }

  private int getLightValue(int[][] grid, ArrayList<Integer> lightLocation) {
    if (lightLocation.get(0) < 0 || lightLocation.get(0) >= dim) return 0;
    if (lightLocation.get(1) < 0 || lightLocation.get(1) >= dim) return 0;
    return grid[lightLocation.get(0)][lightLocation.get(1)];
  }

  private ArrayList<ArrayList<Integer>> getNeighbours(int i, int j) {
    ArrayList<ArrayList<Integer>> out = new ArrayList<>();
    out.add(createNeighbour(i - 1, j - 1));
    out.add(createNeighbour(i - 1, j));
    out.add(createNeighbour(i - 1, j + 1));
    out.add(createNeighbour(i, j - 1));
    out.add(createNeighbour(i, j + 1));
    out.add(createNeighbour(i + 1, j - 1));
    out.add(createNeighbour(i + 1, j));
    out.add(createNeighbour(i + 1, j + 1));
    return out;
  }

  private ArrayList<Integer> createNeighbour(int i, int j) {
    ArrayList<Integer> out = new ArrayList<>();
    out.add(i);
    out.add(j);
    return out;
  }

  private int starTwo() {
    return 1;
  }
}
