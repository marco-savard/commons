package com.marcosavard.commons.util.collection;

import com.marcosavard.commons.util.tree.ITreeNode;
import com.marcosavard.commons.util.tree.TreeNodeFactory;

import java.lang.reflect.Field;

public class ReflectionTreeBuilder {
  private Class claz;
  private String fieldName;

  public static ReflectionTreeBuilder of(Class claz, String fieldName) {
    return new ReflectionTreeBuilder(claz, fieldName);
  }

  private ReflectionTreeBuilder(Class claz, String fieldName) {
    this.claz = claz;
    this.fieldName = fieldName;
  }

  public ITreeNode build() {
    TreeNodeFactory treeNodeFactory = new TreeNodeFactory.SwingTreeNodeFactory();
    ITreeNode root = treeNodeFactory.createRoot(fieldName);

    try {
      Field field = claz.getDeclaredField(fieldName);
      Object value = field.get(null);

      if (value instanceof Object[]) {
        buildChildren(root, (Object[]) value);
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    return root;
  }

  private void buildChildren(ITreeNode root, Object[] children) throws IllegalAccessException {
    for (Object childValue : children) {
      buildChild(root, childValue);
    }
  }

  private void buildChild(ITreeNode root, Object childValue) throws IllegalAccessException {
    Field field = findFieldByValue(childValue);
    ITreeNode child = root.addChild(field.getName());

    if (childValue instanceof String[]) {
      addLeaves(child, (String[]) childValue);
    } else if (childValue instanceof Object[]) {
      buildChildren(child, (Object[]) childValue);
    }
  }

  private void addLeaves(ITreeNode node, String[] leaves) {
    for (String leaf : leaves) {
      node.addChild(leaf);
    }
  }

  private Field findFieldByValue(Object fieldValue) throws IllegalAccessException {
    Field[] fields = claz.getDeclaredFields();
    Field foundField = null;

    for (Field field : fields) {
      field.setAccessible(true);
      if (fieldValue.equals(field.get(null))) {
        foundField = field;
        break;
      }
    }

    return foundField;
  }
}
