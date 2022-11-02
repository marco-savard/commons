package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.lang.reflect.meta.annotations.Component;
import com.marcosavard.commons.lang.reflect.meta.annotations.Immutable;
import com.marcosavard.commons.lang.reflect.meta.annotations.Readonly;

import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.*;

public class DynamicPackage {

    protected final Class[] classes;
    private Map<Class, Reference> referenceByClass = null;

    public DynamicPackage(Class claz) {
        this(new Class[] {claz});
    }

    public DynamicPackage(Class[] classes) {
        this.classes = classes;
    }

    public void buildReferenceByClass(String containerName) {
        referenceByClass = buildReferenceByClass(classes, containerName);
    }

    protected Map<Class, Reference> getReferenceByClass() {
        if (referenceByClass == null) {
            buildReferenceByClass("owner");
        }

        return referenceByClass;
    }

    private Map<Class, Reference> buildReferenceByClass(Class<?>[] classes, String containerName) {
        Map<Class, Reference> referenceByClass = new HashMap<>();

        for (Class claz : classes) {
            Field[] fields = claz.getFields();
            for (Field field : fields) {
                if (isComponent(field)) {
                    Class type = field.getType();
                    Class child = isCollection(type) ? getItemType(field) : type;
                    Reference ref = new Reference(child, field, containerName);
                    referenceByClass.put(child, ref);
                }
            }
        }

        return referenceByClass;
    }

    //
    // get methods
    //
    public List<Class> getClasses() {
        return Arrays.asList(classes);
    }

    public Class<?> getItemType(Field f) {
        Class<?> itemType = Object.class;
        Type type = f.getGenericType();

        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type[] types = pt.getActualTypeArguments();
            if (types[0] instanceof Class) {
                itemType = (Class<?>) types[0];
            }
        }

