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

    protected abstract MetaField getReferenceForClass(MetaClass mc);

    protected abstract String getGetterName(MetaField mf);

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

    protected List<String> getGetterList(MetaClass mc) {
        List<String> getterList = new ArrayList<>();
        List<MetaField> variables = mc.getVariables();

        for (MetaField variable : variables) {
            getterList.add(getGetterName(variable) + "()");
        }

        return getterList;
    }
}
