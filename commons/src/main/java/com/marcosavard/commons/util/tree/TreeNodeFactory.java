package com.marcosavard.commons.util.tree;

public abstract class TreeNodeFactory<T> {
    public abstract SimpleTreeNode<T> createRoot(T data);

    public static class SwingTreeNodeFactory<T> extends TreeNodeFactory<T> {

        @Override
        public SimpleTreeNode<T> createRoot(T data) {
            SimpleTreeNode<T> root = SwingTreeNode.createRoot(data);
            return root;
        }
    }

    public static class CustomTreeNodeFactory<T> extends TreeNodeFactory<T> {
        @Override
        public SimpleTreeNode<T> createRoot(T data) {
            SimpleTreeNode<T> root = new CustomTreeNode(null, data);
            return root;
        }
    }
}
