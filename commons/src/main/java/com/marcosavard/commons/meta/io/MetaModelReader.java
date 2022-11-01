package com.marcosavard.commons.meta.io;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import com.marcosavard.commons.meta.annotations.AllArgsConstructor;
import com.marcosavard.commons.meta.annotations.Containment;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.lang.reflect.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.NoArgsConstructor;
import com.marcosavard.commons.lang.reflect.meta.annotations.Readonly;
import com.marcosavard.commons.meta.annotations.NotNull;
import com.marcosavard.commons.meta.annotations.RequiredArgsConstructor;
import com.marcosavard.commons.meta.classes.MetaBuiltinType;
import com.marcosavard.commons.meta.classes.MetaClass;
import com.marcosavard.commons.meta.classes.MetaConstructor;
import com.marcosavard.commons.meta.classes.MetaDataType;
import com.marcosavard.commons.meta.classes.MetaEnum;
import com.marcosavard.commons.meta.classes.MetaModel;
import com.marcosavard.commons.meta.classes.MetaPackage;
import com.marcosavard.commons.meta.classes.MetaParameter;
import com.marcosavard.commons.meta.classes.MetaReference;
import com.marcosavard.commons.meta.classes.MetaStructuralFeature;
import com.marcosavard.commons.meta.classes.MetaVisibility;

public class MetaModelReader {
  private Class modelClass;

  public MetaModelReader(Class claz) {
    this.modelClass = claz;
  }

  public MetaModel read() {
    // create model and package
    File rootFolder = findRootFolder(modelClass);
    Class[] classes = modelClass.getClasses();
    MetaModel model = new MetaModel(rootFolder);
    createPackage(model, classes);

    // create model elements
    for (Class claz : classes) {
      createType(model, claz);
    }

    for (Class claz : classes) {
      createTypeHierarchy(model, claz);
    }

    for (Class claz : classes) {
      addClassMembers(model, claz);
    }

    for (Class claz : classes) {
      addClassConstructors(model, claz);
    }

    model.setContainments();

    return model;
  }

  private File findRootFolder(Class claz) {
    File binFolder = findBinFolder(claz);
    return binFolder.getParentFile();
  }

  private File findBinFolder(Class claz) {
    File rootFolder = null;

    try {
      ClassLoader loader = claz.getClassLoader();
      URL url = loader.getResource(".");
      File file = Paths.get(url.toURI()).toFile();
      rootFolder = file.getParentFile();

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }


    return rootFolder;
  }

  private MetaPackage createPackage(MetaModel model, Class[] classes) {
    Class claz = classes[0];
    String packageName = getPackageName(claz);
    int idx = packageName.lastIndexOf('.');
    packageName = packageName.substring(0, idx);
    MetaPackage mp = model.getPackageByName(packageName);
    return mp;
  }

  private String getPackageName(Class claz) {
    String name = claz.getPackage().getName();
    return name;
  }

  private void createType(MetaModel model, Class claz) {
    String packageName = getPackageName(claz);
    int idx = packageName.lastIndexOf('.');
    packageName = packageName.substring(0, idx);

    String name = claz.getSimpleName();
    MetaPackage mp = model.getPackageByName(packageName);

    if (claz.isEnum()) {
      MetaEnum me = new MetaEnum(mp, name);
      model.addType(me);
    } else {
      MetaClass mc = new MetaClass(mp, name);
      model.addType(mc);

      if (Modifier.isPublic(claz.getModifiers())) {
        mc.setVisibility(MetaVisibility.PUBLIC);
      }

      if (Modifier.isAbstract(claz.getModifiers())) {
        mc.setAbstract(true);
      }

      boolean immutable = (claz.getAnnotation(Immutable.class) instanceof Immutable);
      mc.setImmutable(immutable);

      Description description = (Description) claz.getAnnotation(Description.class);
      mc.setDescription(description == null ? null : description.value());
    }
  }

  private void createTypeHierarchy(MetaModel model, Class claz) {
    if (!claz.isEnum()) {
      Class sc = claz.getSuperclass();

      if (sc != null) {
        String basename = claz.getSimpleName();
        String superClassName = sc.getSimpleName();
        MetaClass base = (MetaClass) model.findTypeByName(basename);
        MetaClass superClass = (MetaClass) model.findTypeByName(superClassName);

        base.setSuperClass(superClass);
      }
    }
  }

  private void addClassMembers(MetaModel model, Class claz) {
    String typeName = claz.getSimpleName();
    MetaDataType type = model.findTypeByName(typeName);
    List<Field> fields = getDeclaredFields(claz, false);

    for (Field f : fields) {
      addClassMember(model, type, f);
    }
  }