        return itemType;
    }

    protected List<String> getMemberNames(List<? extends Member> members) {
        List<String> memberNames = new ArrayList<>();

        for (Member member : members) {
            memberNames.add(member.getName());
        }

        return memberNames;
    }

    protected List<Reference> getParentReferences(Class<?> claz) {
        List<Reference> parentReferences = new ArrayList<>();
        Reference reference = getReferenceByClass().get(claz);
        if (reference != null) {
            parentReferences.add(reference);
        }

        return parentReferences;
    }

    protected List<Member> getSuperClassMembers(Class<?> claz, boolean includeParent) {
        List<Member> superClassMembers = new ArrayList<>();
        Class superClass = getSuperclass(claz);
        Reference reference = includeParent && (superClass != null) ? getReferenceByClass().get(superClass) : null;

        if (reference != null) {
            superClassMembers.add(reference);
        }

        List<Member> requiredMembers = (superClass != null) ? getRequiredMembers(superClass, false) : new ArrayList<>();
        superClassMembers.addAll(requiredMembers);
        return superClassMembers;
    }

    protected List<Member> getRequiredMembers(Class<?> claz, boolean declared) {
        boolean immutable = isImmutable(claz);
        List<Member> requiredMembers = new ArrayList<>();
        Field[] fields = declared ? claz.getDeclaredFields() : claz.getFields();
        requiredMembers.addAll(Arrays.stream(fields)
                .filter(f -> (immutable || ! isOptional(f)))
                .filter(f -> ! isStatic(f))
                .filter(f -> !isCollection(f.getType()))
                .toList());
        return requiredMembers;
    }



    public List<List<? extends Member>> findConcreteFieldSignatures(List<Field> signature) {
        List<List<? extends Member>> signatures = new ArrayList<>();
        signatures.add(signature);
        boolean fullyExpanded = false;
        List<List<? extends Member>> concreteSignatures = null;

        while (! fullyExpanded) {
            concreteSignatures = expandConcreteFieldSignatures(signatures);
            fullyExpanded = (concreteSignatures.size() == signatures.size());
            signatures = concreteSignatures;
        }

        return concreteSignatures;
    }


    public List<List<Class>> findConcreteSignatures(List<Class> fieldTypes) {
        List<List<Class>> signatures = new ArrayList<>();
        signatures.add(fieldTypes);
        boolean fullyExpanded = false;
        List<List<Class>> concreteSignatures = null;

        while (! fullyExpanded) {
            concreteSignatures = expandConcreteSignatures(signatures);
            fullyExpanded = (concreteSignatures.size() == signatures.size());
            signatures = concreteSignatures;
        }

        return concreteSignatures;
    }

    private List<List<Class>> expandSignatures(List<List<Class>> signatures) {
        List<List<Class>> expandedSignatures = new ArrayList<>();

        for (List<Class> signature : signatures) {

        }

        return expandedSignatures;
    }

    private List<List<Class>> expandConcreteSignatures(List<List<Class>> signatures) {
        List<List<Class>> addedSignatures = new ArrayList<>();

        for (List<Class> signature : signatures) {
            int idx = indexOfAbstractClass(signature);

            if (idx >= 0) {
                Class abstractClass = signature.get(idx);
                List<Class> subClasses = getSubclasses(abstractClass);

                for (Class subClass : subClasses) {
                    List<Class> addedSignature = new ArrayList<>();
                    addedSignature.addAll(signature.subList(0, idx));
                    addedSignature.add(subClass);
                    addedSignature.addAll(signature.subList(idx + 1, signature.size()));
                    addedSignatures.add(addedSignature);
                }
            } else {
                addedSignatures.add(signature);
            }
        }

        return addedSignatures;
    }

    private List<List<? extends Member>> expandConcreteFieldSignatures(List<List<? extends Member>> signatures) {
        List<List<? extends Member>> addedSignatures = new ArrayList<>();

        for (List<? extends Member> signature : signatures) {
            int idx = indexOfAbstractFieldType(signature);

            if (idx >= 0) {
                Member member = signature.get(idx);
                List<Class> subClasses = getSubclasses(getType(member));

                for (Class subClass : subClasses) {
                    List<Member> addedSignature = new ArrayList<>();
                    addedSignature.addAll(signature.subList(0, idx));
                    String name = member.getName();
                    DynamicField addedField = new DynamicField(member.getDeclaringClass(), subClass, name);
                    addedSignature.add(addedField);
                    addedSignature.addAll(signature.subList(idx + 1, signature.size()));
                    addedSignatures.add(addedSignature);
                }
            } else {
                addedSignatures.add(signature);
            }
        }
        return addedSignatures;
    }

    public List<List<Class>> expandConcreteClasses(List<Class> types) {
        List<List<Class>> signatures = new ArrayList<>();
        signatures.add(types);
        List<List<Class>> concreteClasses = expandSignatures(signatures);
        return concreteClasses;
    }

    //
    // getters
    //

    protected String getInitialValue(Field field) {
        String initialValue = null;
        Class<?> type = field.getType();
        boolean collection = isCollection(type);

        if (collection) {
            initialValue = "new ArrayList<>()";
        } else {
            if (isConstant(field)) {
                initialValue = getInitialValueOfVariable(field);
            }
        }

        return initialValue;
    }

    protected String getInitialValueOfVariable(Field field) {
        String initialValue = null;

        try {
            Class<?> claz = field.getDeclaringClass();
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

    public List<Class> getSubclasses(Class givenClass) {
        List<Class> subClasses = new ArrayList<>();

        for (Class claz : classes) {
            if (!givenClass.equals(claz) && givenClass.isAssignableFrom(claz)) {
                subClasses.add(claz);
            }
        }

        return subClasses;
    }

    protected Class getSuperclass(Class<?> claz) {
        Class superclass = claz.getSuperclass();
        return superclass.equals(Object.class) ? null : superclass;
    }

    protected Class<?> getType(Member member) {
        Class<?> type = null;

        if (member instanceof Field field) {
            type = field.getType();
        } else if (member instanceof DynamicField field) {
            type = field.getType();
        }

        return type;
    }

    public int indexOfAbstractClass(List<Class> classes) {
        int idx = -1;

        for (int i=0; i<classes.size(); i++) {
            Class claz = classes.get(i);
            if (isAbstract(claz)) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    public int indexOfAbstractFieldType(List<? extends Member> members) {
        int idx = -1;

        for (int i=0; i<members.size(); i++) {
            Member member = members.get(i);
            if (isAbstract(getType(member))) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    protected boolean hasSuperClass(Class<?> claz) {
        Class<?> superclass = claz.getSuperclass();
        return (superclass != null) && (!Object.class.equals(superclass));
    }

    protected boolean isAbstract(Class<?> claz) {
        return Modifier.isAbstract(claz.getModifiers());
    }

    protected boolean isAddable(Package pack, Class<?> type) {
        String packageName = type.getPackageName();
        boolean addable = !"java.lang".equals(packageName);
        return addable && !type.getPackage().equals(pack);
    }

    protected boolean isCollection(Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }

    protected boolean isComponent(Field field) {
        return field.getAnnotation(Component.class) != null;
    }

    protected boolean isConstant(Field field) {
        boolean isStatic = Modifier.isStatic(field.getModifiers());
        boolean isFinal = Modifier.isFinal(field.getModifiers());
        return isStatic && isFinal;
    }

    protected boolean isImmutable(Class<?> claz) {
        return claz.getAnnotation(Immutable.class) != null;
    }

    private boolean isNotNull(Member member) {
        boolean notNull = false;

        if (member instanceof Field field) {
            boolean readonly = isReadOnly(field);
            boolean optional = isOptional(field);
            notNull = readonly || (! optional);
        }

        return notNull;
    }

    protected boolean isOptional(Member member) {
        boolean immutable = isImmutable(member.getDeclaringClass());
        boolean optional = false;

        if (member instanceof Field f) {
            optional =  Optional.class.isAssignableFrom(f.getType());
        }

        return optional;
    }

    protected boolean isPrimitive(Class<?> type) {
        boolean primitive = boolean.class.equals(type);
        primitive = primitive || byte.class.equals(type);
        primitive = primitive || char.class.equals(type);
        primitive = primitive || short.class.equals(type);
        primitive = primitive || int.class.equals(type);
        primitive = primitive || long.class.equals(type);
        primitive = primitive || float.class.equals(type);
        primitive = primitive || double.class.equals(type);
        return primitive;
    }

    protected boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    protected boolean isReadOnly(Field field) {
        boolean immutable = isImmutable(field.getDeclaringClass());
        boolean isFinal = isFinal(field);
        boolean readOnly = immutable || isFinal || field.getAnnotation(Readonly.class) != null;
        return readOnly;
    }

    protected boolean isFinal(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }

    protected boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public boolean hasContainer(Class givenClass) {
        boolean hasContainer = false;

        for (Class claz : classes) {
            if (isContainerOf(claz, givenClass)) {
                hasContainer = true;
                break;
            }
        }

        return hasContainer;
    }

    private boolean isContainerOf(Class claz, Class givenClass) {
        boolean containerOf = false;
        Field[] fields = claz.getFields();

        for (Field f : fields) {
            Class type = f.getType();

            if (isComponent(f)) {
                Class fieldType = isCollection(type) ? getItemType(f) : getType(f);

                if (fieldType.isAssignableFrom(givenClass)) {
                    containerOf = true;
                    break;
                }
            }
        }

        return containerOf;
    }

    public List<Class> getTopLevelContainers() {
        List<Class> topLevelContainers = new ArrayList<>();

        for (Class claz : classes) {
            if (!isEnum(claz) && ! hasContainer(claz)) {
                topLevelContainers.add(claz);
            }
        }

        return topLevelContainers;
    }

    public boolean isEnum(Class claz) {
        return Enum.class.isAssignableFrom(claz);
    }


    //
    // inner classes
    //
    private static class DynamicField implements Member {
        private final Class<?> declaringClass;
        private final Class<?> type;
        private final String name;


        public DynamicField(Class<?> declaringClass, Class<?> type, String name) {
            this.declaringClass = declaringClass;
            this.type = type;
            this.name = name;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return declaringClass;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getModifiers() {
            return 0;
        }

        @Override
        public boolean isSynthetic() {
            return false;
        }

        public Class<?> getType() {
            return type;
        }

        @Override
        public String toString() {
            return MessageFormat.format("{0} {1}", type.getSimpleName(), name);
        }
    }

    protected static class Reference implements Member {
        private final Class declaringClass;
        private final Field oppositeField;
        private final String name;

        public Reference(Class declaringClass, Field oppositeField, String name) {
            this.declaringClass = declaringClass;
            this.oppositeField = oppositeField;
            this.name = name;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return declaringClass;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getModifiers() {
            return 0;
        }

        @Override
        public boolean isSynthetic() {
            return false;
        }

        public Field getOppositeField() {
            return oppositeField;
        }
    }
}
