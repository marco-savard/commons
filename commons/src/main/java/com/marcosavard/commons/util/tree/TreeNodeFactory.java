package com.marcosavard.commons.util.tree;

public abstract class TreeNodeFactory<T> {
    public abstract ITreeNode<T> createRoot(T data);

    public static class SwingTreeNodeFactory<T> extends TreeNodeFactory<T> {

        @Override
        public ITreeNode<T> createRoot(T data) {
            ITreeNode<T> root = SwingTreeNode.createRoot(data);
            return root;
        }
    }

    public static class CustomTreeNodeFactory<T> extends TreeNodeFactory<T> {
        @Override
        public ITreeNode<T> createRoot(T data) {
            ITreeNode<T> root = new CustomTreeNode(null, data);
            return root;
        }
    }
}
