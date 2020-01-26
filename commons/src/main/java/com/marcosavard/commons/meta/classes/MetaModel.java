package com.marcosavard.commons.meta.classes;

import java.io.File;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.marcosavard.commons.meta.annotations.Immutable;
import com.marcosavard.commons.meta.annotations.Readonly;

public class MetaModel {
  private File rootFolder;
  private Map<String, MetaPackage> metaPackages = new HashMap<>();
  private Map<String, MetaDataType> metaTypes = new HashMap<>();

  public MetaModel(File rootFolder) {
    this.rootFolder = rootFolder;
    createBuiltinType("boolean");
    createBuiltinType("int");
    createBuiltinType("long");
    createBuiltinType("String");
  }

  private void createBuiltinType(String name) {
    MetaBuiltinType type = new MetaBuiltinType(this, name);
    metaTypes.put(name, type);

  }

  public File getRootFolder() {
    return rootFolder;
  }

  public MetaPackage getPackageByName(String packageName) {
    MetaPackage mp = metaPackages.get(packageName);

    if (mp == null) {
      mp = new MetaPackage(this, packageName);
      metaPackages.put(packageName, mp);
    }

    return mp;
  }

  public void addType(MetaDataType dataType) {
    metaTypes.put(dataType.getName(), dataType);
  }

  /*
   * public MetaDataType createMetaType(Class claz) { String className = claz.getSimpleName();
   * MetaDataType dataType = metaTypes.get(claz.getSimpleName());
   * 
   * if (dataType == null) { boolean isEnum = claz.isEnum(); if (isEnum) { dataType = new
   * MetaEnum(this, claz); } else { dataType = new MetaClass(this, claz); }
   * 
   * metaTypes.put(className, dataType); }
   * 
   * return dataType; }
   */

  public void addClassMembers(Class claz) {
    boolean immutable = (claz.getAnnotation(Immutable.class) instanceof Immutable);
    MetaDataType type = metaTypes.get(claz.getSimpleName());
    Field[] fields = claz.getFields();

    if (type instanceof MetaEnum) {
      MetaEnum me = (MetaEnum) type;

      for (Field f : fields) {
        new MetaEnumLiteral(me, f.getName());
      }
    } else if (type instanceof MetaClass) {
      MetaClass mc = (MetaClass) type;

      for (Field f : fields) {
        boolean readonly = immutable || (f.getAnnotation(Readonly.class) instanceof Readonly);
        MetaDataType fieldType = metaTypes.get(f.getType().getSimpleName());
        String name = type.getName();
        // MetaField mf = new MetaField(mc, f, readonly);
        // mc.addMetaField(mf);
      }
    }
  }

  public List<MetaDataType> getMetaTypes() {
    List<MetaDataType> types = new ArrayList<>(metaTypes.values());
    return types;
  }

  public List<MetaClass> getMetaClasses() {
    List<MetaDataType> types = new ArrayList<>(metaTypes.values());
    List<MetaClass> metaclasses = new ArrayList<>();
    for (MetaDataType type : types) {
      if (type instanceof MetaClass) {
        metaclasses.add((MetaClass) type);
      }
    }

    return metaclasses;
  }

  public List<MetaEnum> getMetaEnums() {
    List<MetaDataType> types = new ArrayList<>(metaTypes.values());
    List<MetaEnum> metaenums = new ArrayList<>();
    for (MetaDataType type : types) {
      if (type instanceof MetaEnum) {
        metaenums.add((MetaEnum) type);
      }
    }

    return metaenums;
  }


  public void setContainments() {
    /*
     * for (MetaDataType mt : metaTypes.values()) { if (mt instanceof MetaClass) { MetaClass mc =
     * (MetaClass)mt; for (MetaField mf : mc.fields) { if (mf.isContainment()) { MetaClass type =
     * mf.getType();
     * 
     * } } } }
     */
  }

  public MetaClass findMetaClass(Class claz) {
    String name = claz.getSimpleName();
    return (MetaClass) metaTypes.get(name);
  }

  public MetaDataType findTypeByName(String typeName) {
    return metaTypes.get(typeName);
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("model source folder : {0}", this.rootFolder);
    return msg;
  }



}
