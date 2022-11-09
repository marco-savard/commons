package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaPackage;

public class ParsedMetaPackage extends MetaPackage {
    private String name;

    public ParsedMetaPackage(MetaClass mc) {
        String qualifiedName = mc.getQualifiedName();
        int idx = qualifiedName.lastIndexOf('.');
        name = qualifiedName.substring(0, idx);
    }

    @Override
    public String getName() {
        return name;
    }
}
