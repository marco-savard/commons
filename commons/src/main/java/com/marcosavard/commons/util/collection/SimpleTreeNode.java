package com.marcosavard.commons.util.collection;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class SimpleTreeNode<T> extends DefaultMutableTreeNode {

  private SimpleTreeNode(T data) {
    super(data);
  }

  public static <T> SimpleTreeNode createRoot(T data) {
    SimpleTreeNode<T> treeNode = new SimpleTreeNode<T>(data);
    return treeNode;
  }

  public SimpleTreeNode addChild(T childData) {
    SimpleTreeNode child = new SimpleTreeNode(childData);
    this.add(child);
    return child;
  }

  public void addChildren(T... children) {
    for (T child : children) {
      addChild(child);
    }
  }

  @Override
  public boolean equals(Object that) {
    boolean equal = false;

    if (that instanceof TreeNode treeNode) {
      equal = equals(this, treeNode);
    }

    return equal;
  }

  private static boolean equals(TreeNode node1, TreeNode node2) {
    boolean equal = false;

    if (node1 instanceof DefaultMutableTreeNode treeNode1) {
      if (node2 instanceof DefaultMutableTreeNode treeNode2) {
        Object data1 = treeNode1.getUserObject();
        Object data2 = treeNode2.getUserObject();

        if (Objects.equals(data1, data2)) {
          int n = treeNode1.getChildCount();
          equal = (n == treeNode2.getChildCount());

          if (equal) {
            for (int i=0; i<n; i++) {
              equal &= equals(treeNode1.getChildAt(i), treeNode2.getChildAt(i));
            }
          }
        }
      }
    }

    return equal;
  }

  public SimpleTreeNode[] getPath() {
    List<TreeNode> pathToRoot = new ArrayList<>();
    TreeNode ancestor = this;

    do {
      pathToRoot.add(ancestor);
      ancestor = ancestor.getParent();
    } while (ancestor != null);

    Collections.reverse(pathToRoot);
    return pathToRoot.toArray(new SimpleTreeNode[0]);
  }

  public SimpleTreeNode getRoot() {
    return getPath()[0];
  }

  @Override
  public T getUserObject() {
    return (T)userObject;
  }

  public boolean isRoot() {
    return (parent == null);
  }

  public void removeAllChildren() {
    for (int i = getChildCount() - 1; i >= 0; i--) {
      remove(i);
    }
  }

  public void setUserData(T data) {
    super.setUserObject((T)data);
  }

  @Override
  public void setUserObject(Object object) {
    super.setUserObject((T)object);
  }

  @Override
  public String toString() {
    return toShortString();
  }

  public static String toString(TreeNode node) {
    String nodeData = node.toString();
    List<TreeNode> children = getChildren(node);
    List<String> childrenData = toString(children);
    return nodeData + (childrenData.isEmpty() ? "" : childrenData);
  }

  public static List<String> toString(List<TreeNode> children) {
    List<String> data = new ArrayList<>();

    for (TreeNode node : children) {
      data.add(toString(node));
    }

    return data;
  }

  public static List<TreeNode> getChildren(TreeNode node) {
    List<TreeNode> children = new ArrayList<>();
    int n = node.getChildCount();

    for (int i=0; i<n; i++) {
      TreeNode child = node.getChildAt(i);
      children.add(child);
    }

    return children;
  }


  public String toShortString() {
    return this.userObject.toString();
  }

  public String toLongString() {
    List<String> items = new ArrayList<>();

    for (TreeNode child : children) {
      if (child instanceof DefaultMutableTreeNode mutableTreeNode) {
        String data = String.valueOf(mutableTreeNode.getUserObject());
        items.add(data);
      }
    }

    return this.userObject.toString() + " [" + String.join(", ", items) + "]";
  }

  public String toNestedString() {
    String nodeData = this.userObject.toString();
    boolean hasChild = (children != null) && ! children.isEmpty();
    String childrenData = hasChild ? " " + toNestedString(children) : "";
    return nodeData + childrenData;
  }

  public String toNestedString(Vector<TreeNode> nodes) {
    List<String> items = new ArrayList<>();

    for (TreeNode node : nodes) {
      if (node instanceof SimpleTreeNode<?> sn) {
        String s = sn.toNestedString();
        items.add(s);
      }
    }

    return "[" + String.join(", ", items) + "]";
  }
}
