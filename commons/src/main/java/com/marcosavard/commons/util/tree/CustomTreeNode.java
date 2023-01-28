package com.marcosavard.commons.util.tree;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class CustomTreeNode<T> implements SimpleTreeNode<T>  {
    private T data;
    private CustomTreeNode<T> parent = null;
    private List<CustomTreeNode<T>> children = new LinkedList<>();

    CustomTreeNode(CustomTreeNode<T> parent, T data) {
        this.parent = parent;
        this.data = data;
    }

    @Override
    public SimpleTreeNode addChild(T childData) {
        CustomTreeNode<T> childNode = new CustomTreeNode<T>(this, childData);
        this.children.add(childNode);
        return childNode;
    }

    @Override
    public void addChildren(T... childrenData) {
       for (T childData : childrenData) {
           addChild(childData);
       }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return null;
    }
}
