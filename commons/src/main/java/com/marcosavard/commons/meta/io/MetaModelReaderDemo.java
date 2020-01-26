package com.marcosavard.commons.meta.io;

import java.util.List;
import com.marcosavard.commons.meta.classes.MetaClass;
import com.marcosavard.commons.meta.classes.MetaModel;
import com.marcosavard.domain.model.Model1;

public class MetaModelReaderDemo {

  public static void main(String[] args) {
    MetaModelReader reader = new MetaModelReader(Model1.class);
    MetaModel model = reader.read();
    List<MetaClass> classes = model.getMetaClasses();
    System.out.println(model);

    for (MetaClass claz : classes) {
      System.out.println(claz);
    }
  }

}
