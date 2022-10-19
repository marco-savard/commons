package com.marcosavard.commons.meta.classes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.marcosavard.commons.meta.annotations.NotNull;

public class MetaClass extends MetaDataType {
  private MetaClass superClass;
  private MetaVisibility visibility = MetaVisibility.PRIVATE;
  private boolean isAbstract = false;
  private boolean immutable = false;

  private List<MetaConstructor> metaConstructors = new ArrayList<>();
  private List<MetaStructuralFeature> features = new ArrayList<>();

  public MetaClass(MetaPackage pack, String name) {
    super(pack, name);
  }

  public void setSuperClass(MetaClass superClass) {
    this.superClass = superClass;
  }

  public void setVisibility(MetaVisibility visibility) {
    this.visibility = visibility;
  }

  public boolean isAbstract() {
    return isAbstract;
  }

  public void setAbstract(boolean value) {
    isAbstract = value;
  }

  public boolean isImmutable() {
    return immutable;
  }

  public void setImmutable(boolean immutable) {
    this.immutable = immutable;
  }

  public MetaField createField(String fieldName, MetaEnum type) {
    MetaField mf = new MetaField(this, fieldName, type);
    features.add(mf);
    return mf;
  }

  public MetaField createField(String fieldName, MetaBuiltinType type) {
    MetaField mf = new MetaField(this, fieldName, type);
    features.add(mf);
    return mf;
  }

  public MetaReference createReference(String fieldTypeName, MetaClass opposite,
      MetaReference.ReferenceType referenceType) {
    MetaReference ref = new MetaReference(this, fieldTypeName, opposite, referenceType);
    features.add(ref);
    return ref;
  }

  public List<MetaStructuralFeature> getMetaFeatures() {
    return features;
  }

  public MetaStructuralFeature getMetaFeature(String name) {
    MetaStructuralFeature feature =
        features.stream().filter(f -> name.contentEquals(f.getName())).findAny().orElse(null);
    return feature;
  }

  /*
   * MetaClass(MetaModel model, Class claz) { super(model, claz); Class superClass =
   * claz.getSuperclass(); boolean hasSuperClass = (superClass != null) && !
   * superClass.equals(Object.class);
   * 
   * if (hasSuperClass) { this.superClass = (MetaClass)model.createMetaType(superClass); }
   * 
   * createConstructors(); }
   */

  public List<MetaConstructor> getMetaConstructors() {
    return metaConstructors;
  }

  /*
   * public void addMetaField(MetaField mf) { metaFields.add(mf); }
   */
  /*
   * private void createConstructors() { List<Field[]> constructorParams = new ArrayList<>();
   * Constructor[] constructors = claz.getConstructors(); Field[] allFields = claz.getFields();
   * List<Field> fieldList = Arrays.asList(allFields);
   * 
   * for (Constructor constructor : constructors) { List<Field> constructorFields = new
   * ArrayList<>(); Parameter[] params = constructor.getParameters(); for (Parameter param : params)
   * { Field field = fieldList.stream().filter(f ->
   * f.getName().equals(param.getName())).findFirst().orElse(null); if (field != null) {
   * constructorFields.add(field); } }
   * 
   * if (constructorFields.size() > 0) { Field[] fields = constructorFields.toArray(new
   * Field[constructorFields.size()]); constructorParams.add(fields); } }
   * 
   * boolean noArgsConstructor = claz.getAnnotation(noArgsConstructor.class) instanceof
   * noArgsConstructor; boolean requiredArgsConstructor =
   * claz.getAnnotation(requiredArgsConstructor.class) instanceof requiredArgsConstructor; boolean
   * allArgsConstructor = claz.getAnnotation(allArgsConstructor.class) instanceof
   * allArgsConstructor;
   * 
   * if (noArgsConstructor) { Field[] noFields = new Field[] {}; constructorParams.add(noFields); }
   * 
   * if (requiredArgsConstructor) { Field[] requiredFields = getRequiredFields(claz.getFields());
   * constructorParams.add(requiredFields); }
   * 
   * if (allArgsConstructor) { constructorParams.add(allFields); }
   * 
   * Comparator<Field[]> comparator = new FieldComparator(); Collections.sort(constructorParams,
   * comparator);
   * 
   * Set<String> parameterSet = new TreeSet<>(); for (Field[] fields : constructorParams) {
   * List<String> fieldNames = getFieldNames(fields); String parameterList = String.join(",",
   * fieldNames);
   * 
   * if (! parameterSet.contains(parameterList)) { boolean callAllArgsConstructor =
   * (allArgsConstructor && (fieldNames.size() < allFields.length));
   * parameterSet.add(parameterList);
   * 
   * MetaConstructor constr = new MetaConstructor(this); metaConstructors.add(constr);
   * 
   * for (Field f : fields) { String typename = getFieldTypeText(f); boolean readonly =
   * (f.getAnnotation(readonly.class) instanceof readonly); boolean required =
   * (f.getAnnotation(required.class) instanceof required); MetaParameter mp = new
   * MetaParameter(typename, f.getName(), readonly, required); constr.add(mp); } } } }
   */

  private String getFieldTypeText(Field field) {
    Type type = field.getGenericType();
    String typeName = getTypeName(type);
    return typeName;
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

  private Field[] getRequiredFields(Field[] declaredFields) {
    List<Field> requiredFieldList = new ArrayList<>();

    for (Field f : declaredFields) {
      if (isRequired(f)) {
        requiredFieldList.add(f);
      }
    }

    Field[] requiredFields = requiredFieldList.toArray(new Field[requiredFieldList.size()]);
    return requiredFields;
  }

  private boolean isRequired(Field field) {
    Annotation annotation = field.getAnnotation(NotNull.class);
    boolean required = (annotation instanceof NotNull);
    return required;
  }

  private List<String> getFieldNames(Field[] fields) {
    List<String> fieldNames = new ArrayList<>();
    for (Field f : fields) {
      fieldNames.add(f.getName());
    }

    Collections.sort(fieldNames);
    return fieldNames;
  }

  public MetaClass getSuperclass() {
    return superClass;
  }

  public String getModifierText() {
    List<String> modifiers = new ArrayList<>();

    if (visibility == MetaVisibility.PUBLIC) {
      modifiers.add("public");
    }

    if (isAbstract) {
      modifiers.add("abstract");
    }

    String keywords = String.join(" ", modifiers);
    return keywords;
  }

  public MetaConstructor createConstructor() {
    MetaConstructor constr = new MetaConstructor(this);
    metaConstructors.add(constr);
    return constr;
  }

  private static class FieldComparator implements Comparator<Field[]> {

    @Override
    public int compare(Field[] o1, Field[] o2) {
      int comparison = o1.length - o2.length;
      return comparison;
    }

  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("class {0}", this.getName());
    return msg;
  }



}
