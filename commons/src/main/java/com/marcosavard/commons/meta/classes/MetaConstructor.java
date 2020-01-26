package com.marcosavard.commons.meta.classes;

import java.util.ArrayList;
import java.util.List;

public class MetaConstructor {
  private MetaClass mc;
  private List<MetaParameter> parameters = new ArrayList<>();

  public MetaConstructor(MetaClass mc) {
    this.mc = mc;
  }

  public MetaParameter createParameter(String name, MetaDataType fieldType) {
    MetaParameter parameter = new MetaParameter(name, fieldType);
    parameters.add(parameter);
    return parameter;
  }



  public void add(MetaParameter parameter) {
    parameters.add(parameter);
  }

  public List<MetaParameter> getMetaParameters() {
    return parameters;
  }

  public int getNbParameters() {
    return parameters.size();
  }

  @Override
  public String toString() {
    String s = mc.getName() + "(";
    for (int i = 0; i < parameters.size(); i++) {
      MetaParameter param = parameters.get(i);
      s = s + param.getName();
      if (i < parameters.size() - 1) {
        s = s + ", ";
      }
    }
    s = s + ")";

    return s;
  }



}
