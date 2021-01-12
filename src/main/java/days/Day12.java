package main.java.days;

import com.github.cliftonlabs.json_simple.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

public class Day12 {
  private static final int day = 12;

  public static void main(String[] args) throws IOException, JsonException {
    try (FileReader fileReader = new FileReader(("src/main/resources" + "/day12" + ".json"))) {
      JsonArray deserialize = (JsonArray) Jsoner.deserialize(fileReader);
      System.out.println(deserialize);
      Day12 day12 = new Day12();
      long starOneAnswer = day12.iterateOverJsonArray(deserialize, false);
      long starTwoAnswer = day12.iterateOverJsonArray(deserialize, true);
      System.out.println("Day " + day + " star 1: " + starOneAnswer);
      System.out.println("Day " + day + " star 2: " + starTwoAnswer);
      // Using generic Object
      long starOneAnswerObj = day12.calculateJsonValue(deserialize, false);
      long starTwoAnswerObj = day12.calculateJsonValue(deserialize, true);
      System.out.println("Day " + day + " star 1 with generics: " + starOneAnswerObj);
      System.out.println("Day " + day + " star 2 with generics: " + starTwoAnswerObj);
    }
  }

  private long iterateOverJsonArray(JsonArray array, boolean ignoreRed) {
    long arrayValue = 0;
    for (Object obj : array) {
      if (obj instanceof JsonObject)
        arrayValue += iterateOverJsonObject((JsonObject) obj, ignoreRed);
      if (obj instanceof JsonArray) arrayValue += iterateOverJsonArray((JsonArray) obj, ignoreRed);
      if (obj instanceof BigDecimal) arrayValue += ((BigDecimal) obj).longValue();
    }
    return arrayValue;
  }

  private long iterateOverJsonObject(JsonObject object, boolean ignoreRed) {
    long objectValue = 0;
    Set<String> keys = object.keySet();
    for (String key : keys) {
      if (object.get(key) instanceof JsonArray)
        objectValue += iterateOverJsonArray((JsonArray) object.get(key), ignoreRed);
      if (object.get(key) instanceof JsonObject)
        objectValue += iterateOverJsonObject((JsonObject) object.get(key), ignoreRed);
      if (object.get(key) instanceof String)
        if (object.get(key).equals("red") && ignoreRed) return 0;
      if (object.get(key) instanceof BigDecimal)
        objectValue += ((BigDecimal) object.get(key)).longValue();
    }
    return objectValue;
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
    } else if (obj instanceof String) {
    } else if (obj instanceof BigDecimal) {
      objectValue += ((BigDecimal) obj).longValue();
    }
    return objectValue;
  }
}
