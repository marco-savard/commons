package com.marcosavard.commons.res;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertyLoader {
    private final Class<?> resourceClass;

    private PropertyLoader(Class<?> resourceClass) {
        this.resourceClass = resourceClass;
    }

    public static PropertyLoader of(Class<?> resourceClass) {
        return new PropertyLoader(resourceClass);
    }

    public Properties load(String resourceName) {
        return load(resourceName, false);
    }

    public Properties loadNested(String resourceName) {
        return load(resourceName, true);
    }

    private Properties load(String resourceName, boolean nested) {
        Package pack = resourceClass.getPackage();
        String path = pack.getName().replace('.', File.separatorChar);
        String qualifiedName = path + File.separatorChar + resourceName;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();

        try(InputStream resourceStream = loader.getResourceAsStream(qualifiedName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        if (nested) {
            nestProperties(properties);
        }

        return properties;
    }

    private void nestProperties(Properties properties) {
        List<String> itemsToRemove = new ArrayList<>();
        Map<String, List<String>> arrayByName = new HashMap<>();

        for (String name : properties.stringPropertyNames()) {
            int startIdx = name.indexOf("[");
            int endIdx = name.indexOf("]");

            if ((startIdx > 0) && (endIdx > startIdx)) {
                itemsToRemove.add(name);
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

        for (String name : itemsToRemove) {
            properties.remove(name);
        }

        for (String name : arrayByName.keySet()) {
            properties.put(name, arrayByName.get(name));
        }
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
