package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.io.FormatWriter;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.meta.annotations.Description;
import com.marcosavard.commons.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.Readonly;
import com.marcosavard.commons.util.collection.SortedList;

import java.io.*;
import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.*;

public class PojoGenerator {
    //private static final Comparator<Field> FIELD_COMPARATOR = new FieldComparator();

    private final File destinationFolder;

    public PojoGenerator(File destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    public File generate(Class<?> claz) throws IOException {
        //create folder
        String folderName = claz.getPackageName().replace(".", "//");
        File subfolder = new File(destinationFolder, folderName);
        subfolder.mkdirs();

        //create file
        String filename = claz.getSimpleName() + ".java";
        File generated = new File(subfolder, filename);
        Writer w = new FileWriter(generated);
        FormatWriter fw = new FormatWriter(w);

        //generate code
        generateType(fw, claz);
        fw.close();

        return generated;
    }

    private void generateType(FormatWriter fw, Class<?> claz) {
        if (claz.isEnum()) {
            generateEnum(fw, claz);
        } else {
            generateClass(fw, claz);
        }
    }

    private void generateEnum(FormatWriter w, Class<?> claz) {
        w.println("package {0};", claz.getPackageName());
        w.println();

        w.println("public enum {0} '{'", claz.getSimpleName());
        w.indent();
        generateLiterals(w, claz);
        w.unindent();
        w.println("}");
    }

    private void generateLiterals(FormatWriter w, Class<?> claz) {
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields) {
            generateLiteral(w, field);
        }
    }

    private void generateLiteral(FormatWriter w, Field field) {
        boolean isPublic = Modifier.isPublic(field.getModifiers());

        if (isPublic) {
            w.println("{0},", field.getName());
        }
    }

    private void generateClass(FormatWriter w, Class<?> claz) {
        String modifiers = getModifiers(claz);
        Class superClass = claz.getSuperclass();

        w.println("package {0};", claz.getPackageName());
        w.println();
        generateImports(w, claz);

        generateClassComment(w, claz);
        w.print("{0} class {1}", modifiers, claz.getSimpleName());

        if ((superClass != null) && ! superClass.equals(Object.class)) {
            w.print(" extends {0}", superClass.getSimpleName());
        }

        w.println(" {");
        w.indent();
        generateConstants(w, claz);
        generateVariables(w, claz);
        generateConstructor(w, claz);
        generateMethods(w, claz);
        w.unindent();
        w.println("}");
    }

    private void generateImports(FormatWriter w, Class<?> claz) {
        Package pack = claz.getPackage();
        Field[] fields = claz.getDeclaredFields();
        Comparator<Class> comparator = new ImportComparator();
        List<Class> importees = new SortedList<>(comparator);
        importees.add(Objects.class);

        for (Field field : fields) {
            Class type = field.getType();
            boolean collection = isCollection(type);

            if (isAddable(pack, type)) {
                importees.add(type);
            }

            if (collection) {
                Class itemType = getItemType(field);
                if (! itemType.getPackage().equals(pack)) {
                    importees.add(itemType);
                }
            }
        }

        if (importees.size() > 0) {
            for (Class importee : importees) {
                String fullName = importee.getName().replace('$', '.');
                w.println("import {0};", fullName);
            }
            w.println();
        }
    }

    private void generateClassComment(FormatWriter w, Class<?> claz) {
        Description description = (Description) claz.getAnnotation(Description.class);

        if (description != null) {
            w.println("/**");
            w.println(" * " + description.value());
            w.println(" */");
        }
    }

    private void generateConstants(FormatWriter w, Class<?> claz) {
        List<Field> fields = getConstants(claz);

        for (Field field : fields) {
            generateField(w, field);
        }

        w.println();
    }

    private void generateVariables(FormatWriter w, Class<?> claz) {
        List<Field> fields = getVariables(claz);
        
        for (Field field : fields) {
            generateField(w, field);
        }

        w.println();
    }

    private void generateField(FormatWriter w, Field field) {
        String modifiers = getModifiers(field);
        Class type = field.getType();
        boolean collection = isCollection(type);
        Class itemType = collection ? getItemType(field) : null;
        String typeName = getTypeName(type, itemType);
        String initValue = getInitialValue(field);

        if (initValue == null) {
            w.println("{0} {1} {2};", modifiers, typeName, field.getName());
        } else {
            w.println("{0} {1} {2} = {3};", modifiers, typeName, field.getName(), initValue);
        }
    }

