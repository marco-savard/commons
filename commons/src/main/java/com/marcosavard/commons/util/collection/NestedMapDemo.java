package com.marcosavard.commons.util.collection;

import java.util.LinkedHashMap;
import java.util.Map;

public class NestedMapDemo {

  public static void main(String[] args) {
    createNestedMap();
    convertMapToNestedMap();
  }

  private static void convertMapToNestedMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("Number", 1.0);
    map.put("String", "A");

    Map<String, Object> submap = new LinkedHashMap<>();
    submap.put("Number", 2.0);
    submap.put("String", "B");
    map.put("Map", submap);

    Map<String, Object> nestMap = new NestedMap(map);
    System.out.println(nestMap);
  }

  private static void createNestedMap() {
    Map<String, Object> map = new NestedMap();
    map.put("Canada/Quebec/City[0]", "Quebec City");
    map.put("Canada/Quebec/City[1]", "Montreal");
    map.put("Canada/Ontario/City[0]", "Toronto");
    map.put("Canada/Ontario/City[1]", "Ottawa");
    System.out.println(map);

    String key = "Canada/Quebec/City[1]";
    Object city = map.get(key);
    System.out.println(key + " : " + city);
  }
}
