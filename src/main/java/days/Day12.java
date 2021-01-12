package main.java.days;

import com.github.cliftonlabs.json_simple.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

public class Day12 {
  public static void main(String[] args) throws IOException, JsonException {
    try (FileReader fileReader = new FileReader(("src/main/resources" + "/day12" + ".json"))) {

      JsonArray deserialize = (JsonArray) Jsoner.deserialize(fileReader);
      System.out.println(deserialize);
      System.out.println("--------------------------");
      Day12 day12 = new Day12();
      long answer = day12.iterateOverJsonArray(deserialize);
      System.out.println(answer);
    }
  }

  private long iterateOverJsonArray(JsonArray array) {
    long arrayValue = 0;
    for (Object obj : array) {
      if (obj instanceof JsonObject) {
        arrayValue += iterateOverJsonObject((JsonObject) obj);
      } else if (obj instanceof JsonArray) {
        arrayValue += iterateOverJsonArray((JsonArray) obj);
      } else if (obj instanceof String) {
        continue;
      } else {
        arrayValue += ((BigDecimal) obj).longValue();
      }
    }
    return arrayValue;
  }

  private long iterateOverJsonObject(JsonObject object) {
    long objectValue = 0;
    Set<String> keys = object.keySet();
    for (String key : keys) {
      if (object.get(key) instanceof JsonArray) {
        objectValue += iterateOverJsonArray((JsonArray) object.get(key));
      } else if (object.get(key) instanceof JsonObject) {
        objectValue += iterateOverJsonObject((JsonObject) object.get(key));
      } else if (object.get(key) instanceof String) {
        String stringValue = (String) object.get(key);
        if (stringValue.equals("red")){
          return 0;
        }
        //continue;
      } else {
        objectValue += ((BigDecimal) object.get(key)).longValue();
      }
    }
    return objectValue;
  }
}