    private void generateConstructor(FormatWriter w, Class<?> claz) {
        List<Field> fields = getAllReadOnlyFields(claz);

        if (! fields.isEmpty()) {
            String visibility = isAbstract(claz) ? "protected" : "public";
            String name = claz.getSimpleName();
            List<String> parameters = getParameters(fields);

            w.print("{0} {1}(", visibility, name);
            w.print(String.join(", ", parameters));
            w.println(") {");
            w.indent();
            generateConstructorBody(w, claz);
            w.unindent();
            w.println("}");
            w.println();
        }
    }

    private void generateConstructorBody(FormatWriter w,Class<?> claz) {
        List<Field> allReadOnlyFields = getAllReadOnlyFields(claz);
        List<Field> fields = getReadOnlyFields(claz);
        List<Field> superclassFields = new ArrayList<>(allReadOnlyFields);
        superclassFields.removeAll(fields);
        List<String> fieldNames = getFieldNames(superclassFields);
        w.println("super(" + String.join(", ", fieldNames) + ");");

        for (Field field : fields) {
            w.println("this.{0} = {0};", field.getName());
        }
    }

    private List<String> getFieldNames(List<Field> fields) {
        List<String> fieldNames = new ArrayList<>();

        for (Field field : fields) {
            fieldNames.add(field.getName());
        }

        return fieldNames;
    }

    private void generateMethods(FormatWriter w, Class<?> claz) {
        generateAccessors(w, claz);
        generateIdentityMethods(w, claz);
    }

    private void generateAccessors(FormatWriter w, Class<?> claz) {
        List<Field> fields = getVariables(claz);
        boolean immutable = isImmutable(claz);

        for (Field field : fields) {
            Readonly readonly = (Readonly) field.getAnnotation(Readonly.class);
            generateGetter(w, field);

            if (! immutable && (readonly == null)) {
                generateSetter(w, field);
            }
        }

        w.println();
    }

    private void generateGetter(FormatWriter w, Field field) {
        Class type = field.getType();
        boolean collection = isCollection(type);
        Class itemType = collection ? getItemType(field) : null;
        String typeName = getTypeName(type, itemType);
        String getter = getGetterName(field);

        w.println("/**");
        w.println(" * @return " + getDescription(field));
        w.println(" */");
        w.println("public {0} {1}() '{'", typeName, getter);
        w.println("  return {0};", field.getName());
        w.println("}");
        w.println();
    }

    private void generateSetter(FormatWriter w, Field field) {
        boolean constant = isConstant(field);
        boolean settable = ! constant;
        Class type = field.getType();
        boolean collection = isCollection(type);
        
        if (settable) {
            if (collection) {
                generateCollectionSetters(w, field);
            } else {
                generateBasicSetter(w, field);
            }
        }
    }

    private void generateCollectionSetters(FormatWriter w, Field field) {
        generateAdder(w, field);
        generateRemover(w, field);
    }

    private void generateAdder(FormatWriter w, Field field) {
        String visibility = getVisibility(field);
        String name = StringUtil.capitalize(field.getName());
        String itemType = getItemType(field).getSimpleName();
        String parameter = StringUtil.uncapitalize(itemType);

        w.println("{0} void addTo{1}({2} {3}) '{'", visibility, name, itemType, parameter);
        w.indent();
        w.println("this.{0}.add({1});", field.getName(), parameter);
        w.unindent();
        w.println("}");
        w.println();
    }

    private void generateRemover(FormatWriter w, Field field) {
        String visibility = getVisibility(field);
        String name = StringUtil.capitalize(field.getName());
        String itemType = getItemType(field).getSimpleName();
        String parameter = StringUtil.uncapitalize(itemType);

        w.println("{0} void removeFrom{1}({2} {3}) '{'", visibility, name, itemType, parameter);
        w.indent();
        w.println("this.{0}.remove({1});", field.getName(), parameter);
        w.unindent();
        w.println("}");
        w.println();
    }

    private void generateIdentityMethods(FormatWriter w, Class<?> claz) {
        generateEquals(w, claz);
        generateHashCode(w, claz);
        generateIsEqualTo(w, claz);
    }

    private void generateEquals(FormatWriter w, Class<?> claz) {
        w.println("@Override");
        w.println("public boolean equal(Object that) {");
        w.indent();
        generateEqualsBody(w, claz);
        w.unindent();
        w.println("}");
        w.println();
    }

