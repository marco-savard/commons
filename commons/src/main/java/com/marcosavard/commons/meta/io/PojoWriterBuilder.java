package com.marcosavard.commons.meta.io;

import com.marcosavard.commons.meta.io.PojoWriterOptions.AccessorOrder;
import com.marcosavard.commons.util.ProgressMonitor;

public class PojoWriterBuilder {
  private PojoWriterOptions options = new PojoWriterOptions();

  void setAccessorOrder(AccessorOrder accessorOrder) {
    options.setAccessorOrder(accessorOrder);
  }

  void setIndentation(int indentation) {
    options.setIndentation(indentation);
  }

  void setSourceFolder(String sourceFolder) {
    options.setSourceFolder(sourceFolder);
  }

  void setProgressMonitor(ProgressMonitor progressMonitor) {
    options.setProgressMonitor(progressMonitor);
  }

  void writeGenericAccessors(boolean writeGenericAccessor) {
    options.writeGenericAccessor = writeGenericAccessor;
  }

  public PojoWriter build() {
    PojoWriter generator = new PojoWriter(options);
    return generator;
  }



}
