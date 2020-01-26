package com.marcosavard.commons.meta.io;

import com.marcosavard.commons.meta.classes.MetaModel;
import com.marcosavard.commons.util.SimpleProgressMonitor;
import com.marcosavard.domain.model.Model8;

public class PojoWriterDemo {

  public static void main(String[] args) {
    generateModel(Model8.class);
  }

  private static void generateModel(Class<?> modelClass) {
    MetaModelReader reader = new MetaModelReader(modelClass);
    MetaModel model = reader.read();

    PojoWriterBuilder builder = new PojoWriterBuilder();
    builder.setIndentation(2);
    builder.writeGenericAccessors(false);
    builder.setSourceFolder("src/main/java");
    builder.setProgressMonitor(new DemoProgressMonitor());
    PojoWriter writer = builder.build();
    writer.write(model);
  }

  private static class DemoProgressMonitor extends SimpleProgressMonitor {

    @Override
    public void beginTask(String task, int totalWork) {
      System.out.println(task);
    }

    @Override
    public void worked(String subtask, int work) {
      System.out.println(subtask);
    }

    @Override
    public void terminateTask(String task) {
      System.out.println(task);
    }

  }



}
