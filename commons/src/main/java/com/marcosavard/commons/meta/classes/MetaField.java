package com.marcosavard.commons.meta.classes;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import com.marcosavard.commons.meta.annotations.Containment;

public class MetaField extends MetaStructuralFeature {
  private MetaDataType type;
  private Field field;

  public MetaField(MetaClass owner, String name, MetaDataType type) {
    super(owner, name);
    this.type = type;
  }

  /*
   * private MetaField(MetaDataType owner, String name, Field f, boolean readonly) { this.owner =
   * owner; this.field = f; this.readonly = readonly; }
   */

  @Override
  public String getTypeName() {
    return type.getName();
  }

  /*
   * public String getVisibility() { String visibility = "";
   * 
   * if (Modifier.isPublic(field.getModifiers())) { visibility = "public"; } else if
   * (Modifier.isProtected(field.getModifiers())) { visibility = "protected"; } else if
   * (Modifier.isPrivate(field.getModifiers())) { visibility = "private"; }
   * 
   * return visibility; }
   */

  public String getFieldTypeText() {
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

  public String getConstantValue() {
    Object value = "?";

    try {
      value = field.get(null);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    String text = value.toString();
    Class type = field.getType();

    if (type.equals(Character.class)) {
      text = "\'" + text + "\'";
    } else if (type.equals(String.class)) {
      text = "\"" + text + "\"";
    }

    return text;
  }

  /*
   * public boolean isFinal() { boolean constant = Modifier.isFinal(field.getModifiers()); return
   * constant; }
   * 
   * public boolean isStatic() { boolean constant = Modifier.isStatic(field.getModifiers()); return
   * constant; }
   * 
   * public boolean isConstant() { boolean constant = (Modifier.isStatic(field.getModifiers()) &&
   * Modifier.isFinal(field.getModifiers())); return constant; }
   */

  public boolean isContainment() {
    boolean containment = (field.getAnnotation(Containment.class) instanceof Containment);
    return containment;
  }

  /*
   * public boolean isCollection() { Class c = field.getType(); boolean collection =
   * Collection.class.isAssignableFrom(c); return collection; }
   * 
   * 
   * public boolean isRequired() { Annotation annotation = field.getAnnotation(required.class);
   * boolean required = (annotation instanceof required); return required; }
   */

  public String getFieldParameterTypeText() {
    String typeName = "?";
    Type type = field.getGenericType();

    if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      Type[] types = pt.getActualTypeArguments();
      typeName = getTypeName(types[0]);
    }
    return typeName;
  }



  private String capitalize(String name) {
    String capitalized = Character.toUpperCase(name.charAt(0)) + name.substring(1);
    return capitalized;
  }

  private String uncapitalize(String name) {
    String capitalized = Character.toLowerCase(name.charAt(0)) + name.substring(1);
    return capitalized;
  }

  /*
   * public MetaClass getType() { Class claz = field.getType(); MetaModel model =
   * owner.getMetaModel(); MetaClass type = model.findMetaClass(claz); return type; }
   */

}
