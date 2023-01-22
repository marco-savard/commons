package com.marcosavard.commons.util.tree;

public abstract class TreeNodeFactory<T> {
    public abstract SimpleTreeNode<T> createRoot(T data);
}
