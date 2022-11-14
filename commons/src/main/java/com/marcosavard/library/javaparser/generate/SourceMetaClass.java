package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaField;
import com.marcosavard.commons.lang.reflect.meta.MetaPackage;

import java.util.ArrayList;
import java.util.List;

public class SourceMetaClass extends MetaClass {
    private CompilationUnit compilationUnit;

    private TypeDeclaration typeDeclaration;
    private Type type;

    private String simpleName, qualifiedName;

    private String packageName;

    public static SourceMetaClass of(CompilationUnit cu, Type type) {
        return new SourceMetaClass(cu, type);
    }

    public SourceMetaClass(CompilationUnit cu, TypeDeclaration typeDeclaration) {
        this.compilationUnit = cu;
        List<Node> nodes = typeDeclaration.getChildNodes();

        for (Node node : nodes) {
            List<Node> childNodes = node.getChildNodes();

        }

        this.typeDeclaration = typeDeclaration;
        this.simpleName = typeDeclaration.getName().asString();

        String qualifiedName = (String)typeDeclaration.getFullyQualifiedName().orElse(null);
        int idx = lastIndexOf(qualifiedName, '.', 2);
        packageName = qualifiedName.substring(0, idx);

    }

    public SourceMetaClass(CompilationUnit cu, Type type) {
        this.compilationUnit = cu;
        this.type = type;
        String simpleName = null;
        String qualifiedName = null;

        if (type.isClassOrInterfaceType()) {
            ClassOrInterfaceType claz = type.asClassOrInterfaceType();
            simpleName = claz.getName().asString();
        } else if (type.isPrimitiveType()) {
            PrimitiveType pt = type.asPrimitiveType();
            simpleName = pt.asString();
        }

        List<ImportDeclaration> imports = cu.getImports();
        for (ImportDeclaration id : imports) {
            String imported = id.getName().asString();
            int idx = imported.lastIndexOf('.');
            String basename = imported.substring(idx+1);

            if (simpleName.equals(basename)) {
                qualifiedName = imported;
            }
        }

        this.simpleName = simpleName;
        this.qualifiedName = qualifiedName;
    }

    @Override
    public MetaField[] getDeclaredFields() {
        List<MetaField> metaFields = new ArrayList<>();

        if (typeDeclaration instanceof ClassOrInterfaceDeclaration claz) {
            metaFields = getClassFields(claz);
        } else if (typeDeclaration instanceof EnumDeclaration enumDeclaration) {
            metaFields = getEnumLiterals(enumDeclaration);
        }

        MetaField[] array = metaFields.toArray(new MetaField[0]);
        return array;
    }

    private List<MetaField> getClassFields(ClassOrInterfaceDeclaration claz) {
        List<MetaField> metaFields = new ArrayList<>();

        List<FieldDeclaration> fields = claz.getFields();
        for (FieldDeclaration field : fields) {
            List<VariableDeclarator> variables = field.getVariables();

            for (VariableDeclarator variable : variables) {
                MetaField mf = new SourceMetaField(compilationUnit, variable);
                metaFields.add(mf);
            }
        }

        return metaFields;
    }

    private List<MetaField> getEnumLiterals(EnumDeclaration enumDeclaration) {
        List<MetaField> metaFields = new ArrayList<>();
        NodeList<EnumConstantDeclaration> entries = enumDeclaration.getEntries();

        for (EnumConstantDeclaration entry : entries) {
            MetaField mf = new SourceMetaField(compilationUnit, entry);
            metaFields.add(mf);
        }

        return metaFields;
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
        return simpleName;
    }

    @Override
    public MetaPackage getPackage() {
        return new SourceMetaPackage(this);
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public MetaClass getSuperClass() {
        return null;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getQualifiedName() {
        /*
        String qualifiedName = (String)type.getFullyQualifiedName().orElse(null);
        int idx = qualifiedName.lastIndexOf('.');
        qualifiedName = qualifiedName.substring(0, idx);
        idx = qualifiedName.lastIndexOf('.');
        qualifiedName = qualifiedName.substring(0, idx);
        return qualifiedName;

         */

        return qualifiedName;
    }

    @Override
    public boolean isAbstract() {
        return false; //type.hasModifier(Modifier.Keyword.ABSTRACT);
    }

    @Override
    public boolean isCollection() {
        boolean collection = false;

        if (this.type instanceof ClassOrInterfaceType claz) {
            collection = isCollectionClass(claz);
        }

        return collection;
    }

    //TODO : Collection.class.isAssignableFrom(claz);
    private boolean isCollectionClass(ClassOrInterfaceType claz) {
        boolean collection = false;

        if ("java.util.List".equals(qualifiedName)) {
            collection = true;
        }

       //String qualifiedName = claz.getFullyQualifiedName();
       // NodeList<ClassOrInterfaceType> extendedTypes = claz.getExtendedTypes();
     //   NodeList<ClassOrInterfaceType> implementedTypes = claz.getImplementedTypes();

        return collection;
    }


    @Override
    public boolean isEnum() {
        boolean enumeration = (this.typeDeclaration instanceof EnumDeclaration);
        return enumeration;
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return type.isPrimitiveType();
    }

    @Override
    public boolean isPublic() {
        boolean isPublic = true;

        if (this.typeDeclaration instanceof ClassOrInterfaceDeclaration claz) {
            isPublic = claz.isPublic();
        } else if (this.typeDeclaration instanceof EnumDeclaration enu) {
            isPublic = enu.isPublic();
        }

        return isPublic;
    }

    @Override
    public boolean hasSuperClass() {
        return false;
    }

    private int lastIndexOf(String str, char ch, int position) {
        int idx = str.length();

        for (int i=0; i<=position; i++) {
            idx = str.lastIndexOf(ch, idx - 1);
        }

        return idx;
    }
}
