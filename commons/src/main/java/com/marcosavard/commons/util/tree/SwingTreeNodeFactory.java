package com.marcosavard.commons.util.tree;

public class SwingTreeNodeFactory<T> extends TreeNodeFactory<T> {

    @Override
    public SimpleTreeNode createRoot(T data) {
        SimpleTreeNode<T> root = new SwingTreeNode<>(data);
        return root;
    }
}