    private void generateEqualsBody(FormatWriter w, Class<?> claz) {
        String name = claz.getSimpleName();
        w.println("boolean equal = false;");
        w.println();

        w.println("if (other instanceof {0}) '{'", name);
        w.println("  {0} that = ({0})other;", name);
        w.println("  equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;");
        w.println("}");
        w.println();

        w.println("return equal;");
        w.println();
    }

    private void generateHashCode(FormatWriter w, Class<?> claz) {
        w.println("@Override");
        w.println("public int hashCode() {");
        w.indent();
        generateHashCodeBody(w, claz);
        w.unindent();
        w.println("}");
        w.println();
    }

    private void generateHashCodeBody(FormatWriter w, Class<?> claz) {
        List<String> fieldList = getGetterList(claz);
        String fields = String.join(", ", fieldList);
        w.println(MessageFormat.format("int hashCode = Objects.hash({0});", fields));
        w.println("return hashCode;");
    }

    private void generateIsEqualTo(FormatWriter w, Class<?> claz) {
        String name = claz.getSimpleName();
        w.println("private boolean isEqualTo({0} that) '{'", name);
        w.indent();
        generateIsEqualToBody(w, claz);
        w.unindent();
        w.println("}");
        w.println();
    }

    private void generateIsEqualToBody(FormatWriter w, Class<?> claz) {
        List<Field> fields = getVariables(claz);
        w.println("boolean equal = false;");

        for (Field field : fields) {
            String getter = getGetterName(field);
            if (isPrimitive(field.getType())) {
                w.println("equal = equal && {0}() == that.{0}();", getter);
            } else {
                w.println("equal = equal && {0}() == null ? that.{0}() == null : {0}().equals(that.{0}());", getter);
            }
        }

        w.println("return equal;");
    }

    private List<String> getParameters(List<Field> fields) {
        List<String> parameters = new ArrayList<>();

        for (Field field : fields) {
            String typeName = field.getType().getSimpleName();
            String fieldname = field.getName();
            parameters.add(typeName + " " + fieldname);
        }

        return parameters;    }

    private String getDescription(Field field) {
        String name = field.getName();
        Description description = (Description) field.getAnnotation(Description.class);
        return (description == null) ? name : name + " " + description.value();
    }

    private List<Field> getConstants(Class<?> claz) {
        return Arrays.asList(claz.getDeclaredFields()).stream()
                .filter(f -> isConstant(f))
                //.sorted(FIELD_COMPARATOR)
                .toList();
    }

    private List<Field> getVariables(Class<?> claz) {
        return Arrays.asList(claz.getDeclaredFields()).stream()
                .filter(f -> ! isConstant(f))
              //  .sorted(FIELD_COMPARATOR)
                .toList();
    }

    private List<Field> getAllReadOnlyFields(Class<?> claz) {
        boolean immutable = isImmutable(claz);
        return Arrays.asList(claz.getFields()).stream()
                .filter(f -> immutable || isReadOnly(f))
             //   .sorted(FIELD_COMPARATOR)
                .toList();
    }

    private List<Field> getReadOnlyFields(Class<?> claz) {
        boolean immutable = isImmutable(claz);
        return Arrays.asList(claz.getDeclaredFields()).stream()
                .filter(f -> immutable || isReadOnly(f))
          //      .sorted(FIELD_COMPARATOR)
                .toList();
    }

    private Class<?> getItemType(Field f) {
        Class<?> itemType = Object.class;
        Type type = f.getGenericType();

        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type rawType = pt.getRawType();
            Type[] types = pt.getActualTypeArguments();
            if (types[0] instanceof Class) {
                itemType = (Class) types[0];
            }

        }

