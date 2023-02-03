package com.marcosavard.commons.util.tree;

public abstract class TreeNodeFactory<T> {
    public static final TreeNodeFactory SWING = new SwingTreeNodeFactory();
    public static final TreeNodeFactory CUSTOM = new CustomTreeNodeFactory();

    public abstract ITreeNode<T> createRoot(T data);

    private static class SwingTreeNodeFactory<T> extends TreeNodeFactory<T> {

        @Override
        public ITreeNode<T> createRoot(T data) {
            ITreeNode<T> root = SwingTreeNode.createRoot(data);
            return root;
        }
    }

    private static class CustomTreeNodeFactory<T> extends TreeNodeFactory<T> {
        @Override
        public ITreeNode<T> createRoot(T data) {
            ITreeNode<T> root = new CustomTreeNode(null, data);
            return root;
        }
    }
}
