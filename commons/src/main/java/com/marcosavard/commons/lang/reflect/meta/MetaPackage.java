package com.marcosavard.commons.lang.reflect.meta;

import java.util.ArrayList;
import java.util.List;

public abstract class MetaPackage {
    private List<MetaClass> classes = new ArrayList<>();

    public void addClass(MetaClass mc) {
        classes.add(mc);
    }


    @Override
    public boolean equals(Object other) {
        boolean equal = false;

        if (other instanceof MetaPackage) {
            MetaPackage that = (MetaPackage)other;
            equal = getName().equals(that.getName());
        }

        return equal;
    }

    public List<MetaClass> getClasses() {
        return classes;
    }

    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }



}
