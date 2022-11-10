package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaField;
import com.marcosavard.commons.lang.reflect.meta.MetaPackage;

public class ParsedMetaClass extends MetaClass {
    private final TypeDeclaration type;

    public ParsedMetaClass(TypeDeclaration type) {
        this.type = type;
    }

    @Override
    public MetaField[] getDeclaredFields() {
        return new MetaField[0];
    }

    @Override
    public MetaField[] getFields() {
        return new MetaField[0];
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getName() {
        return (String)type.getFullyQualifiedName().orElse(null);
    }

    @Override
    public MetaPackage getPackage() {
        return new ParsedMetaPackage(this);
    }

    @Override
    public String getSimpleName() {
        return type.getName().asString();
    }

    @Override
    public MetaClass getSuperClass() {
        return null;
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
    public String getQualifiedName() {
        String qualifiedName = (String)type.getFullyQualifiedName().orElse(null);
        int idx = qualifiedName.lastIndexOf('.');
        qualifiedName = qualifiedName.substring(0, idx);
        idx = qualifiedName.lastIndexOf('.');
        qualifiedName = qualifiedName.substring(0, idx);
        return qualifiedName;
    }

    @Override
    public boolean isAbstract() {
        return type.hasModifier(Modifier.Keyword.ABSTRACT);
    }

    @Override
    public boolean isCollection() {
        boolean collection = false;
        //TODO : Collection.class.isAssignableFrom(claz);
        return collection;
    }


    @Override
    public boolean isEnum() {
        return type.isEnumDeclaration();
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isPublic() {
        return type.isPublic();
    }

    @Override
    public boolean hasSuperClass() {
        return false;
    }
}
