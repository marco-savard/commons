package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.lang.reflect.meta.annotations.Immutable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

public class ReflectiveMetaClass extends MetaClass {
    protected final Class<?> claz;

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
    public boolean isBoolean() {
        boolean bool = boolean.class.equals(claz);
        return bool;
    }

    @Override
    public boolean isCharacter() {
        boolean character = char.class.equals(claz);
        return character;
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
    public boolean isFloatingNumber() {
        boolean floatingNumber = float.class.equals(claz);
        floatingNumber = floatingNumber || double.class.equals(claz);
        return floatingNumber;
    }

    @Override
    public boolean isImmutable() {
        return claz.getAnnotation(Immutable.class) != null;
    }

    @Override
    public boolean isNumber() {
        boolean number = byte.class.equals(claz);
        number = number || short.class.equals(claz);
        number = number || int.class.equals(claz);
        number = number || long.class.equals(claz);
        number = number || float.class.equals(claz);
        number = number || double.class.equals(claz);
        return number;
    }

    @Override
    public boolean isPrimitive() {
        boolean primitive = isNumber();
        primitive = primitive || isBoolean();
        primitive = primitive || isCharacter();
        return primitive;
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(claz.getModifiers());
    }

}
