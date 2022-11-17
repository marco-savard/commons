package com.marcosavard.commons.lang.reflect.meta;

import java.util.List;

public class MetaReference extends MetaField {
    private MetaClass declaringClass;
    private MetaField oppositeField;

    private String name;

    public MetaReference(MetaClass declaringClass, MetaField oppositeField, String fieldName) {
        super(declaringClass);
        this.oppositeField = oppositeField;
        this.name = fieldName;
    }

    @Override
    public String getInitialValue() {
        return null;
    }

    @Override
    public MetaClass getItemType() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<String> getOtherModifiers() {
        return null;
    }

    @Override
    public MetaClass getType() {
        return oppositeField.getDeclaringClass();
    }

    @Override
    public List<String> getVisibilityModifiers() {
        return null;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    @Override
    public boolean isOptional() {
        return false;
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
    public MetaClass getDeclaringClass() {
        return this.declaringClass;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isComponent() {
        return false;
    }
}
