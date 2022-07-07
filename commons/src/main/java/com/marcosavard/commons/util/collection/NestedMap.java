package com.marcosavard.commons.util.collection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class NestedMap extends LinkedHashMap<String, Object> {

  public NestedMap() {}

  public NestedMap(Map<String, Object> map) {
    Set<String> keys = map.keySet();

    for (String key : keys) {
      put(key, map.get(key));
    }
  }

  @Override
  public Object put(String key, Object value) {
    int idx = key.indexOf('/');

    if (idx == -1) {
      super.put(key, value);
    } else {
      String root = key.substring(0, idx);
      String path = key.substring(idx + 1);
      putRoot(root, path, value);
    }

    return value;
  }

  private void putRoot(String root, String path, Object value) {
    int begin = root.indexOf('[');
    int end = (begin > 0) ? root.indexOf(']', begin) : -1;

    if (begin == -1) {
      putNestedMapInNode(root, path, value);
    } else {
      String index = root.substring(begin + 1, end);
      root = root.substring(0, begin);
      putNestedMapListInNode(root, index, path, value);
    }
  }

  private void putNestedMapInNode(String root, String path, Object value) {
    NestedMap subMap = (NestedMap) super.get(root);

    if (subMap == null) {
      subMap = new NestedMap();
      super.put(root, subMap);
    }

    subMap.put(path, value);
  }

  private void putNestedMapListInNode(String root, String index, String path, Object value) {
    int idx = Integer.parseInt(index);
    List<NestedMap> subMapList = (List) super.get(root);

    if (subMapList == null) {
      subMapList = new ArrayList<>();
      super.put(root, subMapList);
    }

    ensureSize(subMapList, idx + 1);
    NestedMap subMap = subMapList.get(idx);

    if (subMap == null) {
      subMap = new NestedMap();
      subMapList.set(idx, subMap);
    }

    subMap.put(path, value);
  }

  @Override
  public Object get(Object key) {
    Object value = null;
    String path = (key == null) ? "" : key.toString();
    int idx = path.indexOf('/');

    if (idx == -1) {
      value = super.get(key);
    } else {
      String root = path.substring(0, idx);
      path = path.substring(idx + 1);
      value = getRoot(root, path);
    }

    return value;
  }


  private Object getRoot(String root, String path) {
    Object value = null;
    int begin = root.indexOf('[');
    int end = (begin > 0) ? root.indexOf(']', begin) : -1;

    if (begin == -1) {
      value = getNestedMapInNode(root, path);
    } else {
      String index = root.substring(begin + 1, end);
      root = root.substring(0, begin);
      value = getNestedMapListInNode(root, index, path);
    }

    return value;
  }

  private Object getNestedMapInNode(String root, String path) {
    Object value = null;
    NestedMap subMap = (NestedMap) super.get(root);
    value = (subMap == null) ? null : subMap.get(path);
    return value;
  }

  private Object getNestedMapListInNode(String root, String index, String path) {
    Object value = null;
    int idx = Integer.parseInt(index);
    List<NestedMap> subMapList = (List) super.get(root);
    ensureSize(subMapList, idx + 1);
    NestedMap subMap = (subMapList == null) ? null : subMapList.get(idx);
    value = (subMap == null) ? null : subMap.get(path);

    return value;
  }

  private void ensureSize(List<NestedMap> list, int size) {
    if (list != null) {
      while (list.size() < size) {
        list.add(null);
      }
    }
  }
}
