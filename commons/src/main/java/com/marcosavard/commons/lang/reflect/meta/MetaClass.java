package com.marcosavard.commons.lang.reflect.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MetaClass {
    private MetaPackage ownerPackage;

    protected List<MetaField> metaFields = new ArrayList<>();

    protected MetaClass(MetaPackage ownerPackage) {
        this.ownerPackage = ownerPackage;

        if (ownerPackage != null) {
            ownerPackage.addClass(this);
        }
    }

    public static MetaClass of(Class claz) {
        return ReflectiveMetaClass.of(claz);
    }

    public void addReference(MetaReference mr) {
        metaFields.add(0, mr);
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;

        if (other instanceof MetaClass) {
            MetaClass that = (MetaClass)other;
            equal = getName().equals(that.getName());
        }

        return equal;
    }

    public MetaField[] getDeclaredFields() {
        MetaField[] array = metaFields.toArray(new MetaField[0]);
        return array;
    }

    public abstract MetaField[] getFields();

    public abstract String getDescription();

    public abstract String getName();

    public List<String> getOtherModifiers() {
        List<String> modifiers = new ArrayList<>();

        if (isAbstract()) {
            modifiers.add("abstract");
        }

        return modifiers;
    }

    public MetaPackage getPackage() {
        return ownerPackage;
    }

    public abstract String getPackageName();

    public abstract String getQualifiedName();

    public abstract String getSimpleName();

    public abstract MetaClass getSuperClass();

    public List<String> getVisibilityModifiers() {
        List<String> modifiers = new ArrayList<>();

        if (isPublic()) {
            modifiers.add("public");
        }

        return modifiers;
    }

    public abstract boolean isAbstract();

    public abstract boolean isBoolean();

    public abstract boolean isCharacter();

    public abstract boolean isCollection() ;

    public abstract boolean isEnum();

    public abstract boolean isFloatingNumber();

    public abstract boolean isImmutable();

    public abstract boolean isNumber();

    public abstract boolean isPrimitive();

    public abstract boolean isPublic();

    @Override
    public String toString() {
        return getQualifiedName();
    }


    //to be removed
    public Class getClaz() {
        return ((ReflectiveMetaClass)this).claz;
    }

    public List<MetaField> getConstants() {
        return Arrays.stream(getDeclaredFields()).filter(f -> f.isConstant()).collect(Collectors.toList());
    }

    public String getDefaultValue() {
        String defaultValue;

        if (isFloatingNumber()) {
            defaultValue = "0.0";
        } if (isNumber()) {
            defaultValue = "0";
        } else if (isBoolean()) {
            defaultValue = "false";
        } else if (isCharacter()) {
            defaultValue = "'\0''";
        } else  {
            defaultValue = "null";
        }

        return defaultValue;
    }

    public List<MetaField> getAllVariables() {
        return Arrays.stream(getFields()).filter(f -> ! f.isConstant()).collect(Collectors.toList());
    }

    public List<MetaField> getVariables() {
        return Arrays.stream(getDeclaredFields()).filter(f -> ! f.isConstant()).collect(Collectors.toList());
    }

    public abstract boolean hasSuperClass();


}