  private void addClassMember(MetaModel model, MetaDataType dataType, Field f) {
    String name = f.getName();

    if (dataType instanceof MetaEnum) {
      MetaEnum me = (MetaEnum) dataType;
      me.createLiteral(name);
    } else if (dataType instanceof MetaClass) {
      MetaClass mc = (MetaClass) dataType;
      boolean collection = Collection.class.isAssignableFrom(f.getType());
      Class type = collection ? getItemType(f) : f.getType();
      String typeName = type.getSimpleName();
      MetaDataType fieldType = model.findTypeByName(typeName);
      MetaStructuralFeature feature = null;

      if (fieldType instanceof MetaBuiltinType) {
        MetaBuiltinType mt = (MetaBuiltinType) fieldType;
        feature = mc.createField(f.getName(), mt);
      } else if (fieldType instanceof MetaEnum) {
        MetaEnum me = (MetaEnum) fieldType;
        feature = mc.createField(f.getName(), me);
      } else if (fieldType instanceof MetaClass) {
        MetaClass opposite = (MetaClass) fieldType;
        boolean containment = (f.getAnnotation(Containment.class) instanceof Containment);
        MetaReference.ReferenceType refType = containment ? MetaReference.ReferenceType.COMPOSITION
            : MetaReference.ReferenceType.AGGREGATION;
        feature = mc.createReference(f.getName(), opposite, refType);
      }

      if (feature != null) {
        boolean isStatic = Modifier.isStatic(f.getModifiers());
        boolean isFinal = Modifier.isFinal(f.getModifiers());
        MetaVisibility visibility = getVisibility(f);
        boolean readonly = (f.getAnnotation(Readonly.class) instanceof Readonly);
        boolean required = (f.getAnnotation(NotNull.class) instanceof NotNull);
        Description description = (Description) f.getAnnotation(Description.class);
        readonly = readonly || mc.isImmutable();
        Class c = f.getType();

        feature.setStatic(isStatic);
        feature.setFinal(isFinal);
        feature.setVisibility(visibility);
        feature.setCollection(collection);
        feature.setReadOnly(readonly);
        feature.setRequired(required);
        feature.setDescription(description == null ? null : description.value());

        if (isStatic) {
          String value = getConstantValue(f);
          feature.setValue(value);
        }
      }
    }
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

  private void addClassConstructors(MetaModel model, Class claz) {
    String typeName = claz.getSimpleName();
    MetaDataType type = model.findTypeByName(typeName);

    if (type instanceof MetaClass) {
      MetaClass mc = (MetaClass) type;
      Constructor[] constructors = claz.getConstructors();
      List<Field> allFields = getDeclaredFields(claz, true);
      List<Field[]> constructorParams = new ArrayList<>();

      for (Constructor constructor : constructors) {
        List<Field> constructorFields = getConstructorFields(allFields, constructor);

        if (constructorFields.size() > 0) {
          Field[] fields = constructorFields.toArray(new Field[constructorFields.size()]);
          constructorParams.add(fields);
        }
      }

      boolean noArgsConstructor =
          claz.getAnnotation(NoArgsConstructor.class) instanceof NoArgsConstructor;
      boolean requiredArgsConstructor =
          claz.getAnnotation(RequiredArgsConstructor.class) instanceof RequiredArgsConstructor;
      boolean allArgsConstructor =
          claz.getAnnotation(AllArgsConstructor.class) instanceof AllArgsConstructor;
      Field[] allFieldsArray = allFields.toArray(new Field[allFields.size()]);

      if (noArgsConstructor) {
        Field[] noFields = new Field[] {};
        constructorParams.add(noFields);
      }

      if (requiredArgsConstructor) {
        Field[] requiredFields = getRequiredFields(claz, true);
        constructorParams.add(requiredFields);
      }

      if (allArgsConstructor) {
        constructorParams.add(allFieldsArray);
      }

      Comparator<Field[]> comparator = new FieldComparator();
      Collections.sort(constructorParams, comparator);
      Set<String> parameterSet = new TreeSet<>();

      for (Field[] fields : constructorParams) {
        addConstructorForParameters(mc, parameterSet, allFieldsArray, fields, allArgsConstructor);
      }
    }
  }

  private void addConstructorForParameters(MetaClass mc, Set<String> parameterSet,
      Field[] allFields, Field[] fields, boolean allArgsConstructor) {
    List<String> fieldNames = getFieldNames(fields);
    String parameterList = String.join(",", fieldNames);
    MetaModel model = mc.getPackage().getModel();

    if (!parameterSet.contains(parameterList)) {
      boolean callAllArgsConstructor =
          (allArgsConstructor && (fieldNames.size() < allFields.length));
      parameterSet.add(parameterList);
      MetaConstructor constr = mc.createConstructor();

      for (Field f : fields) {
        String fieldTypeName = f.getType().getSimpleName();
        MetaDataType fieldType = model.findTypeByName(fieldTypeName);
        MetaParameter param = constr.createParameter(f.getName(), fieldType);

        boolean readonly = (f.getAnnotation(Readonly.class) instanceof Readonly);
        boolean required = (f.getAnnotation(NotNull.class) instanceof NotNull);
        readonly = readonly || mc.isImmutable();
        param.setReadOnly(readonly);
        param.setRequired(required);
      }
    }
  }

  private List<String> getFieldNames(Field[] fields) {
    List<String> fieldNames = new ArrayList<>();
    for (Field f : fields) {
      fieldNames.add(f.getName());
    }

    Collections.sort(fieldNames);
    return fieldNames;
  }

  private Field[] getRequiredFields(Class claz, boolean includeHierarchy) {
    List<Field> declaredFields = getDeclaredFields(claz, includeHierarchy);
    List<Field> requiredFieldList = new ArrayList<>();
    boolean immutable = (claz.getAnnotation(Immutable.class) instanceof Immutable);

    for (Field f : declaredFields) {
      boolean required = isRequired(f) || immutable;
      if (required) {
        requiredFieldList.add(f);
      }
    }

    Field[] requiredFields = requiredFieldList.toArray(new Field[requiredFieldList.size()]);
    return requiredFields;
  }

  private List<Field> getDeclaredFields(Class claz, boolean includeHierarchy) {
    List<Field> declaredFieldList = new ArrayList<>();

    if (includeHierarchy) {
      Class superClass = claz.getSuperclass();
      if (superClass != null) {
        declaredFieldList.addAll(getDeclaredFields(superClass, includeHierarchy));
      }
    }

    Field[] declaredFields = claz.getDeclaredFields();

    for (Field f : declaredFields) {
      String name = f.getName();
      boolean valid = !"this$0".equals(name);
      valid &= !(Enum.class.equals(claz) && "name".equals(name));
      valid &= !(Enum.class.equals(claz) && "ordinal".equals(name));
      valid &= !"ENUM$VALUES".equals(name);

      if (valid) {
        declaredFieldList.add(f);
      }
    }

    return declaredFieldList;
  }

  private boolean isRequired(Field field) {
    Annotation annotation = field.getAnnotation(NotNull.class);
    boolean required = (annotation instanceof NotNull);
    return required;
  }

  private List<Field> getConstructorFields(List<Field> fieldList, Constructor constructor) {
    List<Field> constructorFields = new ArrayList<>();
    Parameter[] params = constructor.getParameters();

    for (Parameter param : params) {
      Field field = fieldList.stream().filter(f -> f.getName().equals(param.getName())).findFirst()
          .orElse(null);
      if (field != null) {
        constructorFields.add(field);
      }
    }

    return constructorFields;
  }

  private MetaVisibility getVisibility(Field f) {
    MetaVisibility visibility;

    if (Modifier.isPublic(f.getModifiers())) {
      visibility = MetaVisibility.PUBLIC;
    } else if (Modifier.isProtected(f.getModifiers())) {
      visibility = MetaVisibility.PROTECTED;
    } else if (Modifier.isPrivate(f.getModifiers())) {
      visibility = MetaVisibility.PRIVATE;
    } else {
      visibility = MetaVisibility.PACKAGE;
    }

    return visibility;
  }


  public String getConstantValue(Field f) {
    Object value = "?";

    try {
      value = f.get(null);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    String text = value.toString();
    Class type = f.getType();

    if (type.equals(Character.class)) {
      text = "\'" + text + "\'";
    } else if (type.equals(String.class)) {
      text = "\"" + text + "\"";
    }

    return text;
  }

  private String getParameterType(Type[] types) {
    String[] typenames = getTypeNames(types);
    return "<" + String.join(", ", typenames) + ">";
  }

  private String[] getTypeNames(Type[] types) {
    String[] typeNames = new String[types.length];

    for (int i = 0; i < types.length; i++) {
      typeNames[i] = getTypeName(types[i]);
    }

    return typeNames;
  }

  private String getTypeName(Type type) {
    String typeName = "?";

    if (type instanceof Class) {
      Class claz = (Class) type;
      typeName = claz.getSimpleName();
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      Type rawType = pt.getRawType();
      Type[] types = pt.getActualTypeArguments();
      typeName = getTypeName(rawType) + getParameterType(types);
    }

    return typeName;
  }

  private static Field[] concatenate(Field[] firstArray, Field[] secondArray) {
    Field[] concatenation = Arrays.copyOf(firstArray, firstArray.length + secondArray.length);
    System.arraycopy(secondArray, 0, concatenation, firstArray.length, secondArray.length);
    return concatenation;
  }

  private static class FieldComparator implements Comparator<Field[]> {
    @Override
    public int compare(Field[] o1, Field[] o2) {
      int comparison = o1.length - o2.length;
      return comparison;
    }
  }
}
