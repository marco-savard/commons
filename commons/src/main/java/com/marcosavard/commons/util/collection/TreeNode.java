package com.marcosavard.commons.util.collection;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeNode<T> {
    private T data;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children = new ArrayList<>();

    private DefaultMutableTreeNode nd;

    public static TreeNode createRoot(String data) {
        TreeNode root = new TreeNode(null, data);
        return root;
    }

    public TreeNode addChild(String childData) {
        TreeNode child = new TreeNode(this, childData);
        return child;
    }

    private TreeNode(TreeNode<T> parent, T data) {
        this.parent = parent;
        this.data = data;

        if (parent != null) {
            this.parent.children.add(this);
        }
    }

    public int getChildCount() {
        return children.size();
    }

    public TreeNode getChildAt(int index) {
        return children.get(index);
    }

    public int getLevel() {
        return getPath().length - 1;
    }

    public TreeNode getParent() {
        return this.parent;
    }

    public TreeNode[] getPath() {
        List<TreeNode> pathToRoot = new ArrayList<>();
        TreeNode ancestor = this;

        do {
            pathToRoot.add(ancestor);
            ancestor = ancestor.getParent();
        } while (ancestor != null);

        Collections.reverse(pathToRoot);
        return pathToRoot.toArray(new TreeNode[0]);
    }

    public TreeNode getRoot() {
        return getPath()[0];
    }

    public void remove(TreeNode child) {
        children.remove(child);
        child.parent = null;
    }

    public void remove(int childIndex) {
        remove(children.get(childIndex));
    }

    public void removeAllChildren() {
        for (int i = getChildCount()-1; i >= 0; i--) {
            remove(i);
        }
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean isRoot() {
        return (parent == null);
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

    public String toNestedString() {
        String nodeData = this.data.toString();
        String childrenData = children.isEmpty() ? "" : " " + toNestedString(children);
        return nodeData + childrenData;
    }

    public String toNestedString(List<TreeNode<T>> nodes) {
        List<String> items = new ArrayList<>();

        for (TreeNode<T> node : nodes) {
            items.add(node.toNestedString());
        }

        return "[" + String.join(", ", items) + "]";
    }


}
