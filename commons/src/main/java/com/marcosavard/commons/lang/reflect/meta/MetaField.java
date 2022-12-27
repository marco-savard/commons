package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.lang.reflect.meta.annotations.Component;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.lang.reflect.meta.annotations.Readonly;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class MetaField {
    protected MetaClass declaringClass;

    private String name;

    public static MetaField of(Member member) {
        return new ReflectiveMetaField(member);
    }

    protected MetaField(MetaClass declaringClass, String fieldName) {
        this.declaringClass = declaringClass;
        this.name = fieldName;
    }

    public MetaClass getDeclaringClass() {
        return declaringClass;
    }

    public abstract String getDescription();

    public abstract String getInitialValue();

    public abstract MetaClass getItemType();

    public String getName() {
        return name;
    }

    public abstract List<String> getOtherModifiers();

    public abstract MetaClass getType();

    public abstract List<String> getVisibilityModifiers();

    public abstract boolean isComponent();

    public boolean isConstant() {
        return isStatic() && isFinal();
    }

    public abstract boolean isFinal();

    public abstract boolean isOptional();

    public abstract boolean isProtected();

    public abstract boolean isPublic();

    public abstract boolean isReadOnly();

    public abstract boolean isStatic();

    @Override
    public String toString() {
        return getName();
    }

    //to delete
    public Member getField() {
        return ((ReflectiveMetaField)this).member;
    }

    public String getTypeName() {
        String typeName = getType().getSimpleName();
        String actualType = getType().isCollection() || isOptional() ? typeName + "<" + getItemType().getSimpleName() + ">": typeName;
        return actualType;
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;

        if (other instanceof MetaField) {
            MetaField that = (MetaField)other;
            equal = this.getDeclaringClass().equals(that.getDeclaringClass());
            equal = equal && this.getName().equals(that.getName());
        }

        return equal;
    }

    public String getDefaultValue() {
        String defaultValue = getInitialValue();

        if (defaultValue == null) {
            MetaClass type = getType();
            defaultValue = type.getDefaultValue();
        }
        return defaultValue;
    }

}
