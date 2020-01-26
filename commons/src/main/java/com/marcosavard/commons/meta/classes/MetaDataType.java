package com.marcosavard.commons.meta.classes;

public abstract class MetaDataType extends MetaNamedObject {
  protected MetaPackage pack;

  // protected List<MetaField> fields = new ArrayList<>();

  protected MetaDataType(MetaPackage pack, String name) {
    super(name);
    this.pack = pack;
  }

  public MetaPackage getPackage() {
    return pack;
  }


  /*
   * private void add(MetaField mf) { fields.add(mf); }
   * 
   * public List<MetaField> getMetaFields() { return fields; }
   * 
   * 
   * 
   * public File getRootFolder() { return rootFolder; }
   * 
   * public String getPackageName() { return this.claz.getPackage().getName(); }
   * 
   * public MetaModel getMetaModel() { return model; }
   */

}
