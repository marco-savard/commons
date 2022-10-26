package com.marcosavard.library.javaparser.generate;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DynamicPackage {

    protected final Class[] classes;

    public DynamicPackage(Class[] classes) {
        this.classes = classes;
    }

    //
    // methods
    //
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


    public List<Class> getSubclasses(Class givenClass) {
        List<Class> subClasses = new ArrayList<>();

        for (Class claz : classes) {
            if (claz.getSuperclass().equals(givenClass)) {
                subClasses.add(claz);
            }
        }

        return subClasses;
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

    protected Class<?> getType(Member member) {
        Class<?> type = null;

        if (member instanceof Field field) {
            type = field.getType();
        } else if (member instanceof DynamicField field) {
            type = field.getType();
        }

        return type;
    }

    private boolean isAbstract(Class<?> claz) {
        return Modifier.isAbstract(claz.getModifiers());
    }



    public List<List<Class>> expandConcreteClasses(List<Class> types) {
        List<List<Class>> signatures = new ArrayList<>();
        signatures.add(types);
        List<List<Class>> concreteClasses = expandSignatures(signatures);
        return concreteClasses;
    }

    private List<List<Class>> expandSignatures(List<List<Class>> signatures) {
        List<List<Class>> expandedSignatures = new ArrayList<>();

        for (List<Class> signature : signatures) {

        }

        return expandedSignatures;
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



}
