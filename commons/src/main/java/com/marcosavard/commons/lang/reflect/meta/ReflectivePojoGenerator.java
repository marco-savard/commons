package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.io.FormatWriter;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.util.collection.UniqueList;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectivePojoGenerator extends PojoGenerator {

  private DynamicPackage dynamicPackage;

  public ReflectivePojoGenerator(Map<String, String> codeByFileName, Class<?>[] classes) {
    super(codeByFileName);
    dynamicPackage = new DynamicPackage(classes);
  }

  @Override
  public void generatePojos() {
    dynamicPackage.buildReferenceByClass(containerName);

    for (Class claz : dynamicPackage.classes) {
      MetaClass mc = MetaClass.of(claz);
      generatePojo(mc);
    }
  }

  public void generate(Class<?> claz) throws IOException {
    MetaClass mc = MetaClass.of(claz);
    generatePojo(mc);
  }

  @Override
  protected MetaField getReferenceForClass(MetaClass mc) {
    Class claz = (mc == null) ? null : mc.getClaz();
    DynamicPackage.Reference reference = (claz == null) ? null : dynamicPackage.getReferenceByClass().get(claz);
    MetaField field = (reference == null) ? null : MetaField.of(reference);
    return field;
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
            .collect(Collectors.toList());

    constructorFields.addAll(requiredFields);
    return constructorFields;
  }

  @Override
  protected String getInitialValue(MetaField mf) {
    Object value;

    try {
      Member member = mf.getField();
      Class claz = member.getDeclaringClass();
      Object instance = instantiate(claz);
      value = (instance != null) && (member instanceof Field) ? ((Field)member).get(instance) : getDefaultValue(member);
    } catch (IllegalAccessException e) {
      value = null;
    }

    String initialValue = toString(value);
    return initialValue;
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

  public Object instantiate(Class<?> claz)  {
    Object instance;

    try {
      Constructor<?> constr = claz.getConstructor(new Class[] {});
      instance = constr.newInstance(new Object[] {});
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      instance = null;
    }

    return instance;
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
    } else if ((member instanceof Field) && Enum.class.isAssignableFrom(type)) {
      Field f = (Field)member;
      value = dynamicPackage.getInitialValueOfVariable(f);
    }

    return value;
  }

  private void generateOfMethods(FormatWriter w, MetaClass mc) {
    List<Member> constructorParameters = findConstructorParameters(mc, true);
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
    List<MetaField> constructorFields = getConstructorFields(mc);
    String arguments = String.join(", ", toNameList(constructorFields));

    String argumentsOld = String.join(", ", dynamicPackage.getMemberNames(constructorParameters));
    w.println("{0} {1} = new {0}({2});", mc.getSimpleName(), instance, argumentsOld);

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
      if (member instanceof Field) {
        Field field = (Field)member;
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
      if (member instanceof Field) {
        Field field = (Field)member;
        readOnlyFields.add(field);
      }
    }

    List<Field> requiredComponents = readOnlyFields.stream()
            .filter(f -> dynamicPackage.isComponent(f))
            .collect(Collectors.toList());
    return requiredComponents;
  }

  private List<Member> findConstructorParameters(MetaClass mc, boolean includeOwner) {
    Class claz = mc.getClaz();
    List<DynamicPackage.Reference> ownerReferences = includeOwner ? dynamicPackage.getOwnerReferences(claz) : new ArrayList<>();
    List<Member> superClassMembers = dynamicPackage.getSuperClassMembers(claz, includeOwner);
    List<Member> requiredMembers = dynamicPackage.getRequiredMembers(claz, false);
    List<String> superClassMemberNames = dynamicPackage.getMemberNames(superClassMembers);

    requiredMembers = requiredMembers.stream()
            .filter(m -> (m instanceof Field) && !dynamicPackage.isComponent((Field)m))
            .filter(m -> ! superClassMemberNames.contains(m.getName()))
            .collect(Collectors.toList());

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
    return Arrays.stream(claz.getDeclaredFields()).filter(f -> dynamicPackage.isConstant(f)).collect(Collectors.toList());
  }

  private List<Field> getAllVariables(Class<?> claz) {
    return Arrays.stream(claz.getFields()).filter(f -> !dynamicPackage.isConstant(f)).collect(Collectors.toList());
  }

  private List<Field> getVariables(Class<?> claz) {
    return Arrays.stream(claz.getDeclaredFields()).filter(f -> !dynamicPackage.isConstant(f)).collect(Collectors.toList());
  }



  private List<MetaField> getRequiredFields(MetaClass mc) {
    boolean immutable = mc.isImmutable();
    List<MetaField> allVariables = mc.getVariables();
    List<MetaField> fields = allVariables.stream()
            .filter(mf -> immutable || ! mf.isOptional())
            .collect(Collectors.toList());
    return fields;
  }

  private List<? extends Member> getAllReadOnlyMembers(Class<?> claz) {
    boolean immutable = dynamicPackage.isImmutable(claz);
    List<Field> readOnlyFields = Arrays.stream(claz.getFields())
            .filter(f -> immutable || dynamicPackage.isReadOnly(f))
            .collect(Collectors.toList());

    List<Member> readOnlyMembers = new ArrayList<>();
    readOnlyMembers.addAll(readOnlyFields);
    return readOnlyMembers;
  }

  private List<? extends Member> getAllRequiredMembers(Class<?> claz) {
    boolean immutable = dynamicPackage.isImmutable(claz);
    List<Field> requiredFields = Arrays.stream(claz.getFields())
            .filter(f -> immutable || ! dynamicPackage.isOptional(f))
            .collect(Collectors.toList());

    List<Member> requiredMembers = new ArrayList<>();
    requiredMembers.addAll(requiredFields);
    return requiredMembers;
  }



  private List<Field> getNotNullFields(Class<?> claz) {
    return Arrays.stream(claz.getDeclaredFields()).filter(f -> ! dynamicPackage.isOptional(f)).collect(Collectors.toList());
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
