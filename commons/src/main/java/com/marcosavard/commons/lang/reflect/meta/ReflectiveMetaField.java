package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.lang.reflect.meta.annotations.Component;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.lang.reflect.meta.annotations.Readonly;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ReflectiveMetaField extends MetaField {
    protected final Member member;

    public ReflectiveMetaField(Member member) {
        super(MetaClass.of(member.getDeclaringClass()), member.getName());
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

        if (member instanceof Field) {
            try {
                Field field = (Field)member;
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
        } else if (value instanceof Enum) {
            Enum e = (Enum)value;
            str = e.getDeclaringClass().getSimpleName() + "." + e.name();
        } else {
            str = Objects.toString(value);
        }

        return str;
    }

    @Override
    public MetaClass getDeclaringClass() {
        return MetaClass.of(member.getDeclaringClass());
    }

    @Override
    public String getDescription() {
        String desc = "";

        if (member instanceof Field) {
            Field field = (Field)member;
            Description description = field.getAnnotation(Description.class);
            desc = (description == null) ? "" : description.value();
        }

        return desc;
    }

    @Override
    public MetaClass getItemType() {
        Class<?> itemType = Object.class;

        if (member instanceof Field) {
            Field field = (Field)member;
            Type type = field.getGenericType();

            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType)type;
                Type[] types = pt.getActualTypeArguments();
                if (types[0] instanceof Class) {
                    itemType = (Class<?>) types[0];
                }
            }
        }

        return MetaClass.of(itemType);
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

        if (member instanceof Field) {
            Field field = (Field)member;
            type = field.getType();
        } else if (member instanceof DynamicPackage.Reference) {
            DynamicPackage.Reference reference = (DynamicPackage.Reference)member;
            Field opposite = reference.getOppositeField();
            type = opposite.getDeclaringClass();
        }

        return (type == null) ? null : MetaClass.of(type);
    }

    @Override
    public List<String> getVisibilityModifiers() {
        List<String> modifiers = new ArrayList<>();

        if (isPublic()) {
            modifiers.add("public");
        }

        if (isProtected()) {
            modifiers.add("protected");
        }

        return modifiers;
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
    public boolean isProtected() {
        return Modifier.isProtected(member.getModifiers());
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(member.getModifiers());
    }

    @Override
    public boolean isReadOnly() {
        boolean readOnly = false;

        if (member instanceof Field) {
            Field field = (Field)member;
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

    @Override
    public boolean isComponent() {
        boolean component = false;

        if (member instanceof Field) {
            Field field = (Field)member;
            component = field.getAnnotation(Component.class) != null;
        }

        return component;
    }
}
