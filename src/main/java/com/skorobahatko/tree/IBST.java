package com.skorobahatko.tree;

public interface IBST<T extends Comparable<T>> {

    public Node<T> find(T key);

    public void insert(T key);

    public void remove(T key);

    public String toString();

}
