package com.marcosavard.commons.util.tree;

import com.sun.source.tree.Tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class SwingTreeNode<T> extends DefaultMutableTreeNode implements ITreeNode<T> {

  private SwingTreeNode(T data) {
    super(data);
  }

  static <T> SwingTreeNode createRoot(T data) {
    SwingTreeNode<T> treeNode = new SwingTreeNode<T>(data);
    return treeNode;
  }

 // @Override
  public ITreeNode addChild(T childData) {
    SwingTreeNode child = new SwingTreeNode(childData);
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
    return ITreeNode.equals(this, that);
  }

  public SwingTreeNode[] getPath() {
    List<TreeNode> pathToRoot = ITreeNode.getPath(this);
    SwingTreeNode[] path = pathToRoot.toArray(new SwingTreeNode[0]);
    return path;
  }

  public SwingTreeNode getRoot() {
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

  public String toShortString() {
    return userObject.toString();
  }

  public String toLongString() {
    return ITreeNode.toLongString(this, children);
  }

  public String toLongStringOld() {
    List<ITreeNode<T>> childList = toList(children);
    return ITreeNode.toLongString(userObject, childList);
  }

  private List<ITreeNode<T>> toList(Vector<TreeNode> items) {
    List<ITreeNode<T>> list = new ArrayList<>();
    for (TreeNode item : items) {
      list.add((ITreeNode<T>)item);
    }

    return list;
  }

  public String toNestedString() {
    return ITreeNode.toNestedString(userObject, children);
  }

}
