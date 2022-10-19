package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.io.FormatWriter;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.meta.annotations.Description;
import com.marcosavard.commons.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.Readonly;
import com.marcosavard.commons.meta.annotations.NotNull;
import com.marcosavard.commons.util.collection.SortedList;
import com.marcosavard.commons.util.collection.UniqueList;

import java.io.*;
import java.lang.reflect.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PojoGenerator {

    public enum AccessorOrder {
        GROUPED_BY_PROPERTIES, GROUPED_BY_GETTERS_SETTERS
    }

    private final File destinationFolder;
    private int indentation = 2;
    private boolean metadataGeneration = false;
    private AccessorOrder accessorOrder = AccessorOrder.GROUPED_BY_PROPERTIES;

    public PojoGenerator(File destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    public PojoGenerator withIndentation(int indentation) {
        this.indentation = indentation;
        return this;
    }

    public void withAccessors(AccessorOrder accessorOrder) {
        this.accessorOrder = accessorOrder;
    }

    public PojoGenerator withMetadataGeneration() {
        metadataGeneration = true;
        return this;
    }

    public File generate(Class<?> claz) throws IOException {
        //create folder
        String packageName = getPackageName(claz);
        String folderName = packageName.replace(".", "//");
        File subfolder = new File(destinationFolder, folderName);
        subfolder.mkdirs();

        //create file
        String filename = claz.getSimpleName() + ".java";
        File generated = new File(subfolder, filename);
        Writer w = new FileWriter(generated);
        FormatWriter fw = new FormatWriter(w, indentation);

        //generate code
        generateType(fw, packageName, claz);
        fw.close();

        return generated;
    }

    private String getPackageName(Class<?> claz) {
        String modelPackage = claz.getPackageName();
        int idx = modelPackage.lastIndexOf('.');
        return modelPackage.substring(0, idx);
    }

    private void generateType(FormatWriter w, String packageName, Class<?> claz) {
        w.println("package {0};", packageName);
        w.println();

        if (claz.isEnum()) {
            generateEnum(w, claz);
        } else {
            generateClass(w, claz);
        }
    }

    private void generateEnum(FormatWriter w, Class<?> claz) {
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
        Class<?> superClass = claz.getSuperclass();

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
        generateMetaFields(w, claz);
        generateConstructor(w, claz);
        generateMethods(w, claz);
        w.unindent();
        w.println("}");
    }

    private void generateImports(FormatWriter w, Class<?> claz) {
        Package pack = claz.getPackage();
        Field[] fields = claz.getDeclaredFields();
        Comparator<Class<?>> comparator = new ImportComparator();
        List<Class<?>> importees = new SortedList<>(comparator);
        importees.add(Objects.class);

        if (metadataGeneration) {
            importees.add(Field.class);
        }

        for (Field field : fields) {
            Class<?> type = field.getType();
            boolean collection = isCollection(type);

            if (isAddable(pack, type)) {
                importees.add(type);
            }

            if (collection) {
                Class<?> itemType = getItemType(field);
                if (! itemType.getPackage().equals(pack)) {
                    importees.add(itemType);
                }
            }
        }

        if (importees.contains(List.class)) {
            importees.add(ArrayList.class);
        }

        if (importees.size() > 0) {
            for (Class<?> importee : importees) {
                String fullName = importee.getName().replace('$', '.');
                w.println("import {0};", fullName);
            }
            w.println();
        }
    }

    private void generateClassComment(FormatWriter w, Class<?> claz) {
        String description = getDescription(claz);
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        w.println("/**");
        w.println(" * " + description);
        w.println(" * Generated on {0}", time.format(formatter));
        w.println(" */");
    }

    private void generateConstants(FormatWriter w, Class<?> claz) {
        List<Field> fields = getConstants(claz);

        for (Field field : fields) {
            generateField(w, field);
        }

        if (! fields.isEmpty()) {
            w.println();
        }
    }

    private void generateVariables(FormatWriter w, Class<?> claz) {
        List<Field> fields = getVariables(claz);
        
        for (Field field : fields) {
            generateField(w, field);
        }

        if (! fields.isEmpty()) {
            w.println();
        }
    }

    private void generateField(FormatWriter w, Field field) {
        String modifiers = getModifiers(field);
        Class<?> type = field.getType();
        boolean collection = isCollection(type);
        Class<?> itemType = collection ? getItemType(field) : null;
        String typeName = getTypeName(type, itemType);
        String initValue = getInitialValue(field);

        if (initValue == null) {
            w.println("{0} {1} {2};", modifiers, typeName, field.getName());
        } else {
            w.println("{0} {1} {2} = {3};", modifiers, typeName, field.getName(), initValue);
        }
    }

    private void generateMetaFields(FormatWriter w, Class<?> claz) {
        if (metadataGeneration) {
            List<Field> fields = getVariables(claz);
            String className = claz.getSimpleName();

            for (Field field : fields) {
                String metaField = StringUtil.camelToUnderscore(field.getName()) + "_FIELD";
                w.println("public static final Field {0};", metaField);
            }

            if (! fields.isEmpty()) {
                w.println();
                w.println("static {");
                w.indent();
                w.println("try {");

                for (Field field : fields) {
                    String name = field.getName();
                    String metaField = StringUtil.camelToUnderscore(name) + "_FIELD";
                    w.printlnIndented("{0} = {1}.class.getDeclaredField(\"{2}\");", metaField, className, name);
                }

                w.println("} catch (NoSuchFieldException e) {");
                w.printlnIndented("throw new RuntimeException(e);");
                w.println("}");
                w.unindent();
                w.println("}");
                w.println();
            }
        }
    }

    private void generateConstructor(FormatWriter w, Class<?> claz) {
        List<Field> readOnlyFields = getReadOnlyFields(claz);
        List<Field> superClassReadOnlyFields = getSuperClassReadOnlyFields(claz);
        List<Field> notNullFields = getNotNullFields(claz);

        List<Field> constructorFields = new ArrayList<>();
        constructorFields.addAll(superClassReadOnlyFields);
        constructorFields.addAll(readOnlyFields);
        //constructorFields.addAll(notNullFields);

        Map<String, Field> fieldsByName = new HashMap<>();
        for (Field field : constructorFields) {
            fieldsByName.put(field.getName(), field);
        }

        if (! constructorFields.isEmpty()) {
            String visibility = isAbstract(claz) ? "protected" : "public";
            String className = claz.getSimpleName();
            List<String> parameters = getFieldDeclarations(constructorFields);
            w.println("/**");

            for (String name : fieldsByName.keySet()) {
                Field field = fieldsByName.get(name);
                w.println(" * @param " + getDescription(field));
            }

            w.println(" */");
            w.print("{0} {1}(", visibility, className);
            w.print(String.join(", ", parameters));
            w.println(") {");
            w.indent();
            generateConstructorBody(w, superClassReadOnlyFields, fieldsByName);
            w.unindent();
            w.println("}");
            w.println();
        }
    }

    private void generateConstructorBody(FormatWriter w, List<Field> superClassReadOnlyFields, Map<String, Field> fieldsByName) {
        List<Field> settableFields = new ArrayList<>(fieldsByName.values());
        settableFields.removeAll(superClassReadOnlyFields);

        if (! superClassReadOnlyFields.isEmpty()) {
            List<String> fieldNames = getFieldNames(superClassReadOnlyFields);
            w.println("super(" + String.join(", ", fieldNames) + ");");
        }

        for (Field f : settableFields) {
            if (isNotNull(f) && ! isPrimitive(f.getType())) {
                verifyNullArgument(w, f);
            }
        }

        for (Field f : settableFields) {
            w.println("this.{0} = {0};", f.getName());
        }

        if (! settableFields.isEmpty()) {
            w.println();
        }
    }

    private void verifyNullArgument(FormatWriter w, Field f) {
        w.println("if ({0} == null) '{'", f.getName());
        w.printlnIndented("throw new IllegalArgumentException (\"Parameter ''{0}'' cannot be null\");", f.getName());
        w.println("}");
        w.println();
    }

    private void generateMethods(FormatWriter w, Class<?> claz) {
        generateAccessors(w, claz);
        generateMetaAccessors(w, claz);
        generateIdentityMethods(w, claz);
        generateToString(w, claz);
    }

    private void generateAccessors(FormatWriter w, Class<?> claz) {
        if (this.accessorOrder == AccessorOrder.GROUPED_BY_PROPERTIES) {
            generateAccessorsGroupedByProperties(w, claz);
        } else {
            generateAccessorsGroupedByGettersSetters(w, claz);
        }
    }

    private void generateAccessorsGroupedByProperties(FormatWriter w, Class<?> claz) {
        List<Field> fields = getVariables(claz);
        boolean immutable = isImmutable(claz);

        for (Field field : fields) {
            generateGetter(w, field);

            if (! immutable && (! isReadOnly(field))) {
                generateSetter(w, field);
            }
        }

        w.println();
    }

    private void generateAccessorsGroupedByGettersSetters(FormatWriter w, Class<?> claz) {
        List<Field> fields = getVariables(claz);
        boolean immutable = isImmutable(claz);

        for (Field field : fields) {
            generateGetter(w, field);
        }

        for (Field field : fields) {
            if (! immutable && (! isReadOnly(field))) {
                generateSetter(w, field);
            }
        }

        w.println();
    }

    private void generateGetter(FormatWriter w, Field field) {
        Class<?> type = field.getType();
        boolean collection = isCollection(type);
        Class<?> itemType = collection ? getItemType(field) : null;
        String typeName = getTypeName(type, itemType);
        String getter = getGetterName(field);

        w.println("/**");
        w.println(" * @return " + getDescription(field));
        w.println(" */");
        w.println("public {0} {1}() '{'", typeName, getter);
        w.printlnIndented("return {0};", field.getName());
        w.println("}");
        w.println();
    }

    private void generateSetter(FormatWriter w, Field field) {
        boolean constant = isConstant(field);
        boolean settable = ! constant;
        Class<?> type = field.getType();
        boolean collection = isCollection(type);
        
        if (settable) {
            if (collection) {
                generateCollectionSetters(w, field);
            } else {
                generateBasicSetter(w, field);
            }
        }
    }

    private void generateBasicSetter(FormatWriter w, Field field) {
        String name = field.getName();
        String methodName = "set" + StringUtil.capitalize(name);
        String typeName = getTypeName(field);

        w.println("/**");
        w.println(" * @param " + getDescription(field));
        w.println(" */");
        w.println("public void {0}({1} {2}) '{'", methodName, typeName, name);
        w.indent();

        if (isNotNull(field) && ! isPrimitive(field.getType())) {
            verifyNullArgument(w, field);
        }

        w.println("this.{0} = {0};", name);
        w.unindent();
        w.println("}");
        w.println();
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
        w.printlnIndented("this.{0}.remove({1});", field.getName(), parameter);
        w.println("}");
        w.println();
    }

    private void generateMetaAccessors(FormatWriter w, Class<?> claz) {
        if (metadataGeneration) {
            List<Field> fields = getVariables(claz);
            List<String> metaFields = new ArrayList<>();

            w.println("public static Field[] getFields() {");
            w.indent();
            w.print("return new Field[] {");
            for (Field field : fields) {
                metaFields.add(StringUtil.camelToUnderscore(field.getName()) + "_FIELD");
            }
            w.print(String.join(", ", metaFields));
            w.print("};");
            w.println();
            w.unindent();
            w.println("}");
            w.println();
            generateMetaGetter(w);
            generateMetaSetter(w);
        }
    }

    private void generateMetaGetter(FormatWriter w) {
        w.println("/**");
        w.println(" * @param field");
        w.println(" * @return the value for this field");
        w.println(" */");
        w.println("public Object get(Field field) throws IllegalAccessException {");
        w.printlnIndented("return field.get(this);");
        w.println("}");
        w.println();
    }

    private void generateMetaSetter(FormatWriter w) {
        w.println("/**");
        w.println(" * @param field");
        w.println(" * @param value to be assigned");
        w.println(" */");
        w.println("public void set(Field field, Object value) throws IllegalAccessException {");
        w.printlnIndented("field.set(this, value);");
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
        w.println("public boolean equals(Object other) {");
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
        w.printlnIndented("{0} that = ({0})other;", name);
        w.printlnIndented("equal = (hashCode() == that.hashCode()) && isEqualTo(that);");
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
        List<String> hashList = getGetterList(claz);

        if (hasSuperClass(claz)) {
            hashList.add("super.hashCode()");
        }

        String fields = String.join(", ", hashList);
        w.println(MessageFormat.format("return Objects.hash({0});", fields));
    }

    private void generateIsEqualTo(FormatWriter w, Class<?> claz) {
        String name = claz.getSimpleName();
        w.println("protected boolean isEqualTo({0} that) '{'", name);
        w.indent();
        generateIsEqualToBody(w, claz);
        w.unindent();
        w.println("}");
        w.println();
    }

    private void generateIsEqualToBody(FormatWriter w, Class<?> claz) {
        List<Field> fields = getVariables(claz);
        w.println("boolean equal = true;");

        for (Field field : fields) {
            String getter = getGetterName(field);
            if (isPrimitive(field.getType())) {
                w.println("equal = equal && {0}() == that.{0}();", getter);
            } else {
                w.println("equal = equal && {0}() == null ? that.{0}() == null : {0}().equals(that.{0}());", getter);
            }
        }

        if (hasSuperClass(claz)) {
            w.println("equal = equal && super.isEqualTo(that);");
        }

        w.println("return equal;");
    }

    private void generateToString(FormatWriter w, Class<?> claz) {
        List<Field> fields = getVariables(claz);

        w.println("@Override");
        w.println("public String toString() {");
        w.printlnIndented("StringBuilder sb = new StringBuilder();");
        w.printlnIndented("sb.append(\"{\");");

        for (Field field : fields) {
            w.printlnIndented("sb.append(\"{0} = \").append({0}).append(\", \");", field.getName());
        }

        w.printlnIndented("sb.append(\"}\");");
        w.printlnIndented("return sb.toString();");
        w.println("}");
        w.println();
    }


    private List<String> getFieldNames(List<Field> fields) {
        List<String> fieldNames = new ArrayList<>();

        for (Field field : fields) {
            fieldNames.add(field.getName());
        }

        return fieldNames;
    }

    private List<Field> getSuperClassReadOnlyFields(Class<?> claz) {
        List<Field> allReadOnlyFields = getAllReadOnlyFields(claz);
        List<Field> fields = getReadOnlyFields(claz);
        List<Field> superclassFields = new ArrayList<>(allReadOnlyFields);
        superclassFields.removeAll(fields);
        return superclassFields;
    }

    private List<String> getFieldDeclarations(List<Field> fields) {
        List<String> declarations = new UniqueList<>();

        for (Field field : fields) {
            String typeName = field.getType().getSimpleName();
            String fieldname = field.getName();
            declarations.add(typeName + " " + fieldname);
        }

        return declarations;
    }

    private String getDescription(Class<?> claz) {
        String name = claz.getSimpleName();
        Description description = claz.getAnnotation(Description.class);
        String desc =  (description == null) ? "" : description.value();
        return name + " " + desc;
    }

    private String getDescription(Field field) {
        String name = field.getName();
        Description description = field.getAnnotation(Description.class);
        String desc =  (description == null) ? field.getType().getSimpleName() : description.value();
        return name + " " + desc;
    }

    private List<Field> getConstants(Class<?> claz) {
        return Arrays.stream(claz.getDeclaredFields())
                .filter(f -> isConstant(f))
                .toList();
    }

    private List<Field> getVariables(Class<?> claz) {
        return Arrays.stream(claz.getDeclaredFields())
                .filter(f -> ! isConstant(f))
                .toList();
    }

    private List<Field> getAllReadOnlyFields(Class<?> claz) {
        boolean immutable = isImmutable(claz);
        return Arrays.stream(claz.getFields())
                .filter(f -> immutable || isReadOnly(f))
                .toList();
    }

    private List<Field> getReadOnlyFields(Class<?> claz) {
        boolean immutable = isImmutable(claz);
        return Arrays.stream(claz.getDeclaredFields())
                .filter(f -> immutable || isReadOnly(f))
                .toList();
    }

    private List<Field> getNotNullFields(Class<?> claz) {
        return Arrays.stream(claz.getDeclaredFields())
                .filter(f -> isNotNull(f))
                .toList();
    }

    private Class<?> getItemType(Field f) {
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

    private String getTypeName(Field field) {
        Class<?> type = field.getType();
        boolean collection = isCollection(type);
        Class<?> itemType = collection ? getItemType(field) : null;
        String typeName = type.getSimpleName();
        return (itemType == null) ? typeName : typeName + "<" + itemType.getSimpleName() + ">";
    }

    private String getTypeName(Class<?> type, Class<?> itemType) {
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
        Class<?> type = field.getType();
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

    private String getModifiers(Class<?> claz) {
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
        Class<?> claz = field.getDeclaringClass();
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

    private String getInitialValueOfVariable(Field field) {
        String initialValue = null;

        try {
            Class<?> claz = field.getDeclaringClass();
            Constructor<?> constr = claz.getConstructor(new Class[] {});
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

    private boolean hasSuperClass(Class<?> claz) {
        Class<?> superclass = claz.getSuperclass();
        return (superclass != null) && (! Object.class.equals(superclass));
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

    private boolean isAddable(Package pack, Class<?> type) {
        String packageName = type.getPackageName();
        boolean addable = ! "java.lang".equals(packageName);
        return addable && ! type.getPackage().equals(pack);
    }

    private boolean isCollection(Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }

    private boolean isImmutable(Class<?> claz) {
        return claz.getAnnotation(Immutable.class) != null;
    }

    private boolean isReadOnly(Field field) {
        return field.getAnnotation(Readonly.class) != null;
    }

    private boolean isNotNull(Field field) {
        return field.getAnnotation(NotNull.class) != null;
    }

    private static class ImportComparator implements Comparator<Class<?>> {
        @Override
        public int compare(Class c1, Class c2) {
            String n1 = c1.getName();
            String n2 = c2.getName();
            return n1.compareTo(n2);
        }
    }
}
