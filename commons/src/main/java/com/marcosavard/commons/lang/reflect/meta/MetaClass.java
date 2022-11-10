package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.lang.reflect.meta.annotations.Immutable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class MetaClass {

    public static MetaClass of(Class claz) {
        return new ReflectiveMetaClass(claz);
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;

        if (other instanceof MetaClass that) {
            equal = getName().equals(that.getName());
        }

        return equal;
    }

    public abstract MetaField[] getDeclaredFields();

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

    public abstract MetaPackage getPackage();

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

    public abstract boolean isCollection() ;

    public abstract boolean isEnum();

    public abstract boolean isImmutable();

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
        return Arrays.stream(getDeclaredFields()).filter(f -> f.isConstant()).toList();
    }

    public List<MetaField> getAllVariables() {
        return Arrays.stream(getFields()).filter(f -> ! f.isConstant()).toList();
    }

    public List<MetaField> getVariables() {
        return Arrays.stream(getDeclaredFields()).filter(f -> ! f.isConstant()).toList();
    }

    public abstract boolean hasSuperClass();

    private static class ReflectiveMetaClass extends MetaClass {
        private final Class<?> claz;

        public ReflectiveMetaClass(Class claz) {
            this.claz = claz;
        }

        @Override
        public MetaField[] getDeclaredFields() {
            Field[] fields = claz.getDeclaredFields();
            MetaField[] metaFields = new MetaField[fields.length];

            for (int i=0; i< fields.length; i++) {
                metaFields[i] = MetaField.of(fields[i]);
             }

            return metaFields;
        }

        @Override
        public MetaField[] getFields() {
            Field[] fields = claz.getFields();
            MetaField[] metaFields = new MetaField[fields.length];

            for (int i=0; i< fields.length; i++) {
                metaFields[i] = MetaField.of(fields[i]);
            }

            return metaFields;
        }

        @Override
        public String getDescription() {
            String name = claz.getSimpleName();
            Description description = claz.getAnnotation(Description.class);
            String desc = (description == null) ? "" : description.value();
            return name + " " + desc;
        }

        @Override
        public String getName() {
            return claz.getName();
        }

        @Override
        public MetaPackage getPackage() {
            return MetaPackage.of(claz.getPackage());
        }

        @Override
        public String getPackageName() {
            String modelPackage = claz.getPackageName();
            int idx = modelPackage.lastIndexOf('.');
            return modelPackage.substring(0, idx);
        }

        @Override
        public String getQualifiedName() {
            return claz.getPackageName() + "." + getSimpleName();
        }

        @Override
        public String getSimpleName() {
            return claz.getSimpleName();
        }

        @Override
        public MetaClass getSuperClass() {
            return new ReflectiveMetaClass(claz.getSuperclass());
        }

        @Override
        public boolean hasSuperClass() {
            Class<?> superclass = claz.getSuperclass();
            return (superclass != null) && (!Object.class.equals(superclass));
        }

        @Override
        public boolean isAbstract() {
            return Modifier.isAbstract(claz.getModifiers());
        }

        @Override
        public boolean isCollection() {
            return Collection.class.isAssignableFrom(claz);
        }

        @Override
        public boolean isEnum() {
            return claz.isEnum();
        }

        @Override
        public boolean isImmutable() {
            return claz.getAnnotation(Immutable.class) != null;
        }

        @Override
        public boolean isPrimitive() {
            boolean primitive = boolean.class.equals(claz);
            primitive = primitive || byte.class.equals(claz);
            primitive = primitive || char.class.equals(claz);
            primitive = primitive || short.class.equals(claz);
            primitive = primitive || int.class.equals(claz);
            primitive = primitive || long.class.equals(claz);
            primitive = primitive || float.class.equals(claz);
            primitive = primitive || double.class.equals(claz);
            return primitive;
        }

        @Override
        public boolean isPublic() {
            return Modifier.isPublic(claz.getModifiers());
        }



    }
}
