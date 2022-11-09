package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;

public class ParsedMetaClass extends MetaClass {
    private final TypeDeclaration type;

    public ParsedMetaClass(TypeDeclaration type) {
        this.type = type;
    }

    @Override
    public String getSimpleName() {
        return type.getName().asString();
    }

    @Override
    public String getPackageName() {
        String qualifiedName = (String)type.getFullyQualifiedName().orElse(null);
        int idx = qualifiedName.lastIndexOf('.');
        String packageName = qualifiedName.substring(0, idx);
        idx = packageName.lastIndexOf('.');
        packageName = packageName.substring(0, idx);
        idx = packageName.lastIndexOf('.');
        packageName = packageName.substring(0, idx);
        return packageName;
    }

    @Override
    public boolean isEnum() {
        return type.isEnumDeclaration();
    }
}
