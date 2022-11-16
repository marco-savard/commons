package com.marcosavard.commons.lang.reflect.meta;

import java.util.HashMap;
import java.util.Map;

public class MetaModel {
    private Map<String, MetaPackage> packageByName = new HashMap<>();
    private static MetaModel instance = null;

    public static MetaModel getInstance() {
        if (instance == null) {
            instance = new MetaModel();
        }

        return instance;
    }


    public void addPackage(String name, MetaPackage mp) {
        packageByName.put(name, mp);
    }

    public MetaPackage findPackageByName(String name) {
        return packageByName.get(name);
    }
}
