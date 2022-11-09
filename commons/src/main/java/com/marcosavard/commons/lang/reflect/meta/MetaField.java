package com.marcosavard.commons.lang.reflect.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class MetaField {
    public static MetaField of(Field field) {
        return new ReflectiveMetaField(field);
    }

    public abstract MetaClass getItemType();

    public abstract String getName();

    public abstract List<String> getOtherModifiers();

    public abstract MetaClass getType();

    public boolean isConstant() {
        return isStatic() && isFinal();
    }

    public abstract boolean isFinal();

    public abstract boolean isPublic();

    public abstract boolean isStatic();

    @Override
    public String toString() {
        return getName();
    }

    //to delete
    public Field getField() {
        return ((ReflectiveMetaField)this).field;
    }



    private static class ReflectiveMetaField extends MetaField {
        private final Field field;

        public ReflectiveMetaField(Field field) {
            this.field = field;
        }

        @Override
        public MetaClass getItemType() {
            Class<?> itemType = Object.class;
            Type type = field.getGenericType();

            if (type instanceof ParameterizedType pt) {
                Type[] types = pt.getActualTypeArguments();
                if (types[0] instanceof Class) {
                    itemType = (Class<?>) types[0];
                }
            }

            return MetaClass.of(itemType);
        }

        @Override
        public String getName() {
            return field.getName();
        }

        @Override
        public List<String> getOtherModifiers() {
            List<String> modifiers = new ArrayList<>();

            if (isStatic()) {
                modifiers.add("static");
            }

            if (isFinal()) {
                modifiers.add("final");
            }

            return modifiers;
        }

        @Override
        public MetaClass getType() {
            return MetaClass.of(field.getType());
        }

        @Override
        public boolean isFinal() {
            return Modifier.isFinal(field.getModifiers());
        }

        @Override
        public boolean isPublic() {
            return Modifier.isPublic(field.getModifiers());
        }

        @Override
        public boolean isStatic() {
            return Modifier.isStatic(field.getModifiers());
        }
    }
}
