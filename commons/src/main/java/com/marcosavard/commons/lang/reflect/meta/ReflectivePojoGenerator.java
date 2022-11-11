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
    MetaField[] fields = mc.getDeclaredFields();

    for (MetaField field : fields) {
      generateLiteral(w, field);
    }
  }

  private void generateLiteral(FormatWriter w, MetaField field) {
    if (field.isPublic()) {
      w.println("{0},", field.getName());
    }
  }

  private void generateClass(FormatWriter w, MetaClass mc) {
    List<String> modifierList = new ArrayList<>();
    modifierList.addAll(mc.getVisibilityModifiers());
    modifierList.addAll(mc.getOtherModifiers());
    String modifiers = String.join(" ", modifierList);
    MetaClass superClass = mc.getSuperClass();

    generateImports(w, mc);
    generateClassComment(w, mc);
    w.print("{0} class {1}", modifiers, mc.getSimpleName());

    if ((superClass != null) && !superClass.equals(MetaClass.of(Object.class))) {
      w.print(" extends {0}", superClass.getSimpleName());
    }

    w.println(" {");
    w.indent();

    generateConstants(w, mc);
    generateVariables(w, mc);
    generateMetaFields(w, mc);
    generateConstructor(w, mc);
    generateMethods(w, mc);
    w.unindent();
    w.println("}");
  }

  private void generateImports(FormatWriter w, MetaClass mc) {
    MetaPackage mp = mc.getPackage();
    MetaField[] fields = mc.getDeclaredFields();

    Comparator<MetaClass> comparator = new ImportComparator();
    List<MetaClass> importees = new SortedList<>(comparator);
    importees.add(MetaClass.of(Objects.class));

    if (generateMetadata) {
      importees.add(MetaClass.of(Field.class));
    }

    for (MetaField field : fields) {
      MetaClass fieldType = field.getType();
      MetaPackage fieldPackage = fieldType.getPackage();

      boolean importable = !("".equals(fieldPackage.getName()))
              && !("java.lang".equals(fieldPackage.getName()))
              && ! fieldPackage.equals(mp);
      boolean collection = fieldType.isCollection();

      if (importable) {
        importees.add(fieldType);
      }

      if (collection) {
        MetaClass itemType = field.getItemType();

        if (! itemType.getPackage().equals(mp)) {
          importees.add(itemType);
        }
      }
    }

    if (importees.contains(MetaClass.of(List.class))) {
      importees.add(MetaClass.of(ArrayList.class));
    }

    if (importees.size() > 0) {
      for (MetaClass importee : importees) {
        String fullName = importee.getName().replace('$', '.');
        w.println("import {0};", fullName);
      }
      w.println();
    }
  }

  private void generateClassComment(FormatWriter w, MetaClass mc) {
    String description = mc.getDescription();
    LocalDateTime time = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    w.println("/**");
    w.println(" * " + description);
    w.println(" * Generated on {0}", time.format(formatter));
    w.println(" */");
  }

  private void generateConstants(FormatWriter w, MetaClass mc) {
    List<MetaField> metaFields = mc.getConstants();

    for (MetaField field : metaFields) {
      generateField(w, field);
    }

    if (!metaFields.isEmpty()) {
      w.println();
    }
  }

  private void generateVariables(FormatWriter w, MetaClass mc) {
    MetaField reference = getReferenceForClass(mc);
    List<MetaField> fields = mc.getVariables();

    if (reference != null) {
      generateReference(w, reference);
    }

    for (MetaField field : fields) {
      generateField(w, field);
    }

    if (!fields.isEmpty()) {
      w.println();
    }
  }

  @Override
  protected MetaField getReferenceForClass(MetaClass mc) {
    Class claz = (mc == null) ? null : mc.getClaz();
    DynamicPackage.Reference reference = (claz == null) ? null : dynamicPackage.getReferenceByClass().get(claz);
    MetaField field = (reference == null) ? null : MetaField.of(reference);
    return field;
  }

  private void generateReference(FormatWriter w, MetaField reference) {
    MetaClass type = reference.getType();
    String typeName = type.getSimpleName();
    w.println("private {0} {1};", typeName, reference.getName());
  }

  private void generateField(FormatWriter w, MetaField field) {
    List<String> modifierList = new ArrayList<>();
    modifierList.add("private");
    modifierList.addAll(field.getOtherModifiers());
    String modifiers = String.join(" ", modifierList);

    String typeName = field.getTypeName();
    String initValue = field.getInitialValue();

    if (initValue == null) {
      w.println("{0} {1} {2};", modifiers, typeName, field.getName());
    } else {
      w.println("{0} {1} {2} = {3};", modifiers, typeName, field.getName(), initValue);
    }
  }

  private void generateMetaFields(FormatWriter w, MetaClass mc) {
    if (generateMetadata) {
      List<MetaField> fields = mc.getVariables();
      String className = mc.getSimpleName();

      for (MetaField field : fields) {
        String metaField = StringUtil.camelToUnderscore(field.getName()) + "_FIELD";
        w.println("public static final Field {0};", metaField);
      }

      if (!fields.isEmpty()) {
        w.println();
        w.println("static {");
        w.indent();
        w.println("try {");

        for (MetaField field : fields) {
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

  private void generateConstructor(FormatWriter w, MetaClass mc) {

    List<Member> constructorParameters = findConstructorParameters(mc, true);
    List<MetaField> constructorFields = getConstructorFields(mc);

    if (hasRequiredComponent(mc)) {
      generateOfMethods(w, mc, constructorParameters);
    }

    if (!constructorFields.isEmpty()) {
      generateParameterlessConstructor(w, mc);
      String visibility = mc.isAbstract() ? "protected" : "public";
      String className = mc.getSimpleName();

      w.println("/**");
      for (MetaField field : constructorFields) {
        w.println(" * @param {0} {1}", field.getName(), field.getDescription());
      }
      w.println(" */");

      w.print("{0} {1}(", visibility, className);
      w.print(String.join(", ", toParameters(constructorFields)));
      w.println(") {");
      w.indent();
      generateConstructorBody(w, mc, constructorFields);
      w.unindent();
      w.println("}");
      w.println();
    }
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

  private void generateParameterlessConstructor(FormatWriter w, MetaClass mc) {
    if (generateParameterlessConstructor) {
      String className = mc.getSimpleName();
      w.println("{0} {1}() '{'", "public", className);
      generateParameterlessConstructorBody(w, mc);
      w.println("}");
      w.println();
    }
  }

  private void generateParameterlessConstructorBody(FormatWriter w, MetaClass mc) {
    Class claz = mc.getClaz();
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

  private void generateOfMethods(FormatWriter w, MetaClass mc, List<Member> constructorParameters) {
    List<Field> requiredComponents = getRequiredComponents(mc);
    List<List<? extends Member>> signatures = dynamicPackage.findConcreteFieldSignatures(requiredComponents);

    for (List<? extends Member> signature : signatures) {
      generateOfMethod(w, mc, constructorParameters, signature);
    }
  }

  private void generateOfMethod(FormatWriter w, MetaClass mc, List<Member> constructorParameters, List<? extends Member> signature) {
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

    w.println("public static {0} {1}({2}) '{'", mc.getSimpleName(), methodName, parameterStr);
    generateOfMethodBody(w, mc, constructorParameters, signature);
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

  private void generateOfMethodBody(FormatWriter w, MetaClass mc, List<Member> constructorParameters, List<? extends Member> signature) {
    w.indent();
    String instance = StringUtil.uncapitalize(mc.getSimpleName());
    String arguments = String.join(", ", dynamicPackage.getMemberNames(constructorParameters));
    w.println("{0} {1} = new {0}({2});", mc.getSimpleName(), instance, arguments);

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

  private boolean hasRequiredComponent(MetaClass mc) {
    return ! getRequiredComponents(mc).isEmpty();
  }

  private List<Field> getRequiredComponents(MetaClass mc) {
    Class claz = mc.getClaz();
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

  private List<Member> findConstructorParameters(MetaClass mc, boolean includeOwner) {
    Class claz = mc.getClaz();
    List<DynamicPackage.Reference> ownerReferences = includeOwner ? dynamicPackage.getOwnerReferences(claz) : new ArrayList<>();
    List<Member> superClassMembers = dynamicPackage.getSuperClassMembers(claz, includeOwner);
    List<Member> requiredMembers = dynamicPackage.getRequiredMembers(claz, false);
    List<String> superClassMemberNames = dynamicPackage.getMemberNames(superClassMembers);

    requiredMembers = requiredMembers.stream()
            .filter(m -> (m instanceof Field f) && !dynamicPackage.isComponent(f))
            .filter(m -> ! superClassMemberNames.contains(m.getName()))
            .toList();

    List<Member> constructorParameters = new UniqueList<>();
    constructorParameters.addAll(ownerReferences);
    constructorParameters.addAll(superClassMembers);
    constructorParameters.addAll(requiredMembers);
    return constructorParameters;
  }




  private void verifyNullArgument(FormatWriter w, MetaField mf) {
    w.println("if ({0} == null) '{'", mf.getName());
    w.printlnIndented(
            "throw new IllegalArgumentException (\"Parameter ''{0}'' cannot be null\");", mf.getName());
    w.println("}");
    w.println();
  }

  private void verifyNullArgument(FormatWriter w, Member m) {
    w.println("if ({0} == null) '{'", m.getName());
    w.printlnIndented(
        "throw new IllegalArgumentException (\"Parameter ''{0}'' cannot be null\");", m.getName());
    w.println("}");
    w.println();
  }

















  @Override
  protected List<MetaClass> getSubClasses(MetaClass mc) {
    List<MetaClass> subClasses = new ArrayList<>();
    Class claz = mc.getClaz();
    List<Class> subclasses = getSubclasses(dynamicPackage.classes, claz);

    for (Class sc : subclasses) {
      subClasses.add(MetaClass.of(sc));
    }

    return subClasses;
  }


  private List<String> toParameters(List<MetaField> fields) {
    List<String> parameters = new ArrayList<>();

    for (MetaField mf : fields) {
      parameters.add(mf.getTypeName() + " " + mf.getName());
    }

    return parameters;
  }



  private List<String> toNameList(List<MetaField> fields) {
    List<String> nameList = new ArrayList<>() ;

    for (MetaField mf : fields) {
      nameList.add(mf.getName());
    }

    return nameList;
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



  private List<MetaField> getRequiredFields(MetaClass mc) {
    boolean immutable = mc.isImmutable();
    List<MetaField> allVariables = mc.getVariables();
    List<MetaField> fields = allVariables.stream()
            .filter(mf -> immutable || ! mf.isOptional())
            .toList();
    return fields;
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



  @Override
  protected String getGetterName(MetaField mf) {
    Field field = (Field)mf.getField();
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

  private static class ImportComparator implements Comparator<MetaClass> {
    @Override
    public int compare(MetaClass c1, MetaClass c2) {
      String n1 = c1.getName();
      String n2 = c2.getName();
      return n1.compareTo(n2);
    }
  }

  private static class ImportComparatorOld implements Comparator<Class<?>> {
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
