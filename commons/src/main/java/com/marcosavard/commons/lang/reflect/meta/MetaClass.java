package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.lang.reflect.meta.annotations.Immutable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
        return Arrays.stream(getDeclaredFields()).filter(f -> f.isConstant()).collect(Collectors.toList());
    }

    public List<MetaField> getAllVariables() {
        return Arrays.stream(getFields()).filter(f -> ! f.isConstant()).collect(Collectors.toList());
    }

    public List<MetaField> getVariables() {
        return Arrays.stream(getDeclaredFields()).filter(f -> ! f.isConstant()).collect(Collectors.toList());
    }

    public abstract boolean hasSuperClass();

    private static class ReflectiveMetaClass extends MetaClass {
        private final Class<?> claz;

        public static MetaClass of(Class claz) {
            String packageName = claz.getPackageName();
            MetaPackage mp = MetaModel.getInstance().findPackageByName(packageName);
            return new ReflectiveMetaClass(mp, claz);
        }

        private ReflectiveMetaClass(MetaPackage mp, Class claz) {
            super(mp);
            this.claz = claz;
            addDeclaredFields();
        }

        public void addDeclaredFields() {
            Field[] fields = claz.getDeclaredFields();

            for (int i=0; i< fields.length; i++) {
                metaFields.add(MetaField.of(fields[i]));
             }
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
            Class<?> superclass = claz.getSuperclass();
            superclass = Object.class.equals(superclass) ? null : superclass;
            return (superclass == null) ? null : ReflectiveMetaClass.of(superclass);
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
