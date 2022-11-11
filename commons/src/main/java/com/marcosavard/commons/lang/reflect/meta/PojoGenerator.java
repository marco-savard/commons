package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.io.FormatWriter;
import com.marcosavard.commons.lang.StringUtil;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class PojoGenerator {
    protected final File outputFolder;

    //Options
    protected int indentation = 2;
    protected boolean generateMetadata = false;

    protected boolean generateParameterlessConstructor = false;

    protected AccessorOrder accessorOrder = AccessorOrder.GROUPED_BY_PROPERTIES;

    protected String containerName = "owner";

    public enum AccessorOrder {
        GROUPED_BY_PROPERTIES,
        GROUPED_BY_GETTERS_SETTERS
    }

    protected PojoGenerator(File outputFolder) {
        this.outputFolder = outputFolder;
    }

    public PojoGenerator withAccessors(AccessorOrder accessorOrder) {
        this.accessorOrder = accessorOrder;
        return this;
    }

    public PojoGenerator withContainerName(String containerName) {
        this.containerName = containerName;
        return this;
    }

    public PojoGenerator withIndentation(int indentation) {
        this.indentation = indentation;
        return this;
    }

    public PojoGenerator withMetadata() {
        generateMetadata = true;
        return this;
    }

    public PojoGenerator withParameterlessConstructor() {
        generateParameterlessConstructor = true;
        return this;
    }

    public abstract File generateClass(MetaClass mc) throws IOException;

    protected abstract String getInitialValue(MetaField mf);

    protected abstract MetaField getReferenceForClass(MetaClass mc);

    protected abstract String getGetterName(MetaField mf);

    protected abstract List<MetaClass> getSubClasses(MetaClass mc);

    protected void generateParameterlessConstructor(FormatWriter w, MetaClass mc) {
        if (generateParameterlessConstructor) {
            String className = mc.getSimpleName();
            w.println("{0} {1}() '{'", "public", className);
            generateParameterlessConstructorBody(w, mc);
            w.println("}");
            w.println();
        }
    }

    protected void generateParameterlessConstructorBody(FormatWriter w, MetaClass mc) {
        List<MetaField> requiredFields = mc.getVariables().stream().filter(mf -> ! mf.isOptional()).toList();

        w.indent();
        for (MetaField mf : requiredFields) {
            w.println("this.{0} = {1};", mf.getName(), getInitialValue(mf));
        }
        w.unindent();
    }

    protected void generateConstructorBody(FormatWriter w, MetaClass mc, List<MetaField> parameters) {
        MetaClass superClass = mc.getSuperClass();
        List<MetaField> superClassVariables = getConstructorFields(superClass);

        //generate the super
        if (!superClassVariables.isEmpty()) {
            List<String> referenceNames = toNameList(superClassVariables);
            w.println("super(" + String.join(", ", referenceNames) + ");");
        }

        List<MetaField> settableParameters = new ArrayList<>(parameters);
        settableParameters.removeAll(superClassVariables);

        for (MetaField mf : settableParameters) {
            if (! mf.isOptional() && ! mf.getType().isPrimitive() && ! mf.getType().isCollection()) {
                verifyNullArgument(w, mf);
            }
        }

        for (MetaField m : settableParameters) {
            w.println("this.{0} = {0};", m.getName());
        }
    }

    protected void generateMethods(FormatWriter w, MetaClass mc) {
        generateAccessors(w, mc);
        generateMetaAccessors(w, mc);
        generateIdentityMethods(w, mc);
        generateToString(w, mc);
    }

    protected void generateAccessors(FormatWriter w, MetaClass mc) {
        if (this.accessorOrder == AccessorOrder.GROUPED_BY_PROPERTIES) {
            generateAccessorsGroupedByProperties(w, mc);
        } else {
            generateAccessorsGroupedByGettersSetters(w, mc);
        }
    }

    protected void generateAccessorsGroupedByProperties(FormatWriter w, MetaClass mc) {
        List<MetaField> variables = mc.getVariables();
        boolean immutable = mc.isImmutable();

        for (MetaField variable : variables) {
            generateGetter(w, variable);

            if (! immutable && ! variable.isReadOnly()) {
                generateSetter(w, variable);
            }
        }

        w.println();
    }

    protected void generateAccessorsGroupedByGettersSetters(FormatWriter w, MetaClass mc) {
        List<MetaField> variables = mc.getVariables();
        boolean immutable = mc.isImmutable();

        for (MetaField variable : variables) {
            generateGetter(w, variable);
        }

        for (MetaField variable : variables) {
            if (!immutable && ! variable.isReadOnly()) {
                generateSetter(w, variable);
            }
        }

        w.println();
    }

    protected void generateGetter(FormatWriter w, MetaField mf) {
        List<String> modifiers = new ArrayList<>();
        modifiers.addAll(mf.getVisibilityModifiers());
        modifiers.addAll(mf.getOtherModifiers());

        boolean optional = mf.isOptional();
        String typeName = optional ? mf.getItemType().getSimpleName() : mf.getTypeName();
        String getter = getGetterName(mf);
        String orElseNull = optional ? ".orElse(null)" : "";

        w.println("/**");
        w.println(" * @return {0} {1}" + mf.getName(), mf.getDescription());
        w.println(" */");
        w.println("{0} {1} {2}() '{'", String.join(" ", modifiers), typeName, getter);
        w.printlnIndented("return {0}{1};", mf.getName(), orElseNull);
        w.println("}");
        w.println();
    }

    protected void generateSetter(FormatWriter w, MetaField mf) {
        boolean settable = ! mf.isConstant();
        boolean collection = mf.getType().isCollection();

        if (settable) {
            if (collection) {
                generateAddersRemovers(w, mf);
            } else {
                if (mf.isComponent()) {
                    generateFactories(w, mf);
                } else {
                    generateBasicSetter(w, mf);
                }
            }
        }
    }

    protected void generateBasicSetter(FormatWriter w, MetaField mf) {
        List<String> modifiers = new ArrayList<>();
        modifiers.addAll(mf.getVisibilityModifiers());
        modifiers.addAll(mf.getOtherModifiers());

        String prefix = mf.isStatic() ? mf.getDeclaringClass().getSimpleName() : "this";
        String name = mf.getName();
        String methodName = "set" + StringUtil.capitalize(name);

        boolean optional = mf.isOptional();
        boolean primitive = mf.getType().isPrimitive();

        String typeName = optional ? mf.getItemType().getSimpleName() : mf.getTypeName();
        String value = optional ? "Optional.of(" + name + ")" : name;

        w.println("/**");
        w.println(" * @param " + mf.getName() + " " + mf.getDescription());
        w.println(" */");
        w.println("{0} void {1}({2} {3}) '{'", String.join(" ", modifiers), methodName, typeName, name);
        w.indent();

        if (! primitive && ! optional) {
            verifyNullArgument(w, mf);
        }

        w.println("{0}.{1} = {2};", prefix, name, value);
        w.unindent();
        w.println("}");
        w.println();
    }


    protected void generateAddersRemovers(FormatWriter w, MetaField mf) {
        boolean component = mf.isComponent() && mf.isOptional();

        if (component) {
            generateFactories(w, mf);
        } else {
            generateAdder(w, mf);
        }

        generateRemover(w, mf);
    }

    protected void generateFactories(FormatWriter w, MetaField mf) {
        boolean collection = mf.getType().isCollection();
        boolean optional = mf.isOptional();

        MetaClass type = collection || optional ? mf.getItemType() : mf.getType();
        String verb = collection ? "create" : "set";
        String fieldName = StringUtil.capitalize(mf.getName());

        if (type.isAbstract()) {
            MetaClass actualType = collection || optional ? mf.getItemType() : mf.getType();
            List<MetaClass> subclasses = getSubClasses(actualType);

            for (MetaClass subclass : subclasses) {
                String typeName =  StringUtil.capitalize(subclass.getSimpleName());
                String factoryName = verb + fieldName + typeName;
                generateFactory(w, mf, subclass, factoryName);
            }
        } else {
            String factoryName = verb + type.getSimpleName();
            generateFactory(w, mf, type, factoryName);
        }
    }

    protected void generateFactory(FormatWriter w, MetaField mf, MetaClass type, String factoryName) {
        boolean collection = mf.getType().isCollection();
        String visibility = String.join(" ", mf.getVisibilityModifiers());
        String returnedType = collection ? type.getSimpleName() : "void";

        List<MetaField> requiredFields = getAllRequiredFields(type);
        List<String> params = toParameters(requiredFields);
        String parameters = String.join(", ", params);

        w.println("{0} {1} {2}({3}) '{'", visibility, returnedType, factoryName, parameters);
        w.indent();
        generateFactoryBody(w, mf, type, requiredFields);
        w.unindent();
        w.println("}");
        w.println();
    }

    protected void generateFactoryBody(FormatWriter w, MetaField mf, MetaClass type, List<MetaField> requiredFields) {
        String typeName = type.getSimpleName();
        String instance = StringUtil.uncapitalize(typeName);

        String prefix = mf.isStatic() ? mf.getDeclaringClass().getSimpleName() : "this";
        List<String> argumentList = toNameList(requiredFields);
        String arguments = String.join(", ", argumentList);
        String allArguments = requiredFields.isEmpty() ? "this" : "this, " + arguments;
        boolean collection = mf.getType().isCollection();
        boolean optional = mf.isOptional();
        String value = optional ? "Optional.of(" + instance + ")" : instance;

        w.println("{0} {1} = new {0}({2});", typeName, instance, allArguments);

        if (collection) {
            w.println("{0}.{1}.add({2});", prefix, mf.getName(), instance);
        } else {
            w.println("{0}.{1} = {2};", prefix, mf.getName(), value);
        }

        if (collection) {
            w.println("return {0};", instance);
        }
    }

    protected void generateAdder(FormatWriter w, MetaField mf) {
        String visibility = String.join(" ", mf.getVisibilityModifiers());
        String name = StringUtil.capitalize(mf.getName());
        String itemType = mf.getItemType().getSimpleName();
        String parameter = StringUtil.uncapitalize(itemType);

        w.println("{0} void addTo{1}({2} {3}) '{'", visibility, name, itemType, parameter);
        w.printlnIndented("this.{0}.add({1});", mf.getName(), parameter);
        w.println("}");
        w.println();
    }

    protected void generateRemover(FormatWriter w, MetaField mf) {
        String visibility = String.join(" ", mf.getVisibilityModifiers());
        String name = StringUtil.capitalize(mf.getName());
        String itemType = mf.getItemType().getSimpleName();
        String parameter = StringUtil.uncapitalize(itemType);

        w.println("{0} void removeFrom{1}({2} {3}) '{'", visibility, name, itemType, parameter);
        w.printlnIndented("this.{0}.remove({1});", mf.getName(), parameter);
        w.println("}");
        w.println();
    }

    protected void generateMetaAccessors(FormatWriter w, MetaClass mc) {
        if (generateMetadata) {
            List<MetaField> fields = mc.getVariables();
            List<String> metaFields = new ArrayList<>();

            w.println("public static Field[] getFields() {");
            w.indent();
            w.print("return new Field[] {");
            for (MetaField field : fields) {
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

    protected void generateMetaGetter(FormatWriter w) {
        w.println("/**");
        w.println(" * @param field");
        w.println(" * @return the value for this field");
        w.println(" */");
        w.println("public Object get(Field field) throws IllegalAccessException {");
        w.printlnIndented("return field.get(this);");
        w.println("}");
        w.println();
    }

    protected void generateMetaSetter(FormatWriter w) {
        w.println("/**");
        w.println(" * @param field");
        w.println(" * @param value to be assigned");
        w.println(" */");
        w.println("public void set(Field field, Object value) throws IllegalAccessException {");
        w.printlnIndented("field.set(this, value);");
        w.println("}");
        w.println();
    }

    protected void generateIdentityMethods(FormatWriter w, MetaClass mc) {
        generateEquals(w, mc);
        generateHashCode(w, mc);
        generateIsEqualTo(w, mc);
    }

    protected void generateEquals(FormatWriter w, MetaClass mc) {
        w.println("@Override");
        w.println("public boolean equals(Object other) {");
        w.indent();
        generateEqualsBody(w, mc);
        w.unindent();
        w.println("}");
        w.println();
    }

    protected void generateEqualsBody(FormatWriter w, MetaClass mc) {
        String name = mc.getSimpleName();
        w.println("boolean equal = false;");
        w.println();

        w.println("if (other instanceof {0}) '{'", name);
        w.printlnIndented("{0} that = ({0})other;", name);
        w.printlnIndented("equal = (hashCode() == that.hashCode()) && isEqualTo(that);");
        w.println("}");
        w.println();

        w.println("return equal;");
    }

    protected void generateHashCode(FormatWriter w, MetaClass mc) {
        w.println("@Override");
        w.println("public int hashCode() {");
        w.indent();
        generateHashCodeBody(w, mc);
        w.unindent();
        w.println("}");
        w.println();
    }

    protected void generateHashCodeBody(FormatWriter w, MetaClass mc) {
        List<String> hashList = getGetterList(mc);

        if (mc.hasSuperClass()) {
            hashList.add("super.hashCode()");
        }

        String fields = String.join(", ", hashList);
        w.println(MessageFormat.format("return Objects.hash({0});", fields));
    }

    protected void generateIsEqualTo(FormatWriter w, MetaClass mc) {
        String name = mc.getSimpleName();
        w.println("protected boolean isEqualTo({0} that) '{'", name);
        w.indent();
        generateIsEqualToBody(w, mc);
        w.unindent();
        w.println("}");
        w.println();
    }

    protected void generateIsEqualToBody(FormatWriter w, MetaClass mc) {
        List<MetaField> fields = mc.getVariables();
        w.println("boolean equal = true;");

        for (MetaField field : fields) {
            String getter = getGetterName(field);

            if (field.getType().isPrimitive()) {
                w.println("equal = equal && {0}() == that.{0}();", getter);
            } else {
                w.println(
                        "equal = equal && {0}() == null ? that.{0}() == null : {0}().equals(that.{0}());",
                        getter);
            }
        }

        if (mc.hasSuperClass()) {
            w.println("equal = equal && super.isEqualTo(that);");
        }

        w.println("return equal;");
    }

    protected void generateToString(FormatWriter w, MetaClass mc) {
        List<MetaField> allVariables = mc.getAllVariables();

        w.println("@Override");
        w.println("public String toString() {");
        w.printlnIndented("StringBuilder sb = new StringBuilder();");
        w.printlnIndented("sb.append(\"{\");");

        for (MetaField field : allVariables) {
            String name = field.getName();
            String getter = getGetterName(field);
            w.printlnIndented("sb.append(\"{0} = \").append({1}()).append(\", \");", name, getter);
        }

        w.printlnIndented("sb.append(\"}\");");
        w.printlnIndented("return sb.toString();");
        w.println("}");
        w.println();
    }

    private List<MetaField> getAllRequiredFields(MetaClass mc) {
        boolean immutable = mc.isImmutable();
        List<MetaField> allVariables = mc.getAllVariables();
        List<MetaField> fields = allVariables.stream()
                .filter(mf -> immutable || ! mf.isOptional())
                .toList();
        return fields;
    }

    private List<MetaField> getRequiredFields(MetaClass mc) {
        boolean immutable = mc.isImmutable();
        List<MetaField> allVariables = mc.getVariables();
        List<MetaField> fields = allVariables.stream()
                .filter(mf -> immutable || ! mf.isOptional())
                .toList();
        return fields;
    }

    private List<MetaField> getConstructorFields(MetaClass mc) {
        MetaClass superClass = (mc == null) ? null : mc.getSuperClass();
        List<MetaField> constructorFields = (superClass != null) ? getConstructorFields(superClass): new ArrayList<>();

        MetaField reference = getReferenceForClass(mc);
        if (reference != null) {
            constructorFields.add(reference);
        }

        List<MetaField> requiredFields = (mc == null) ? new ArrayList<>() : getRequiredFields(mc);
        requiredFields = requiredFields.stream()
                .filter(mf -> ! mf.isStatic())
                .filter(mf -> ! mf.isComponent())
                .filter(mf -> ! mf.getType().isCollection())
                .toList();

        constructorFields.addAll(requiredFields);
        return constructorFields;
    }

    protected List<String> getGetterList(MetaClass mc) {
        List<String> getterList = new ArrayList<>();
        List<MetaField> variables = mc.getVariables();

        for (MetaField variable : variables) {
            getterList.add(getGetterName(variable) + "()");
        }

        return getterList;
    }

    private List<String> toNameList(List<MetaField> fields) {
        List<String> nameList = new ArrayList<>() ;

        for (MetaField mf : fields) {
            nameList.add(mf.getName());
        }

        return nameList;
    }

    private List<String> toParameters(List<MetaField> fields) {
        List<String> parameters = new ArrayList<>();

        for (MetaField mf : fields) {
            parameters.add(mf.getTypeName() + " " + mf.getName());
        }

        return parameters;
    }

    private void verifyNullArgument(FormatWriter w, MetaField mf) {
        w.println("if ({0} == null) '{'", mf.getName());
        w.printlnIndented(
                "throw new IllegalArgumentException (\"Parameter ''{0}'' cannot be null\");", mf.getName());
        w.println("}");
        w.println();
    }
}
