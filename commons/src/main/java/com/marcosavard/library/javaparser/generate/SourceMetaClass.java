package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
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

        if (typeDeclaration != null) {
            List<FieldDeclaration> fields = typeDeclaration.getFields();
            for (FieldDeclaration field : fields) {
                List<VariableDeclarator> variables = field.getVariables();

                for (VariableDeclarator variable : variables) {
                    MetaField mf = new SourceMetaField(compilationUnit, variable);
                    metaFields.add(mf);
                }
            }
        } else if (type != null) {
            List<Node> nodes = type.getChildNodes();
        }

        MetaField[] array = metaFields.toArray(new MetaField[0]);
        return array;
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
        //TODO : Collection.class.isAssignableFrom(claz);
        return collection;
    }


    @Override
    public boolean isEnum() {
        return false; //type.isEnumDeclaration();
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
        return true; //type.isPublic();
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
