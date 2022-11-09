package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.FormatWriter;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.util.collection.SortedList;
import com.marcosavard.commons.util.collection.UniqueList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReflectivePojoGenerator extends PojoGenerator {

  private DynamicPackage dynamicPackage;

  public ReflectivePojoGenerator(File outputFolder, Class<?>[] classes) {
    super(outputFolder);
    dynamicPackage = new DynamicPackage(classes);
  }

  public List<File> generate() throws IOException {
    dynamicPackage.buildReferenceByClass(containerName);
    List<File> generatedFiles = new ArrayList<>();

    for (Class claz : dynamicPackage.classes) {
      MetaClass mc = MetaClass.of(claz);
      generatedFiles.add(generateClass(mc));
    }

    return generatedFiles;
  }

  public File generate(Class<?> claz) throws IOException {
    MetaClass mc = MetaClass.of(claz);
    return generateClass(mc);
  }

  @Override
  public File generateClass(MetaClass mc) throws IOException {
    Console.println("Generating code for {0}", mc.getSimpleName());

    // create folder
    String packageName = mc.getPackageName();
    String folderName = packageName.replace(".", "//");
    File subfolder = new File(outputFolder, folderName);
    subfolder.mkdirs();

    // create file
    String filename = mc.getSimpleName() + ".java";
    File generated = new File(subfolder, filename);
    Writer w = new FileWriter(generated);
    FormatWriter fw = new FormatWriter(w, indentation);

    // generate code
    generateType(fw, packageName, mc);
    fw.close();

    return generated;
  }


  private void generateType(FormatWriter w, String packageName, MetaClass mc) {
    w.println("package {0};", packageName);
    w.println();

    if (mc.isEnum()) {
      generateEnum(w, mc);
    } else {
      generateClass(w, mc);
    }
  }

  private void generateEnum(FormatWriter w, MetaClass mc) {
    w.println("public enum {0} '{'", mc.getSimpleName());
    w.indent();
    generateLiterals(w, mc);
    w.unindent();
    w.println("}");
  }

  private void generateLiterals(FormatWriter w, MetaClass mc) {
    Field[] fields = mc.getClaz().getDeclaredFields();

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

  private void generateClass(FormatWriter w, MetaClass mc) {
    Class claz = mc.getClaz();
    String modifiers = getModifiers(claz);
    Class<?> superClass = claz.getSuperclass();

    generateImports(w, claz);
    generateClassComment(w, claz);
    w.print("{0} class {1}", modifiers, claz.getSimpleName());

    if ((superClass != null) && !superClass.equals(Object.class)) {
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

    if (generateMetadata) {
      importees.add(Field.class);
    }

    for (Field field : fields) {
      Class<?> type = field.getType();
      boolean collection = dynamicPackage.isCollection(type);

      if (dynamicPackage.isAddable(pack, type)) {
        importees.add(type);
      }

      if (collection) {
        Class<?> itemType = dynamicPackage.getItemType(field);
        if (!itemType.getPackage().equals(pack)) {
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

    if (!fields.isEmpty()) {
      w.println();
    }
  }

  private void generateVariables(FormatWriter w, Class<?> claz) {
    DynamicPackage.Reference reference = dynamicPackage.getReferenceByClass().get(claz);
    List<Field> fields = getVariables(claz);

    if (reference != null) {
      generateReference(w, reference);
    }

    for (Field field : fields) {
      generateField(w, field);
    }

    if (!fields.isEmpty()) {
      w.println();
    }
  }

  private void generateReference(FormatWriter w, DynamicPackage.Reference reference) {
    Class<?> type = reference.getOppositeField().getDeclaringClass();
    String typeName = type.getSimpleName();
    w.println("private {0} {1};", typeName, reference.getName());
  }

  private void generateField(FormatWriter w, Field field) {
    List<String> modifierList = new ArrayList<>();
    modifierList.add("private");
    modifierList.addAll(getModifiers(field));
    String modifiers = String.join(" ", modifierList);

    Class<?> type = field.getType();
    boolean collection = dynamicPackage.isCollection(type);
    boolean optional = dynamicPackage.isOptional(field);

    Class<?> itemType = (collection || optional) ? dynamicPackage.getItemType(field) : null;
    String typeName = getTypeName(type, itemType);
    String initValue = dynamicPackage.getInitialValue(field);

    if (initValue == null) {
      w.println("{0} {1} {2};", modifiers, typeName, field.getName());
    } else {
      w.println("{0} {1} {2} = {3};", modifiers, typeName, field.getName(), initValue);
    }
  }

  private void generateMetaFields(FormatWriter w, Class<?> claz) {
    if (generateMetadata) {
      List<Field> fields = getVariables(claz);
      String className = claz.getSimpleName();

      for (Field field : fields) {
        String metaField = StringUtil.camelToUnderscore(field.getName()) + "_FIELD";
        w.println("public static final Field {0};", metaField);
      }

      if (!fields.isEmpty()) {
        w.println();
        w.println("static {");
        w.indent();
        w.println("try {");

        for (Field field : fields) {
          String name = field.getName();
          String metaField = StringUtil.camelToUnderscore(name) + "_FIELD";
          w.printlnIndented(
              "{0} = {1}.class.getDeclaredField(\"{2}\");", metaField, className, name);
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
    List<Member> constructorParameters = findConstructorParameters(claz, true);

    if (hasRequiredComponent(claz)) {
      generateOfMethods(w, claz, constructorParameters);
    }

    if (!constructorParameters.isEmpty()) {
      generateParameterlessConstructor(w, claz);
      String visibility = dynamicPackage.isAbstract(claz) ? "protected" : "public";
      String className = claz.getSimpleName();
      List<Member> superClassMembers = dynamicPackage.getSuperClassMembers(claz, true);

      w.println("/**");
      for (Member parameter : constructorParameters) {
        w.println(" * @param " + getDescription(parameter));
      }
      w.println(" */");

      w.print("{0} {1}(", visibility, className);
      w.print(String.join(", ", getMemberDeclarations(constructorParameters)));
      w.println(") {");
      w.indent();
      generateConstructorBody(w, superClassMembers, constructorParameters);
      w.unindent();
      w.println("}");
      w.println();
    }
  }

  private void generateParameterlessConstructor(FormatWriter w, Class<?> claz) {
    if (generateParameterlessConstructor) {
      String className = claz.getSimpleName();
      w.println("{0} {1}() '{'", "public", className);
      generateParameterlessConstructorBody(w, claz);
      w.println("}");
      w.println();
    }
  }

  private void generateParameterlessConstructorBody(FormatWriter w, Class<?> claz) {
    List<Member> requiredMembers = dynamicPackage.getRequiredMembers(claz, true);

    w.indent();
    for (Member member : requiredMembers) {
      boolean field = (member instanceof Field);
      String value = field ? dynamicPackage.getInitialValueOfVariable((Field)member) : getDefaultValue(member);
      w.println("this.{0} = {1};", member.getName(), value);
    }
    w.unindent();
  }

  private String getDefaultValue(Member member) {
    Class type = getType(member);
    String value = "null";

    if (double.class.equals(type)) {
      value = "0.0";
    } else if (int.class.equals(type)) {
      value = "0";
    } else if (long.class.equals(type)) {
      value = "0L";
    } else if ((member instanceof Field f) && Enum.class.isAssignableFrom(type)) {
      value = dynamicPackage.getInitialValueOfVariable(f);
    }

    return value;
  }

  private void generateOfMethods(FormatWriter w, Class<?> claz, List<Member> constructorParameters) {
    List<Field> requiredComponents = getRequiredComponents(claz);
    List<List<? extends Member>> signatures = dynamicPackage.findConcreteFieldSignatures(requiredComponents);

    for (List<? extends Member> signature : signatures) {
      generateOfMethod(w, claz, constructorParameters, signature);
    }
  }

  private void generateOfMethod(FormatWriter w, Class<?> claz, List<Member> constructorParameters, List<? extends Member> signature) {
    List<String> names = getFieldNames(signature);
    String methodName = "of" + String.join("And", names);
    List<String> parameters = new ArrayList<>();
    parameters.addAll(getMemberDeclarations(constructorParameters));

    for (Member member : signature) {
      Class type = getType(member);
      List<? extends Member> requiredMembers = getAllRequiredMembers(type);
      for (Member requiredMember : requiredMembers) {
        String typeName = getType(requiredMember).getSimpleName();
        String paramName = member.getName() + StringUtil.capitalize(requiredMember.getName());
        parameters.add(typeName + " " + paramName);
      }
    }

    String parameterStr = String.join(", ", parameters);

    w.println("public static {0} {1}({2}) '{'", claz.getSimpleName(), methodName, parameterStr);
    generateOfMethodBody(w, claz, constructorParameters, signature);
    w.println("}");
    w.println();
  }

  private List<String> getFieldNames(List<? extends Member> signature) {
    List<String> fieldNames = new ArrayList<>();

    for (Member member : signature) {
      Class type = getType(member);
      String name = StringUtil.capitalize(member.getName());
      fieldNames.add(type.getSimpleName() + name);
    }

    return fieldNames;
  }

  private void generateOfMethodBody(FormatWriter w, Class<?> claz, List<Member> constructorParameters, List<? extends Member> signature) {
    w.indent();
    String instance = StringUtil.uncapitalize(claz.getSimpleName());
    String arguments = String.join(", ", dynamicPackage.getMemberNames(constructorParameters));
    w.println("{0} {1} = new {0}({2});", claz.getSimpleName(), instance, arguments);

    for (Member member : signature) {
      List<String> memberArguments = new ArrayList<>();
      memberArguments.add(instance);
      Class type = getType(member);
      List<? extends Member> requiredMembers = getAllRequiredMembers(type);

      for (Member typeParameter : requiredMembers) {
        String paramName = member.getName() + StringUtil.capitalize(typeParameter.getName());
        memberArguments.add(paramName);
      }

      String args = String.join(", ", memberArguments);
      w.println("{0}.{1} = new {2}({3});", instance, member.getName(), type.getSimpleName(), args);
    }

    w.println("return {0};", instance);
    w.unindent();
  }

  private List<String> getClassNames(List<? extends Member> members) {
    List<String> classNames = new ArrayList<>();

    for (Member member : members) {
      Class type = getType(member);
      classNames.add(type.getSimpleName());
    }

    return classNames;
  }

  private List<List<Class>> findConcreteSubclasses(List<Class> types) {
    List<List<Class>> concreteSubclasses = new ArrayList<>();

    for (Class type : types) {
      if (dynamicPackage.isAbstract(type)) {
        List<Class> subclasses = getSubclasses(dynamicPackage.classes, type);
      }
    }

    return concreteSubclasses;
  }

  private List<Class> getConcreteTypes(List<Class> types) {
    List<Class> concreteTypes = new ArrayList<>();

    for (Class type : types) {
      if (dynamicPackage.isAbstract(type)) {
        List<Class> subclasses = getSubclasses(dynamicPackage.classes, type);
        concreteTypes.addAll(getConcreteTypes(subclasses));
      } else {
        concreteTypes.add(type);
      }
    }

    return concreteTypes;
  }

  private List<Class> getMemberTypes(List<? extends Member> members) {
    List<Class> memberTypes = new ArrayList<>();

    for (Member member : members) {
      if (member instanceof Field field) {
        memberTypes.add(field.getType());
      }
    }

    return memberTypes;
  }

  private boolean hasRequiredComponent(Class<?> claz) {
    return ! getRequiredComponents(claz).isEmpty();
  }

  private List<Field> getRequiredComponents(Class<?> claz) {
    List<? extends Member> readOnlyMembers = getAllRequiredMembers(claz);
    List<Field> readOnlyFields = new ArrayList<>();

    for (Member member : readOnlyMembers) {
      if (member instanceof Field field) {
        readOnlyFields.add(field);
      }
    }

    List<Field> requiredComponents = readOnlyFields.stream()
            .filter(f -> dynamicPackage.isComponent(f))
            .toList();
    return requiredComponents;
  }

  private List<Member> findConstructorParameters(Class<?> claz, boolean includeParent) {
    List<Member> constructorParameters = new UniqueList<>();
    List<DynamicPackage.Reference> parentReferences = includeParent ? dynamicPackage.getParentReferences(claz) : new ArrayList<>();
    List<Member> superClassMembers = dynamicPackage.getSuperClassMembers(claz, includeParent);
    List<Member> requiredMembers = dynamicPackage.getRequiredMembers(claz, false);
    List<String> superClassMemberNames = dynamicPackage.getMemberNames(superClassMembers);

    requiredMembers = requiredMembers.stream()
            .filter(m -> (m instanceof Field f) && !dynamicPackage.isComponent(f))
            .filter(m -> ! superClassMemberNames.contains(m.getName()))
            .toList();

    constructorParameters.addAll(parentReferences);
    constructorParameters.addAll(superClassMembers);
    constructorParameters.addAll(requiredMembers);
    return constructorParameters;
  }


  private void generateConstructorBody(FormatWriter w, List<Member> superClassMembers, List<Member> parameters) {

    //generate the super
    if (!superClassMembers.isEmpty()) {
      List<String> referenceNames = getReferenceNames(superClassMembers);
      w.println("super(" + String.join(", ", referenceNames) + ");");
    }

    List<Member> settableParameters = new ArrayList<>(parameters);
    settableParameters.removeAll(superClassMembers);

    for (Member m : settableParameters) {
      Class type = getType(m);
      if (! dynamicPackage.isPrimitive(type) && ! dynamicPackage.isOptional(m)) {
        verifyNullArgument(w, m);
      }
    }

    for (Member m : settableParameters) {
      w.println("this.{0} = {0};", m.getName());
    }
  }

  private void verifyNullArgument(FormatWriter w, Member m) {
    w.println("if ({0} == null) '{'", m.getName());
    w.printlnIndented(
        "throw new IllegalArgumentException (\"Parameter ''{0}'' cannot be null\");" ,m.getName());
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
    boolean immutable = dynamicPackage.isImmutable(claz);

    for (Field field : fields) {
      generateGetter(w, field);

      if (!immutable && (!dynamicPackage.isReadOnly(field))) {
        generateSetter(w, field);
      }
    }

    w.println();
  }

  private void generateAccessorsGroupedByGettersSetters(FormatWriter w, Class<?> claz) {
    List<Field> fields = getVariables(claz);
    boolean immutable = dynamicPackage.isImmutable(claz);

    for (Field field : fields) {
      generateGetter(w, field);
    }

    for (Field field : fields) {
      if (!immutable && (!dynamicPackage.isReadOnly(field))) {
        generateSetter(w, field);
      }
    }

    w.println();
  }

  private void generateGetter(FormatWriter w, Field field) {
    List<String> modifiers = new ArrayList<>();
    modifiers.addAll(getVisibilityModifiers(field));
    modifiers.addAll(getModifiers(field));
    Class<?> type = field.getType();
    boolean collection = dynamicPackage.isCollection(type);
    boolean optional = dynamicPackage.isOptional(field);

    Class<?> itemType = collection || optional ? dynamicPackage.getItemType(field) : null;
    String typeName = optional ? itemType.getSimpleName() : getTypeName(type, itemType);
    String getter = getGetterName(field);
    String orElseNull = optional ? ".orElse(null)" : "";

    w.println("/**");
    w.println(" * @return " + getDescription(field));
    w.println(" */");
    w.println("{0} {1} {2}() '{'", String.join(" ", modifiers), typeName, getter);
    w.printlnIndented("return {0}{1};", field.getName(), orElseNull);
    w.println("}");
    w.println();
  }

  private void generateSetter(FormatWriter w, Field field) {
    boolean settable = ! dynamicPackage.isConstant(field);
    Class<?> type = field.getType();
    boolean collection = dynamicPackage.isCollection(type);
    boolean component = dynamicPackage.isComponent(field);

    if (settable) {
      if (collection) {
        generateAddersRemovers(w, field);
      } else {
        if (component) {
            generateFactories(w, field);
        } else {
          generateBasicSetter(w, field);
        }
      }
    }
  }

  private void generateBasicSetter(FormatWriter w, Field field) {
    List<String> modifiers = new ArrayList<>();
    modifiers.addAll(getVisibilityModifiers(field));
    modifiers.addAll(getModifiers(field));
    String prefix = dynamicPackage.isStatic(field) ? field.getDeclaringClass().getSimpleName() : "this";
    String name = field.getName();
    String methodName = "set" + StringUtil.capitalize(name);
    boolean optional = dynamicPackage.isOptional(field);
    Class type = optional ? dynamicPackage.getItemType(field) : getType(field);
    String typeName = optional ? type.getSimpleName() : getTypeName(field);
    String value = optional ? "Optional.of(" + name + ")" : name;

    w.println("/**");
    w.println(" * @param " + getDescription(field));
    w.println(" */");
    w.println("{0} void {1}({2} {3}) '{'", String.join(" ", modifiers), methodName, typeName, name);
    w.indent();

    if (! dynamicPackage.isPrimitive(type) && ! optional) {
      verifyNullArgument(w, field);
    }

    w.println("{0}.{1} = {2};", prefix, name, value);
    w.unindent();
    w.println("}");
    w.println();
  }

  private void generateAddersRemovers(FormatWriter w, Field field) {
    boolean component = dynamicPackage.isComponent(field) && dynamicPackage.isOptional(field);

    if (component) {
      generateFactories(w, field);
    } else {
      generateAdder(w, field);
    }

    generateRemover(w, field);
  }

  private void generateFactories(FormatWriter w, Field field) {
    Class fieldType = field.getType();
    boolean collection = dynamicPackage.isCollection(fieldType);
    boolean optional = dynamicPackage.isOptional(field);
    Class type = collection || optional ? dynamicPackage.getItemType(field) : fieldType;
    String verb = collection ? "create" : "set";
    String fieldName = StringUtil.capitalize(field.getName());

    if (dynamicPackage.isAbstract(type)) {
      List<Class> subclasses = getSubclasses(dynamicPackage.classes, type);

      for (Class subclass : subclasses) {
        String typeName =  StringUtil.capitalize(subclass.getSimpleName());
        String factoryName = verb + fieldName + typeName;
        generateFactory(w, field, subclass, factoryName);
      }
    } else {
      String factoryName = verb + type.getSimpleName();
      generateFactory(w, field, type, factoryName);
    }
  }

  private void generateFactory(FormatWriter w, Field field, Class type, String factoryName) {
    Class fieldType = field.getType();
    boolean collection = dynamicPackage.isCollection(fieldType);
    String visibility = String.join(" ", getVisibilityModifiers(field));
    String returnedType = collection ? type.getSimpleName() : "void";

    List<Member> constructorParameters = findConstructorParameters(type, false);
    String parameters = String.join(", ", getMemberDeclarations(constructorParameters));
    List<? extends Member> readOnlyFields = getAllReadOnlyMembers(type);

    w.println("{0} {1} {2}({3}) '{'", visibility, returnedType, factoryName, parameters);
    w.indent();
    generateFactoryBody(w, field, type, constructorParameters, readOnlyFields);
    w.unindent();
    w.println("}");
    w.println();
  }

  private void generateFactoryBody(FormatWriter w, Field field, Class type, List<Member> constructorParameters, List<? extends Member> readOnlyMembers) {
    String typeName = type.getSimpleName();
    String instance = StringUtil.uncapitalize(typeName);

    List<Field> fields = new ArrayList<>();
    for (Member member : constructorParameters) {
      if (member instanceof Field) {
        fields.add((Field)member);
      }
    }

    String prefix = dynamicPackage.isStatic(field) ? field.getDeclaringClass().getSimpleName() : "this";
    String arguments = String.join(", ", dynamicPackage.getMemberNames(fields));
    String allArguments = fields.isEmpty() ? "this" : "this, " + arguments;
    boolean collection = dynamicPackage.isCollection(field.getType());
    boolean optional = dynamicPackage.isOptional(field);
    String value = optional ? "Optional.of(" + instance + ")" : instance;

    w.println("{0} {1} = new {0}({2});", typeName, instance, allArguments);

    if (collection) {
      w.println("{0}.{1}.add({2});", prefix, field.getName(), instance);
    } else {
      w.println("{0}.{1} = {2};", prefix, field.getName(), value);
    }

    if (collection) {
      w.println("return {0};", instance);
    }
  }

  private void generateAdder(FormatWriter w, Field field) {
    String visibility = String.join(" ", getVisibilityModifiers(field));
    String name = StringUtil.capitalize(field.getName());
    String itemType = dynamicPackage.getItemType(field).getSimpleName();
    String parameter = StringUtil.uncapitalize(itemType);

    w.println("{0} void addTo{1}({2} {3}) '{'", visibility, name, itemType, parameter);
    w.printlnIndented("this.{0}.add({1});", field.getName(), parameter);
    w.println("}");
    w.println();
  }

  private void generateRemover(FormatWriter w, Field field) {
    String visibility = String.join(" ", getVisibilityModifiers(field));
    String name = StringUtil.capitalize(field.getName());
    String itemType = dynamicPackage.getItemType(field).getSimpleName();
    String parameter = StringUtil.uncapitalize(itemType);

    w.println("{0} void removeFrom{1}({2} {3}) '{'", visibility, name, itemType, parameter);
    w.printlnIndented("this.{0}.remove({1});", field.getName(), parameter);
    w.println("}");
    w.println();
  }

  private void generateMetaAccessors(FormatWriter w, Class<?> claz) {
    if (generateMetadata) {
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

    if (dynamicPackage.hasSuperClass(claz)) {
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
      if (dynamicPackage.isPrimitive(field.getType())) {
        w.println("equal = equal && {0}() == that.{0}();", getter);
      } else {
        w.println(
            "equal = equal && {0}() == null ? that.{0}() == null : {0}().equals(that.{0}());",
            getter);
      }
    }

    if (dynamicPackage.hasSuperClass(claz)) {
      w.println("equal = equal && super.isEqualTo(that);");
    }

    w.println("return equal;");
  }

  private void generateToString(FormatWriter w, Class<?> claz) {
    List<Field> fields = getAllVariables(claz);

    w.println("@Override");
    w.println("public String toString() {");
    w.printlnIndented("StringBuilder sb = new StringBuilder();");
    w.printlnIndented("sb.append(\"{\");");

    for (Field field : fields) {
      String name = field.getName();
      String getter = getGetterName(field);
      w.printlnIndented("sb.append(\"{0} = \").append({1}()).append(\", \");", name, getter);
    }

    w.printlnIndented("sb.append(\"}\");");
    w.printlnIndented("return sb.toString();");
    w.println("}");
    w.println();
  }

  private List<String> getReferenceNames(List<Member> members) {
    List<String> memberNames = new ArrayList<>();

    for (Member member : members) {
      memberNames.add(member.getName());
    }

    return memberNames;
  }

  private List<Class> getSubclasses(Class[] classes, Class givenClass) {
    List<Class> subClasses = new ArrayList<>();

    for (Class claz : classes) {
      if (claz.getSuperclass().equals(givenClass)) {
        subClasses.add(claz);
      }
    }

    return subClasses;
  }



  /*
  private List<Field> getSuperClassReadOnlyFields(Class<?> claz) {
    List<Field> allReadOnlyFields = getAllReadOnlyFields(claz);
    List<Field> fields = getReadOnlyFields(claz);
    List<Field> superclassFields = new ArrayList<>(allReadOnlyFields);
    superclassFields.removeAll(fields);
    return superclassFields;
  }*/

  private List<String> getMemberDeclarations(List<? extends Member> members) {
    List<String> declarations = new UniqueList<>();

    for (Member member : members) {
      String name = member.getName();
      Class type = getType(member);
      declarations.add(type.getSimpleName() + " " + name);
    }

    return declarations;
  }

  public Class getType(Member member) {
    Class type = null;

    if (member instanceof DynamicPackage.Reference) {
      DynamicPackage.Reference ref = (DynamicPackage.Reference) member;
      type = ref.getOppositeField().getDeclaringClass();
    } else {
      type = dynamicPackage.getType(member);
    }

    return type;
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
    String desc = (description == null) ? "" : description.value();
    return name + " " + desc;
  }

  private String getDescription(Member member) {
    String name = member.getName();
    Description description = null;
    Class type = null;

    if (member instanceof Field) {
      Field field = (Field)member;
      description = field.getAnnotation(Description.class);
      type = field.getType();
    } else if (member instanceof DynamicPackage.Reference) {
      DynamicPackage.Reference reference = (DynamicPackage.Reference)member;
      type = reference.getOppositeField().getDeclaringClass();
    }

    String desc = (description == null) ? type.getSimpleName() : description.value();
    return name + " " + desc;
  }

  private List<Field> getConstants(Class<?> claz) {
    return Arrays.stream(claz.getDeclaredFields()).filter(f -> dynamicPackage.isConstant(f)).toList();
  }

  private List<Field> getAllVariables(Class<?> claz) {
    return Arrays.stream(claz.getFields()).filter(f -> !dynamicPackage.isConstant(f)).toList();
  }

  private List<Field> getVariables(Class<?> claz) {
    return Arrays.stream(claz.getDeclaredFields()).filter(f -> !dynamicPackage.isConstant(f)).toList();
  }

  private List<? extends Member> getAllReadOnlyMembers(Class<?> claz) {
    boolean immutable = dynamicPackage.isImmutable(claz);
    List<Field> readOnlyFields = Arrays.stream(claz.getFields())
            .filter(f -> immutable || dynamicPackage.isReadOnly(f))
            .toList();

    List<Member> readOnlyMembers = new ArrayList<>();
    readOnlyMembers.addAll(readOnlyFields);
    return readOnlyMembers;
  }

  private List<? extends Member> getAllRequiredMembers(Class<?> claz) {
    boolean immutable = dynamicPackage.isImmutable(claz);
    List<Field> requiredFields = Arrays.stream(claz.getFields())
            .filter(f -> immutable || ! dynamicPackage.isOptional(f))
            .toList();

    List<Member> requiredMembers = new ArrayList<>();
    requiredMembers.addAll(requiredFields);
    return requiredMembers;
  }



  private List<Field> getNotNullFields(Class<?> claz) {
    return Arrays.stream(claz.getDeclaredFields()).filter(f -> ! dynamicPackage.isOptional(f)).toList();
  }

  private String getTypeName(Field field) {
    Class<?> type = field.getType();
    boolean collection = dynamicPackage.isCollection(type);
    Class<?> itemType = collection ? dynamicPackage.getItemType(field) : null;
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

    return getterList;
  }

  private String getGetterName(Field field) {
    Class<?> type = field.getType();
    String verb = type.equals(boolean.class) ? "is" : "get";
    return verb + StringUtil.capitalize(field.getName());
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

  private List<String> getVisibilityModifiers(Field field) {
    List<String> modifiers = new NotNullList<>();
    boolean isPublic = Modifier.isPublic(field.getModifiers());
    boolean isProtected = Modifier.isProtected(field.getModifiers());
    boolean isPrivate = Modifier.isPrivate(field.getModifiers());
    modifiers.add(isPublic ? "public" : null);
    modifiers.add(isProtected ? "protected" : null);
    modifiers.add(isPrivate ? "private" : null);
    return modifiers;
  }

  private List<String> getModifiers(Field field) {
    List<String> modifiers = new NotNullList<>();
    Class<?> claz = field.getDeclaringClass();
    boolean immutable = dynamicPackage.isImmutable(claz);
    boolean isStatic = Modifier.isStatic(field.getModifiers());
    boolean isFinal = Modifier.isFinal(field.getModifiers()) || dynamicPackage.isReadOnly(field) || immutable;
    modifiers.add(isStatic ? "static" : null);
    modifiers.add(isFinal ? "final" : null);
    return modifiers;
  }

  private static class ImportComparator implements Comparator<Class<?>> {
    @Override
    public int compare(Class c1, Class c2) {
      String n1 = c1.getName();
      String n2 = c2.getName();
      return n1.compareTo(n2);
    }
  }

  private static class NotNullList<E> extends ArrayList<E> {
    @Override
    public boolean add(E e) {
      boolean added = false;

      if (e != null) {
        added = super.add(e);
      }

      return added;
    }
  }
 }
