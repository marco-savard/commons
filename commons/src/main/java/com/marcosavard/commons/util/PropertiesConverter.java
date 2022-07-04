package com.marcosavard.commons.util;

import java.util.*;

public class PropertiesConverter {
    private final Properties properties;

    public static PropertiesConverter of(Properties properties) {
        return new PropertiesConverter(properties);
    }

    public PropertiesConverter(Properties properties) {
        this.properties = properties;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        Map<String, List<String>> arrayByName = new HashMap<>();

        for (String name : properties.stringPropertyNames()) {
            int startIdx = name.indexOf("[");
            int endIdx = name.indexOf("]");

            if ((startIdx == -1) && (endIdx == -1)) {
                String value = properties.getProperty(name);
                map.put(name, value);
            } else if ((startIdx > 0) && (endIdx > startIdx)) {
                String arrayName = name.substring(0, startIdx);
                String idxText = name.substring(startIdx+1, endIdx);
                int idx = Integer.parseInt(idxText);
                List<String> list = arrayByName.get(arrayName);

                if (list == null) {
                    list = new ArrayList<>();
                    arrayByName.put(arrayName, list);
                }

                String value = properties.getProperty(name);
                setListValue(list, idx, value);
            }
        }

        for (String name : arrayByName.keySet()) {
            map.put(name, arrayByName.get(name));
        }

        return map;
    }

    private void setListValue(List<String> list, int idx, String value) {
        int count = list.size();
        List nullList = new ArrayList();

        for (int i=count; i<=idx; i++) {
            nullList.add(null);
        }

        list.addAll(nullList);
        list.set(idx, value);
    }
}
