package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedType;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaField;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;

import java.util.ArrayList;
import java.util.List;

public class SourceMetaField extends MetaField {
    private SourceMetaClass ownerClass;

    private VariableDeclarator variable;

    private EnumConstantDeclaration literal;

    private final String name;

    private Type type;

    public SourceMetaField(SourceMetaClass mc, VariableDeclarator variable) {
        this.ownerClass = mc;
        this.variable = variable;
        name = variable.getName().asString();
        type = variable.getType();
    }

    public SourceMetaField(SourceMetaClass mc, EnumConstantDeclaration literal) {
        this.ownerClass = mc;
        this.literal = literal;
        this.name = literal.getNameAsString();
    }

    @Override
    public MetaClass getDeclaringClass() {
        return this.ownerClass;
    }

    @Override
    public String getDescription() {
        String descriptionName = Description.class.getSimpleName();
        Node parent = variable.getParentNode().orElse(null);
        List<Node> nodes = parent.getChildNodes();
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
    public String getInitialValue() {
        return null;
    }

    @Override
    public MetaClass getItemType() {
        MetaClass itemType = null;
        List<Node> nodes = type.getChildNodes();

        for (Node node : nodes) {
            if (node instanceof ClassOrInterfaceType claz) {
                itemType = SourceMetaClass.of(ownerClass.getPackage(), claz);
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
        CompilationUnit cu = ownerClass.getPackage().getCompilationUnit();
        String simpleName = getSimpleName(type);
        String qualifiedName = findQualifiedName(cu, simpleName);
        int idx = qualifiedName.lastIndexOf('.');
        String packageName = (idx == -1) ? "" : qualifiedName.substring(0, idx-1);
        SourceMetaPackage mp = SourceMetaPackage.of(cu, packageName);
        return SourceMetaClass.of(mp, type);
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

    private String findQualifiedName(CompilationUnit cu, String simpleName) {
        NodeList<ImportDeclaration> imports = cu.getImports();
        String qualifiedName = "";

        for (ImportDeclaration id : imports) {
            String imported = id.getName().asString();
            int idx = imported.lastIndexOf('.');
            String basename = imported.substring(idx+1);

            if (simpleName.equals(basename)) {
                qualifiedName = imported;
                break;
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
        return false;
    }
}
