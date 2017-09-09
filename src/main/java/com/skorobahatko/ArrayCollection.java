package com.skorobahatko;

import java.util.*;

public class ArrayCollection<E> implements Collection<E> {

    private static final int DEFAULT_ARRAY_LENGTH = 10;

    private Object[] elements;

    private int size;

    public ArrayCollection() {
        this(DEFAULT_ARRAY_LENGTH);
    }

    public ArrayCollection(int capacity) {
        elements = new Object[capacity];
    }

    public ArrayCollection(Collection<? extends E> c) {
        // TODO
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof List || obj instanceof Set)
            return false;

        ArrayCollection<E> that = (ArrayCollection<E>) obj;

        if (this.size != that.size)
            return false;

        Iterator<E> iter1 = this.iterator();
        Iterator<E> iter2 = that.iterator();
        while (iter1.hasNext() && iter2.hasNext()) {
            E o1 = iter1.next();
            E o2 = iter2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2)))
                return false;
        }

        return true;
    }

    public int hashCode() {
        int hash = 1;
        for (Object o : elements) {
            hash = hash * 31 + ((o==null) ? 0 : o.hashCode());
        }
        return hash;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Object obj : elements) {
            if ((obj == null) ? o == null : obj.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayCollectionIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a == null)
            throw new NullPointerException();
        final T[] arr = (a.length >= size) ?
                a :
                (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size());
        System.arraycopy(elements, 0, arr, 0, size);
        return arr;
    }

    @Override
    public boolean add(E e) {
        if (size == elements.length)
            enlarge();
        elements[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                System.arraycopy(elements, i + 1, elements, i, size - i);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isChanged = false;
        for (E e : c) {
            add(e);
            if (!isChanged)
                isChanged = true;
        }
        return isChanged;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChanged = false;
        for (Object o : c) {
            if (contains(o)) {
                remove(o);
                if (!isChanged)
                    isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChanged = false;
        for (int i = 0; i < size; ) {
            Object elem = elements[i];
            if (!c.contains(elem)) {
                remove(elem);
                if (!isChanged)
                    isChanged = true;
                continue;
            }
            i++;
        }
        return isChanged;
    }

    @Override
    public void clear() {
        elements = new Object[DEFAULT_ARRAY_LENGTH];
        size = 0;
    }

    private void enlarge() {
        int newSize = elements.length * 2;
        Object[] newArray = new Object[newSize];
        System.arraycopy(elements, 0, newArray, 0, size);
        elements = newArray;
    }

    private class ArrayCollectionIterator implements Iterator<E> {

        private int index;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            return (E) elements[index++];
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
