package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.lang.reflect.meta.annotations.Readonly;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class MetaField {
    public static MetaField of(Member member) {
        return new ReflectiveMetaField(member);
    }

    public abstract String getInitialValue();

    public abstract MetaClass getItemType();

    public abstract String getName();

    public abstract List<String> getOtherModifiers();

    public abstract MetaClass getType();

    public boolean isConstant() {
        return isStatic() && isFinal();
    }

    public abstract boolean isFinal();

    public abstract boolean isOptional();

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




    private static class ReflectiveMetaField extends MetaField {
        private final Member member;

        public ReflectiveMetaField(Member member) {
            this.member = member;
        }

        @Override
        public String getInitialValue() {
            String initialValue = null;
            MetaClass type = getType();
            boolean collection = type.isCollection();

            if (collection) {
                initialValue = "new ArrayList<>()";
            } else {
                if (isConstant()) {
                    initialValue = getInitialValueOfVariable(member);
                }
            }

            return initialValue;
        }

        protected String getInitialValueOfVariable(Member member) {
            String initialValue = null;

            if (member instanceof Field field) {
                try {
                    Class<?> claz = member.getDeclaringClass();
                    Object instance = instantiate(claz);
                    Object value = field.get(instance);

                    if (value != null) {
                        initialValue = toString(value);
                    }

                } catch (NoSuchMethodException
                         | InstantiationException
                         | IllegalAccessException
                         | InvocationTargetException e) {
                    initialValue = null;
                }
            }

            return initialValue;
        }

        public Object instantiate(Class<?> claz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            Constructor<?> constr = claz.getConstructor(new Class[] {});
            Object instance = constr.newInstance(new Object[] {});
            return instance;
        }

        private String toString(Object value) {
            String str;

            if (value instanceof String) {
                str = "\"" + value + "\"";
            } else if (value instanceof Enum e) {
                str = e.getDeclaringClass().getSimpleName() + "." + e.name();
            } else {
                str = Objects.toString(value);
            }

            return str;
        }




        @Override
        public MetaClass getItemType() {
            Class<?> itemType = Object.class;

            if (member instanceof Field field) {
                Type type = field.getGenericType();

                if (type instanceof ParameterizedType pt) {
                    Type[] types = pt.getActualTypeArguments();
                    if (types[0] instanceof Class) {
                        itemType = (Class<?>) types[0];
                    }
                }
            }

            return MetaClass.of(itemType);
        }

        @Override
        public String getName() {
            return member.getName();
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
            Class type = null;

            if (member instanceof Field field) {
                type = field.getType();
            } else if (member instanceof DynamicPackage.Reference reference) {
                Field opposite = reference.getOppositeField();
                type = opposite.getDeclaringClass();
            }

            return (type == null) ? null : MetaClass.of(type);
        }

        @Override
        public boolean isFinal() {
            return Modifier.isFinal(member.getModifiers());
        }

        @Override
        public boolean isOptional() {
            return Optional.class.isAssignableFrom(getType().getClaz());
        }

        @Override
        public boolean isPublic() {
            return Modifier.isPublic(member.getModifiers());
        }

        @Override
        public boolean isReadOnly() {
            boolean readOnly = false;

            if (member instanceof Field field) {
                Class claz = field.getDeclaringClass();
                boolean immutable = MetaClass.of(claz).isImmutable();
                boolean isFinal = isFinal();
                readOnly = immutable || isFinal || field.getAnnotation(Readonly.class) != null;
            }

            return readOnly;
        }

        @Override
        public boolean isStatic() {
            return Modifier.isStatic(member.getModifiers());
        }
    }
}
