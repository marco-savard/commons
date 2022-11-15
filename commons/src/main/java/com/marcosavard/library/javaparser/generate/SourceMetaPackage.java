package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaPackage;

import java.util.HashMap;
import java.util.Map;

public class SourceMetaPackage extends MetaPackage {
    private CompilationUnit compilationUnit;
    private String name;

    private Map<String, SourceMetaClass> ownedMetaClasses = new HashMap<>();

    public static SourceMetaPackage of(CompilationUnit cu, String packageName) {
        return new SourceMetaPackage(cu, packageName);
    }

    private SourceMetaPackage(CompilationUnit cu, String packageName) {
        compilationUnit = cu;
        name = packageName;
    }

    public SourceMetaPackage(CompilationUnit cu, PackageDeclaration pack) {
        compilationUnit = cu;
        String packageName = pack.getName().asString();
        int idx = packageName.lastIndexOf('.');
        name = (idx == -1) ? "" : packageName.substring(0, idx);
    }

    public SourceMetaPackage(MetaClass mc) {
        String qualifiedName = mc.getQualifiedName();
        int idx = (qualifiedName == null) ? -1 : qualifiedName.lastIndexOf('.');
        name = (idx == -1) ? "" : qualifiedName.substring(0, idx);
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }


    @Override
    public String getName() {
        return name;
    }


    public SourceMetaClass findClassByName(String simpleName) {
        return ownedMetaClasses.get(simpleName);
    }

    public void addClass(String simpleName, SourceMetaClass mc) {
        ownedMetaClasses.put(simpleName, mc);
    }
}
