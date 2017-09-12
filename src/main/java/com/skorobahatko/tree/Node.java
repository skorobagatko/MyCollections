package com.skorobahatko.tree;

public class Node<T> {

    private T key;
    private Node<T> left;
    private Node<T> right;

    public Node(T key, Node<T> left, Node<T> right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public String toString() {
        return nodeToString(this);
    }

    private String nodeToString(Node<T> root) {
        if (root == null)
            return "null";
        return nodeToString(root.getLeft()) +
                " <-- " + root.getKey() +
                " --> " + nodeToString(root.getRight());
    }
}
