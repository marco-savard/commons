package com.marcosavard.commons.util.collection;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class TreeNodeImpl<T> implements MutableTreeNode {
    private T userObject;
    private MutableTreeNode parent;
    private List<MutableTreeNode> children = new ArrayList<>();

    private DefaultMutableTreeNode nd;

    public static TreeNodeImpl createRoot(String data) {
        TreeNodeImpl root = new TreeNodeImpl(null, data);
        return root;
    }

    public TreeNodeImpl addChild(String childData) {
        TreeNodeImpl child = new TreeNodeImpl(this, childData);
        return child;
    }

    private TreeNodeImpl(TreeNodeImpl<T> parent, T userObject) {
        this.parent = parent;
        this.userObject = userObject;

        if (parent != null) {
            int idx = parent.getChildCount();
            this.parent.insert(this, idx);
        }
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        Enumeration<MutableTreeNode> enums = Collections.enumeration(this.children);
        return enums;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public TreeNode getChildAt(int index) {
        return children.get(index);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return this.parent;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean isRoot() {
        return (parent == null);
    }

    @Override
    public String toString() {
        return toShortString();
    }

    public String toShortString() {
        return this.userObject.toString();
    }

    public String toLongString() {
        List<String> items = new ArrayList<>();

        for (MutableTreeNode child : children) {
            items.add(((TreeNodeImpl<T>)child).getUserObject().toString());
        }

        return this.userObject.toString() + " [" + String.join(", ", items) + "]";
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        children.add(index, child);
    }

    public void remove(int childIndex) {
        remove(children.get(childIndex));
    }

    @Override
    public void remove(MutableTreeNode node) {
        children.remove(node);
        node.setParent(null);
    }

    @Override
    public void setUserObject(Object object) {
        this.userObject = (T)object;
    }

    @Override
    public void removeFromParent() {
        this.parent.remove(this);
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        this.parent = parent;
    }

    private Object getUserObject() {
        return userObject;
    }

    public void removeAllChildren() {
        for (int i = getChildCount()-1; i >= 0; i--) {
            remove(i);
        }
    }

    public int getLevel() {
        return getPath().length - 1;
    }


    public TreeNodeImpl[] getPath() {
        List<TreeNode> pathToRoot = new ArrayList<>();
        TreeNode ancestor = this;

        do {
            pathToRoot.add(ancestor);
            ancestor = ancestor.getParent();
        } while (ancestor != null);

        Collections.reverse(pathToRoot);
        return pathToRoot.toArray(new TreeNodeImpl[0]);
    }

    public TreeNodeImpl getRoot() {
        return getPath()[0];
    }


    public String toNestedString() {
        String nodeData = this.userObject.toString();
        String childrenData = children.isEmpty() ? "" : " " + toNestedString(children);
        return nodeData + childrenData;
    }

    public String toNestedString(List<MutableTreeNode> nodes) {
        List<String> items = new ArrayList<>();

        for (MutableTreeNode node : nodes) {
            items.add(((TreeNodeImpl<T>)node).toNestedString());
        }

        return "[" + String.join(", ", items) + "]";
    }


}
