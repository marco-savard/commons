package com.marcosavard.commons.util.tree;

public class CustomTreeNodeFactory <T> extends TreeNodeFactory<T>{
    @Override
    public SimpleTreeNode<T> createRoot(T data) {
        SimpleTreeNode<T> root = new CustomTreeNode<>(null, data);
        return root;
    }
}
