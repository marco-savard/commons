package com.marcosavard.commons.meta.io;

import com.marcosavard.commons.util.ProgressMonitor;
import com.marcosavard.commons.util.SimpleProgressMonitor;

public class PojoWriterOptions {
  public enum ClassGeneration {
    ONE_CLASS_PER_ENTITY, ABSTRACT_PER_ENTITY
  }
  public enum AccessorOrder {
    GROUP_BY_PROPERTY, GROUP_BY_ACCESSOR_TYPE
  }

  private ClassGeneration cg;
  private AccessorOrder accessorOrder = AccessorOrder.GROUP_BY_PROPERTY;
  private int indentation = 2;
  private String sourceFolder = "src";
  private ProgressMonitor progressMonitor = new SimpleProgressMonitor();
  public boolean writeGenericAccessor;

  public void setAccessorOrder(AccessorOrder accessorOrder) {
    this.accessorOrder = accessorOrder;
  }

  public AccessorOrder getAccessorOrder() {
    return accessorOrder;
  }

  public void setIndentation(int indentation) {
    this.indentation = indentation;
  }

  public int getIndentation() {
    return indentation;
  }

  public void setSourceFolder(String sourceFolder) {
    this.sourceFolder = sourceFolder;
  }

  public String getSourceFolder() {
    return sourceFolder;
  }

  public void setProgressMonitor(ProgressMonitor progressMonitor) {
    this.progressMonitor = progressMonitor;
  }

  public ProgressMonitor getProgressMonitor() {
    return this.progressMonitor;
  }
}
