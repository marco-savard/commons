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
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class SourceMetaClass extends MetaClass {

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

        if (type instanceof ClassOrInterfaceType) {
            ClassOrInterfaceType claz = (ClassOrInterfaceType)type;
            typeName = claz.getName().asString();
        } else if (type instanceof PrimitiveType) {
            PrimitiveType primitiveType = (PrimitiveType)type;
            typeName = primitiveType.asString();
        }

        return typeName;
    }

    public SourceMetaClass(SourceMetaPackage mp, CompilationUnit cu, TypeDeclaration typeDeclaration) {
        super(mp);
        this.typeDeclaration = typeDeclaration;
        this.simpleName = typeDeclaration.getName().asString();

        String packageName = getFullyQualifiedName(cu, typeDeclaration);
        //String packageName = (String)typeDeclaration.getFullyQualifiedName().orElse(null);
        int idx = lastIndexOf(packageName, '.', 2);
        this.packageName = packageName.substring(0, idx);
        this.qualifiedName = this.packageName + "." + this.simpleName;
        mp.addClass(this.simpleName, this);

        if (typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration)typeDeclaration;
            createClassFields(claz);
        } else if (typeDeclaration instanceof EnumDeclaration) {
            EnumDeclaration enumDeclaration = (EnumDeclaration)typeDeclaration;
            createClassFields(enumDeclaration);
        }
    }

    private String getFullyQualifiedName(CompilationUnit cu, TypeDeclaration typeDeclaration) {
        PackageDeclaration packageDecl = cu.getPackageDeclaration().orElse(null);
        String packageName = packageDecl.getNameAsString();
        String qualifiedName = packageName + "." + typeDeclaration.getName(); //true ?
        return qualifiedName;
    }

    public SourceMetaClass(SourceMetaPackage mp, Type type) {
        super(mp);
        this.type = type;
        String simpleName = null;
        String qualifiedName = null;

        if (type instanceof ClassOrInterfaceType) {
            ClassOrInterfaceType claz = (ClassOrInterfaceType)type;
            simpleName = claz.getName().asString();
        } else if (type instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType)type;
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
    public String getDescription() {
        String descriptionName = Description.class.getSimpleName();
        List<Node> nodes = typeDeclaration.getChildNodes();
        String description = "";

        for (Node node : nodes) {
            if (node instanceof SingleMemberAnnotationExpr) {
                SingleMemberAnnotationExpr annotation = (SingleMemberAnnotationExpr)node;
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
        MetaClass sc = getSuperClass();
        MetaField[] inherited = (sc != null) ? sc.getFields() : new MetaField[] {};
        MetaField[] declaredFields = getDeclaredFields();
        return ArrayUtil.concat(inherited, declaredFields);
    }

    @Override
    public String getName() {
        return simpleName;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public MetaClass getSuperClass() {
        MetaClass superClass = null;

        if (this.typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration)this.typeDeclaration;
            SourceMetaPackage mp = (SourceMetaPackage)this.getPackage();
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

        if (this.typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration)this.typeDeclaration;
            NodeList<ClassOrInterfaceType> extendedTypes = claz.getExtendedTypes();
            hasSuperClass = ! extendedTypes.isEmpty();
        }

        return hasSuperClass;
    }

    @Override
    public boolean isAbstract() {
        boolean isAbstract = false;

        if (this.typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration)this.typeDeclaration;
            isAbstract = claz.isAbstract();
        }

        return isAbstract;
    }

    @Override
    public boolean isCollection() {
        boolean collection = false;

        if (this.type instanceof ClassOrInterfaceType) {
            ClassOrInterfaceType claz = (ClassOrInterfaceType)this.type;
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
        return (type == null) ? false : (type instanceof PrimitiveType);
    }

    @Override
    public boolean isPublic() {
        boolean isPublic = true;

        if (this.typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration)this.typeDeclaration;
            isPublic = claz.isPublic();
        } else if (this.typeDeclaration instanceof EnumDeclaration) {
            EnumDeclaration enu = (EnumDeclaration)this.typeDeclaration;
            isPublic = enu.isPublic();
        }

        return isPublic;
    }

    private void createClassFields(ClassOrInterfaceDeclaration claz) {
        List<FieldDeclaration> fields = claz.getFields();
        for (FieldDeclaration field : fields) {
            List<VariableDeclarator> variables = field.getVariables();

            for (VariableDeclarator variable : variables) {
                MetaField mf = new SourceMetaField(this, variable);
                metaFields.add(mf);
            }
        }
    }

    private void createClassFields(EnumDeclaration enumDeclaration) {
        NodeList<EnumConstantDeclaration> entries = enumDeclaration.getEntries();

        for (EnumConstantDeclaration entry : entries) {
            MetaField mf = new SourceMetaField(this, entry);
            metaFields.add(mf);
        }
    }

    private int lastIndexOf(String str, char ch, int position) {
        int idx = str.length();

        for (int i=0; i<=position; i++) {
            idx = str.lastIndexOf(ch, idx - 1);
        }

        return idx;
    }
}
