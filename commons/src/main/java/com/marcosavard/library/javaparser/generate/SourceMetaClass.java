package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.metamodel.ClassOrInterfaceTypeMetaModel;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaField;
import com.marcosavard.commons.lang.reflect.meta.MetaPackage;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.meta.classes.MetaModel;

import java.util.ArrayList;
import java.util.List;

public class SourceMetaClass extends MetaClass {
    private final SourceMetaPackage ownerPackage;

    private TypeDeclaration typeDeclaration;
    private Type type;

    private String simpleName, qualifiedName;

    private String packageName;

    public static SourceMetaClass of(SourceMetaPackage mp, Type type) {
        String simpleName = getTypeName(type);
        SourceMetaClass mc = mp.findClassByName(simpleName);
        mc = (mc != null) ? mc : new SourceMetaClass(mp, type);
        return mc;
    }

    private static String getTypeName(Type type) {
        String typeName = "";

        if (type.isClassOrInterfaceType()) {
            ClassOrInterfaceType claz = type.asClassOrInterfaceType();
            typeName = claz.getName().asString();
        } else if (type.isPrimitiveType()) {
            PrimitiveType primitiveType = type.asPrimitiveType();
            typeName = primitiveType.asString();
        }

        return typeName;
    }

    public SourceMetaClass(SourceMetaPackage mp, TypeDeclaration typeDeclaration) {
        this.ownerPackage = mp;
        this.typeDeclaration = typeDeclaration;
        this.simpleName = typeDeclaration.getName().asString();
        this.qualifiedName = (String)typeDeclaration.getFullyQualifiedName().orElse(null);
        int idx = lastIndexOf(qualifiedName, '.', 2);
        packageName = qualifiedName.substring(0, idx);
        mp.addClass(this.simpleName, this);

        List<Node> nodes = typeDeclaration.getChildNodes();

        for (Node node : nodes) {
            List<Node> childNodes = node.getChildNodes();

        }
    }

    public SourceMetaClass(SourceMetaPackage mp, Type type) {
        this.ownerPackage = mp;
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

        List<ImportDeclaration> imports = mp.getCompilationUnit().getImports();
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

        if (type instanceof ClassOrInterfaceType claz) {
            //TODO
            ClassOrInterfaceTypeMetaModel model = claz.getMetaModel();
            //model.g
        }

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
                MetaField mf = new SourceMetaField(this, variable);
                metaFields.add(mf);
            }
        }

        return metaFields;
    }

    private List<MetaField> getEnumLiterals(EnumDeclaration enumDeclaration) {
        List<MetaField> metaFields = new ArrayList<>();
        NodeList<EnumConstantDeclaration> entries = enumDeclaration.getEntries();

        for (EnumConstantDeclaration entry : entries) {
            MetaField mf = new SourceMetaField(this, entry);
            metaFields.add(mf);
        }

        return metaFields;
    }

    @Override
    public String getDescription() {
        String descriptionName = Description.class.getSimpleName();
        List<Node> nodes = typeDeclaration.getChildNodes();
        String description = "";

        for (Node node : nodes) {
            if (node instanceof SingleMemberAnnotationExpr annotation) {
                String name = annotation.getName().asString();

                if (descriptionName.equals(name)) {
                    description = annotation.getMemberValue().toString();
                    description = StringUtil.unquote(description).toString();
                }
            }
        }

        return description;
    }

    @Override
    public MetaField[] getFields() {
        return new MetaField[0];
    }

    @Override
    public String getName() {
        return simpleName;
    }

    @Override
    public SourceMetaPackage getPackage() {
        return ownerPackage;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public MetaClass getSuperClass() {
        MetaClass superClass = null;

        if (this.typeDeclaration instanceof ClassOrInterfaceDeclaration claz) {
            SourceMetaPackage mp = this.getPackage();
            NodeList<ClassOrInterfaceType> extendedTypes = claz.getExtendedTypes();
            ClassOrInterfaceType type = extendedTypes.isEmpty() ? null : extendedTypes.get(0);
            superClass = (type == null) ? null : SourceMetaClass.of(mp, type);
        }

        return superClass;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public boolean hasSuperClass() {
        boolean hasSuperClass = false;

        if (this.typeDeclaration instanceof ClassOrInterfaceDeclaration claz) {
            NodeList<ClassOrInterfaceType> extendedTypes = claz.getExtendedTypes();
            hasSuperClass = ! extendedTypes.isEmpty();
        }

        return hasSuperClass;
    }

    @Override
    public boolean isAbstract() {
        boolean isAbstract = false;

        if (this.typeDeclaration instanceof ClassOrInterfaceDeclaration claz) {
            isAbstract = claz.isAbstract();
        }

        return isAbstract;
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

    private int lastIndexOf(String str, char ch, int position) {
        int idx = str.length();

        for (int i=0; i<=position; i++) {
            idx = str.lastIndexOf(ch, idx - 1);
        }

        return idx;
    }
}
