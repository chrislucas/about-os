package sample;

import java.util.AbstractMap;
import java.util.Map;

public class MapEntryStudies {

  /*
     https://www.baeldung.com/java-map-new-entry
  */
  private static void test() {
    Map.Entry<String, String> s = new AbstractMap.SimpleEntry<>("", "");
  }

  public static void main(String[] args) {
    test();
  }
}
