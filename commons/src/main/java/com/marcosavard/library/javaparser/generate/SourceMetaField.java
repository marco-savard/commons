package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaField;
import com.marcosavard.commons.lang.reflect.meta.MetaPackage;
import com.marcosavard.commons.lang.reflect.meta.annotations.Component;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;

import java.util.ArrayList;
import java.util.List;

public class SourceMetaField extends MetaField {

    private VariableDeclarator variable;

    private EnumConstantDeclaration literal;

    private final String name;

    private Type type;

    public SourceMetaField(SourceMetaClass declaringClass, VariableDeclarator variable) {
        super(declaringClass);
        this.variable = variable;
        name = variable.getName().asString();
        type = variable.getType();
    }

    public SourceMetaField(SourceMetaClass declaringClass, EnumConstantDeclaration literal) {
        super(declaringClass);
        this.literal = literal;
        this.name = literal.getNameAsString();
    }

    @Override
    public String getDescription() {
        AnnotationExpr annotation = findAnnotationByName(Description.class.getSimpleName());
        String description = "";

        if (annotation instanceof SingleMemberAnnotationExpr sma) {
            description = (annotation == null) ? "" : sma.getMemberValue().toString();
        }

        description = StringUtil.unquote(description).toString();
        return description;
    }

    private AnnotationExpr findAnnotationByName(String givenName) {
        Node parent = (variable == null) ? null : variable.getParentNode().orElse(null);
        List<Node> nodes = (parent != null) ? parent.getChildNodes() : new ArrayList<>();
        AnnotationExpr foundAnnotation = null;

        for (Node node : nodes) {
            String name = null;

            if (node instanceof AnnotationExpr annotation) {
                name = annotation.getName().asString();

                if (givenName.equals(name)) {
                    foundAnnotation = annotation;
                    break;
                }
            }
        }

        return foundAnnotation;
    }

    @Override
    public String getInitialValue() {
        return null;
    }

    @Override
    public MetaClass getItemType() {
        MetaClass itemType = null;
        List<Node> nodes = type.getChildNodes();

        for (Node node : nodes) {
            if (node instanceof ClassOrInterfaceType claz) {
                SourceMetaPackage mp = (SourceMetaPackage)declaringClass.getPackage();
                itemType = SourceMetaClass.of(mp, claz);
            }
        }

        return itemType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getOtherModifiers() {
        List<String> modifiers = new ArrayList<>();
        return modifiers;
    }

    @Override
    public MetaClass getType() {
        String simpleName = getSimpleName(type);
        String qualifiedName = findQualifiedName(simpleName);
        int idx = qualifiedName.lastIndexOf('.');
        String packageName = (idx == -1) ? "" : qualifiedName.substring(0, idx);
        SourceMetaPackage declaringClassMp = (SourceMetaPackage)declaringClass.getPackage();
        CompilationUnit cu = declaringClassMp.getCompilationUnit();
        SourceMetaPackage mp = SourceMetaPackage.of(cu, packageName);
        MetaClass type = mp.findClassByName(simpleName);
        type = (type != null) ? type : SourceMetaClass.of(mp, this.type);
        return type;
    }

    private String getSimpleName(Type type) {
        String simpleName = null;

        if (type instanceof ClassOrInterfaceType claz) {
            simpleName = claz.getName().asString();
        } else if (type instanceof PrimitiveType primitive) {
            simpleName = primitive.asString();
        }

        return simpleName;
    }

    private String findQualifiedName(String simpleName) {
        SourceMetaPackage mp = (SourceMetaPackage)declaringClass.getPackage();
        CompilationUnit cu = mp.getCompilationUnit();
        NodeList<ImportDeclaration> imports = cu.getImports();
        String qualifiedName = "";

        MetaClass mc = mp.findClassByName(simpleName);

        if (mc != null) {
            qualifiedName = mc.getQualifiedName();
        } else {
            for (ImportDeclaration id : imports) {
                String imported = id.getName().asString();
                int idx = imported.lastIndexOf('.');
                String basename = imported.substring(idx+1);

                if (simpleName.equals(basename)) {
                    qualifiedName = imported;
                    break;
                }
            }
        }

        return qualifiedName;
    }

    @Override
    public List<String> getVisibilityModifiers() {
        List<String> modifiers = new ArrayList<>();
        return modifiers;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    @Override
    public boolean isOptional() {
        String name = null;

        if (type.isClassOrInterfaceType()) {
            ClassOrInterfaceType claz = type.asClassOrInterfaceType();
            name = claz.getName().asString();

            String nameWithScope = claz.getNameWithScope();
            SimpleName simpleName = claz.getName();
            String id1 = simpleName.getIdentifier();
            String id2 = simpleName.getId();

            String x = null;

           // ResolvedType rt = claz.resolve();
        }

        boolean optional = "Optional".equals(name);
        return optional;
    }

    @Override
    public boolean isProtected() {
        return false;
    }

    @Override
    public boolean isPublic() {
        boolean isPublic = false;

        if (literal != null) {
            isPublic = isPublicLiteral(literal);
        }

        return isPublic;
    }

    private boolean isPublicLiteral(EnumConstantDeclaration literal) {
        List<Node> nodes = literal.getChildNodes();
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public boolean isComponent() {
        AnnotationExpr annotation = findAnnotationByName(Component.class.getSimpleName());
        return (annotation != null);
    }
}
