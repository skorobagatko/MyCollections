package com.skorobahatko;

import java.util.*;

public class ArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;

    private int size;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(final int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException();
        elements = new Object[initialCapacity];
    }

    public ArrayList(final Collection<? extends T> c) {
        this(c.size());
        System.arraycopy(c.toArray(), 0, elements, 0, c.size());
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
        for (Object elem : elements) {
            if (o == null ? elem == null : o.equals(elem))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size)
            return (T1[]) Arrays.copyOf(elements, size, a.getClass());
        System.arraycopy(elements, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int index = 0; index < size; index++) {
            Object curElem = get(index);
            if (o==null ? curElem==null : o.equals(curElem))
                return (remove(index) != null);
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int inputSize = c.size();
        int newSize = inputSize + this.size;
        if (newSize > elements.length)
            enlarge(newSize);
        System.arraycopy(c.toArray(), 0, elements, size, inputSize);
        size += inputSize;
        return inputSize > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        if (index == size)
            return addAll(c);
        int inputSize = c.size();
        int newSize = inputSize + this.size;
        if (newSize > elements.length)
            enlarge(newSize);
        int destPos = index + inputSize;
        System.arraycopy(elements, index, elements, destPos, size-index);
        System.arraycopy(c.toArray(), 0, elements, index, inputSize);
        size += inputSize;
        return inputSize > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean listChanged = false;
        for (Object o : c) {
            if (contains(o)) {
                remove(o);
                listChanged = true;
            }
        }
        return listChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean listChanged = false;
        for (int index = 0; index < size;) {
            if (!c.contains(get(index))) {
                remove(index);
                listChanged = true;
                continue;
            }
            index++;
        }
        return listChanged;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++)
            elements[i] = null;
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        checkIndex(index);
        T oldElem = (T) elements[index];
        elements[index] = element;
        return oldElem;
    }

    @Override
    public void add(int index, T element) {
        if (size+1 > elements.length)
            enlarge();
        if (index == size)
            elements[size] = element;
        else {
            int elemeNum = size - index;
            System.arraycopy(elements, index, elements, index+1, elemeNum);
            set(index, element);
        }
        size++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldElem = (T) elements[index];
        if (index != size-1) {
            int elemNum = size - index - 1;
            System.arraycopy(elements, index+1, elements, index, elemNum);
        }
        elements[size--] = null;
        return oldElem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            Object curElem = elements[i];
            if (o == null ? curElem == null : o.equals(curElem))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            Object curElem = elements[i];
            if (o == null ? curElem == null : o.equals(curElem))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        checkIndex(index);
        return new ListIter(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size)
            throw new IndexOutOfBoundsException();
        if (fromIndex > toIndex)
            throw new IllegalArgumentException();
        int elemNum = toIndex - fromIndex;
        Object[] subArray = new Object[elemNum];
        System.arraycopy(elements, fromIndex, subArray, 0, elemNum);
        return Arrays.asList((T[]) subArray);
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    private void enlarge() {
        int oldSize = elements.length;
        int newSize = oldSize + (oldSize / 2) + 1;
        elements = Arrays.copyOf(elements, newSize);
    }

    private void enlarge(int newSize) {
        elements = Arrays.copyOf(elements, newSize);
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
    }

    private class Iter implements Iterator<T> {

        int cursor;
        int lastElem = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            lastElem = cursor;
            return (T) elements[cursor++];
        }

        @Override
        public void remove() {
            if (lastElem == -1)
                throw new IllegalStateException();
            ArrayList.this.remove(lastElem);
            lastElem = -1;
            cursor--;
        }
    }

    private class ListIter extends Iter implements ListIterator<T> {

        ListIter(int index) {
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            lastElem = --cursor;
            return (T) elements[cursor];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {
            if (lastElem == -1)
                throw new IllegalStateException();
            elements[lastElem] = t;
        }

        @Override
        public void add(T t) {
            ArrayList.this.add(cursor++, t);
            lastElem = -1;
        }
    }

}
