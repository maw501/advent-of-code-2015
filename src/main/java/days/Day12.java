package main.java.days;

import com.github.cliftonlabs.json_simple.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Day12 {
  private static final int day = 12;
  private long currentTotal = 0;

  public static void main(String[] args) throws IOException, JsonException {
    try (FileReader fileReader = new FileReader(("src/main/resources" + "/day12_test" + ".json"))) {

      JsonArray deserialize = (JsonArray) Jsoner.deserialize(fileReader);
      System.out.println(deserialize);
      System.out.println("--------------------------");
      Day12 day12 = new Day12();
      day12.iterateOverJsonArray(deserialize);
    }
  }

  private void iterateOverJsonArray(JsonArray array) {
    for (Object j : array) {
      if (j instanceof JsonObject) {
        iterateOverJsonObject((JsonObject) j);
      } else if (j instanceof JsonArray) {
        JsonArray k = (JsonArray) j;
        System.out.println(k);
      } else {
        System.out.println(j);
      }
    }
  }

  private void iterateOverJsonObject(JsonObject array) {
    Set keys = array.keySet();
    for (Object key : keys) {
      System.out.println("key: " + key + " value: " + array.get(key));
    }
  }
}
