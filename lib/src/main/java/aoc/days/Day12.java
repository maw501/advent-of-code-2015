package aoc.days;

import com.github.cliftonlabs.json_simple.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

public class Day12 {
  private static final int day = 12;

  public static void main(String[] args) {
    try (FileReader fileReader = new FileReader("src/main/resources/day12.json")) {
      JsonArray deserialize = (JsonArray) Jsoner.deserialize(fileReader);
      Day12 day12 = new Day12();
      long starOneAnswerObj = day12.calculateJsonValue(deserialize, false);
      long starTwoAnswerObj = day12.calculateJsonValue(deserialize, true);
      System.out.println("Day " + day + " star 1: " + starOneAnswerObj);
      System.out.println("Day " + day + " star 2: " + starTwoAnswerObj);
    } catch (IOException ex) {
      System.out.println("No file found");
    } catch (JsonException ex) {
      System.out.println("Couldn't parse Json");
    }
  }

  private long calculateJsonValue(Object obj, boolean ignoreRed) {
    long objectValue = 0;
    if (obj instanceof JsonArray) {
      for (Object o : (JsonArray) obj) {
        objectValue += calculateJsonValue(o, ignoreRed);
      }
    } else if (obj instanceof JsonObject) {
      Set<String> keys = ((JsonObject) obj).keySet();
      for (String key : keys) {
        Object value = ((JsonObject) obj).get(key);
        if (value.equals("red") && ignoreRed) return 0;
        objectValue += calculateJsonValue(value, ignoreRed);
      }
    } else if (obj instanceof BigDecimal) {
      objectValue += ((BigDecimal) obj).longValue();
    }
    return objectValue;
  }
}
