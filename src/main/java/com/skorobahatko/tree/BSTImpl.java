package com.skorobahatko.tree;

public class BSTImpl<T extends Comparable<T>> implements IBST<T> {

    private Node<T> root;

    public BSTImpl() {
        root = null;
    }

    @Override
    public Node<T> find(T key) {
        return find(root, key);
    }

    private Node<T> find(Node<T> root, T key) {
        if (root == null)
            return null;
        T rootKey = root.getKey();
        int cmp = rootKey.compareTo(key);
        if (cmp == 0)
            return root;
        return (cmp < 0) ?
                find(root.getRight(), key) :
                find(root.getLeft(), key);
    }

    @Override
    public void insert(T key) {
        root = insert(root, key);

    }

    private Node<T> insert(Node<T> root, T key) {
        if (root == null)
            return new Node<>(key, null, null);
        T rootKey = root.getKey();
        int cmp = rootKey.compareTo(key);
        if (cmp == 0)
            return root;
        if (cmp < 0)
            root.setRight(insert(root.getRight(), key));
        if (cmp > 0)
            root.setLeft(insert(root.getLeft(), key));
        return root;
    }

    @Override
    public void remove(T key) {
        root = remove(root, key);
    }

    private Node<T> remove(Node<T> root, T key) {
        if (root == null)
            return null;
        T rootKey = root.getKey();
        int cmp = rootKey.compareTo(key);
        if (cmp < 0)
            root.setRight(remove(root.getRight(), key));
        else if (cmp > 0)
            root.setLeft(remove(root.getLeft(), key));
        else {
            if (root.getLeft() == null && root.getRight() == null)
                return null;
            else if (root.getLeft() != null && root.getRight() == null) {
                T leftKey = root.getLeft().getKey();
                root.setKey(leftKey);
                root.setLeft(null);
            } else if (root.getLeft() == null && root.getRight() != null) {
                T rightKey = root.getRight().getKey();
                root.setKey(rightKey);
                root.setRight(null);
            } else {
                Node<T> rightSubTree = root.getRight();
                if (rightSubTree.getLeft() == null) {
                    root.setKey(rightSubTree.getKey());
                    root.setRight(rightSubTree.getRight());
                } else {
                    Node<T> minNode = getMinNode(rightSubTree);
                    root.setKey(minNode.getKey());
                    remove(rightSubTree, minNode.getKey());
                }
            }
        }
        return root;
    }

    public String toString() {
        return root.toString();
    }

    private Node<T> getMinNode(Node<T> root) {
        Node<T> minNode = root;
        while (minNode.getLeft() != null)
            minNode = minNode.getLeft();
        return minNode;
    }

    public static void main(String[] args) {

        BSTImpl<Integer> bst = new BSTImpl<>();

        bst.insert(3);
        bst.insert(2);
        bst.insert(8);
        bst.insert(6);
        bst.insert(10);

        System.out.println(bst);

        bst.insert(5);
        bst.insert(7);
        bst.insert(9);
        bst.insert(11);

        System.out.println(bst);

        bst.remove(8);

        System.out.println(bst);
    }
}
