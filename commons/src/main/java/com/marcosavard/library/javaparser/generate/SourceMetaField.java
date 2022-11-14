package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedType;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaField;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;

import java.util.ArrayList;
import java.util.List;

public class SourceMetaField extends MetaField {
    private CompilationUnit compilationUnit;

    private final VariableDeclarator variable;
    private final String name;

    private final Type type;

    public SourceMetaField(CompilationUnit cu, VariableDeclarator variable) {
        this.compilationUnit = cu;
        this.variable = variable;
        name = variable.getName().asString();
        type = variable.getType();
    }

    @Override
    public MetaClass getDeclaringClass() {
        return null;
    }

    @Override
    public String getDescription() {
        String description = Description.class.getSimpleName();
        Node parent = variable.getParentNode().orElse(null);
        List<Node> nodes = parent.getChildNodes();
        String value = "";

        for (Node node : nodes) {
            if (node instanceof SingleMemberAnnotationExpr annotation) {
                String name = annotation.getName().asString();

                if (description.equals(name)) {
                    value = annotation.getMemberValue().toString();
                    int i=9;
                }
            }
        }

        return value;
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
                itemType = SourceMetaClass.of(compilationUnit, claz);
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
        return SourceMetaClass.of(compilationUnit, type);
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
        return false;
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
