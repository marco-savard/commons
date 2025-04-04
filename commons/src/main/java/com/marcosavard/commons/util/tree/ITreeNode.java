package com.marcosavard.commons.util.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.*;

public interface ITreeNode<T> extends javax.swing.tree.TreeNode {

    public Object getUserObject();

    ITreeNode addChild(T childData);

    void addChildren(T... childData);

    static <T> boolean equals(ITreeNode<T> treeNode1, Object that) {
        boolean equal = false;

        if (that instanceof TreeNode) {
            TreeNode treeNode2 = (TreeNode)that;
            Object data1 = treeNode1.getUserObject();
            Object data2 = getUserObject(treeNode2);

            if (Objects.equals(data1, data2)) {
                int n = treeNode1.getChildCount();
                equal = (n == treeNode2.getChildCount());

                if (equal) {
                    for (int i = 0; i < n; i++) {
                        equal &= equals((ITreeNode)treeNode1.getChildAt(i), treeNode2.getChildAt(i));
                    }
                }
            }
        }
        return equal;
    }

    static Object getUserObject(TreeNode treeNode) {
        Object userObject;

        if (treeNode instanceof ITreeNode) {
            userObject = ((ITreeNode)treeNode).getUserObject();
        } else if (treeNode instanceof DefaultMutableTreeNode) {
            userObject = ((DefaultMutableTreeNode)treeNode).getUserObject();
        } else {
            userObject = null;
        }

        return userObject;
    }

    public static String toString(TreeNode node) {
        String nodeData = node.toString();
        List<TreeNode> children = TreeNodeUtil.getChildren(node);
        List<String> childrenData = toString(children);
        return nodeData + (childrenData.isEmpty() ? "" : childrenData);
    }

    public static List<String> toString(List<TreeNode> children) {
        List<String> data = new ArrayList<>();

        for (TreeNode node : children) {
            data.add(ITreeNode.toString(node));
        }

        return data;
    }

    static <T> String toLongString(ITreeNode<T> treeNode, Vector<TreeNode> children) {
        Object data = treeNode.getUserObject();
        List<ITreeNode<T>> childList = toList(children);
        return ITreeNode.toLongString(data, childList);
    }

    private static <T> List<ITreeNode<T>> toList(Vector<TreeNode> items) {
        List<ITreeNode<T>> list = new ArrayList<>();
        for (TreeNode item : items) {
            list.add((ITreeNode<T>)item);
        }

        return list;
    }

    public static <T> String toLongString(Object userObject, List<ITreeNode<T>> children) {
        List<String> items = new ArrayList<>();

        for (ITreeNode child : children) {
            if (child instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode mutableTreeNode = (DefaultMutableTreeNode)child;
                String data = String.valueOf(mutableTreeNode.getUserObject());
                items.add(data);
            }
        }

        return userObject.toString() + " [" + String.join(", ", items) + "]";
    }


    static String toNestedString(Object userObject, Vector<TreeNode> children) {
        String nodeData = userObject.toString();
        boolean hasChild = (children != null) && ! children.isEmpty();
        String childrenData = hasChild ? " " + toNestedString(children) : "";
        return nodeData + childrenData;
    }

    private static String toNestedString(Vector<TreeNode> nodes) {
        List<String> items = new ArrayList<>();

        for (TreeNode node : nodes) {
            if (node instanceof SwingTreeNode<?>) {
                SwingTreeNode<?> sn = (SwingTreeNode<?>)node;
                String s = sn.toNestedString();
                items.add(s);
            }
        }

        return "[" + String.join(", ", items) + "]";
    }


}
