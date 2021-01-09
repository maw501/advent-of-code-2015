package main.java.days;

import com.github.cliftonlabs.json_simple.*;

import java.io.FileReader;
import java.io.IOException;

public class Day12 {
  private static final int day = 12;

  public static void main(String[] args) throws IOException, JsonException {
    try (FileReader fileReader = new FileReader(("src/main/resources/day12.json"))) {

      JsonArray deserialize = (JsonArray) Jsoner.deserialize(fileReader);
      System.out.println(deserialize);
    }
  }
}