        return itemType;
    }

    private String getTypeName(Field field) {
        Class type = field.getType();
        boolean collection = isCollection(type);
        Class itemType = collection ? getItemType(field) : null;
        String typeName = type.getSimpleName();
        return (itemType == null) ? typeName : typeName + "<" + itemType.getSimpleName() + ">";
    }

    private String getTypeName(Class type, Class itemType) {
        String typeName = type.getSimpleName();
        return (itemType == null) ? typeName : typeName + "<" + itemType.getSimpleName() + ">";
    }


    private List<String> getGetterList(Class<?> claz) {
        List<String> getterList = new ArrayList<>();
        List<Field> fields = getVariables(claz);

        for (Field field : fields) {
            getterList.add(getGetterName(field) + "()");
        }

        return  getterList;
    }

    private String getGetterName(Field field) {
        Class type = field.getType();
        String verb = type.equals(boolean.class) ? "is" : "get";
        return verb + StringUtil.capitalize(field.getName());
    }

    private String getVisibility(Field field) {
        String visibility = "private";

        if (isPublic(field)) {
            visibility = "public";
        }

        return visibility;
    }

    private void generateBasicSetter(FormatWriter w, Field field) {
        String methodName = "set" + StringUtil.capitalize(field.getName());
        String typeName = getTypeName(field);

        w.println("/**");
        w.println(" * @param " + getDescription(field));
        w.println(" */");
        w.println("public void {0}({1} value) '{'", methodName, typeName);
        w.println("  this.{0} = value;", field.getName());
        w.println("}");
        w.println();
    }

    private String getModifiers(Class claz) {
        List<String> modifiers = new ArrayList<>();
        boolean isPublic = Modifier.isPublic(claz.getModifiers());
        boolean isAbstract = Modifier.isAbstract(claz.getModifiers());

        if (isPublic) {
            modifiers.add("public");
        }

        if (isAbstract) {
            modifiers.add("abstract");
        }

        return String.join(" ", modifiers);
    }

    private String getModifiers(Field field) {
        List<String> modifiers = new ArrayList<>();
        Class claz = field.getDeclaringClass();
        boolean immutable = isImmutable(claz);
        boolean isPublic = Modifier.isPublic(field.getModifiers());
        boolean isProtected = Modifier.isProtected(field.getModifiers());
        boolean isPrivate = Modifier.isPrivate(field.getModifiers());
        boolean isStatic = Modifier.isStatic(field.getModifiers());
        boolean isFinal = Modifier.isFinal(field.getModifiers()) || isReadOnly(field) || immutable;

        if (! isStatic || !isFinal) {
            isPublic = false;
            isProtected = false;
            isPrivate = true;
        }

        if (isPublic) {
            modifiers.add("public");
        }

        if (isProtected) {
            modifiers.add("protected");
        }

        if (isPrivate) {
            modifiers.add("private");
        }

        if (isStatic) {
            modifiers.add("static");
        }

        if (isFinal) {
            modifiers.add("final");
        }


        return String.join(" ", modifiers);
    }

    private String getInitialValue(Field field) {
        String initialValue = null;
        Class type = field.getType();
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

    private String getInitialValueOfVariable(Field field) {
        String initialValue = null;

        try {
            Class<?> claz = field.getDeclaringClass();
            Constructor constr = claz.getConstructor(new Class[] {});
            Object instance = constr.newInstance(new Object[] {});
            Object value = field.get(instance);

            if (value != null) {
                initialValue = (value instanceof String) ? "\"" + value + "\"" : Objects.toString(value);
            }

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            initialValue = null;
        }

        return initialValue;
    }

    private boolean isAbstract(Class<?> claz) {
        return Modifier.isAbstract(claz.getModifiers());
    }

    private boolean isConstant(Field field) {
        boolean isStatic = Modifier.isStatic(field.getModifiers());
        boolean isFinal = Modifier.isFinal(field.getModifiers());
        return isStatic && isFinal;
    }

    private boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    private boolean isPrimitive(Class<?> type) {
        boolean primitive = false;
        primitive = primitive || boolean.class.equals(type);
        primitive = primitive || byte.class.equals(type);
        primitive = primitive || char.class.equals(type);
        primitive = primitive || short.class.equals(type);
        primitive = primitive || int.class.equals(type);
        primitive = primitive || long.class.equals(type);
        primitive = primitive || float.class.equals(type);
        primitive = primitive || double.class.equals(type);
        return primitive;
    }

    private boolean isAddable(Package pack, Class type) {
        String packageName = type.getPackageName();
        boolean addable = ! "java.lang".equals(packageName);
        return addable && ! type.getPackage().equals(pack);
    }

    private boolean isCollection(Class type) {
        return Collection.class.isAssignableFrom(type);
    }

    private boolean isImmutable(Class<?> claz) {
        return claz.getAnnotation(Immutable.class) != null;
    }

    private boolean isReadOnly(Field field) {
        return field.getAnnotation(Readonly.class) != null;
    }

    private static class ImportComparator implements Comparator<Class> {
        @Override
        public int compare(Class c1, Class c2) {
            String n1 = c1.getName();
            String n2 = c2.getName();
            return n1.compareTo(n2);
        }
    }

    /*
    private static class FieldComparator implements Comparator<Field> {
        @Override
        public int compare(Field f1, Field f2) {
            String n1 = f1.getName();
            String n2 = f2.getName();
            return n1.compareTo(n2);
        }
    }*/


}