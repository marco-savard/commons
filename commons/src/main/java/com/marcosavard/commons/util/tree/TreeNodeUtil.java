package com.marcosavard.commons.util.tree;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeNodeUtil {

    public static List<TreeNode> getChildren(TreeNode node) {
        List<TreeNode> children = new ArrayList<>();
        int count = node.getChildCount();

        for (int i=0; i<count; i++) {
            TreeNode child = node.getChildAt(i);
            children.add(child);
        }

        return children;
    }

    public static TreeNode[] getPath(TreeNode node) {
        List<TreeNode> pathToRoot = new ArrayList<>();
        TreeNode ancestor = node;

        do {
            pathToRoot.add(ancestor);
            ancestor = ancestor.getParent();
        } while (ancestor != null);

        Collections.reverse(pathToRoot);
        TreeNode[] path = pathToRoot.toArray(new TreeNode[0]);
        return path;
    }

    public static TreeNode getRoot(TreeNode node) {
        return getPath(node)[0];
    }


    public static boolean isRoot(TreeNode root) {
        return (root.getParent() == null);
    }


}
