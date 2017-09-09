package com.skorobahatko;

import java.util.*;

public class LinkedList<E> implements List<E>, Deque<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    LinkedList() {
        first = last = null;
    }

    LinkedList(Collection<? extends E> c) {
        addAll(c);
    }


    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, null, null);
        if (isEmpty())
            first = last = newNode;
        else {
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, null, null);
        if (isEmpty())
            first = last = newNode;
        else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E removeFirst() {
        checkItemForReturnIsExist();
        E elem = first.elem;
        if (size() == 1)
            first = last = null;
        else {
            Node<E> nextNode = first.next;
            nextNode.prev = null;
            first = null;
            first = nextNode;
        }
        size--;
        return elem;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E removeLast() {
        checkItemForReturnIsExist();
        E elem = last.elem;
        if (size() == 1)
            last = first = null;
        else {
            Node<E> prevNode = last.prev;
            prevNode.next = null;
            last = null;
            last = prevNode;
        }
        size--;
        return elem;
    }

    @Override
    public E pollFirst() {
        try {
            return removeFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public E pollLast() {
        try {
            return removeLast();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public E getFirst() {
        checkItemForReturnIsExist();
        return first.elem;
    }

    @Override
    public E getLast() {
        checkItemForReturnIsExist();
        return last.elem;
    }

    @Override
    public E peekFirst() {
        return isEmpty() ? null : first.elem;
    }

    @Override
    public E peekLast() {
        return isEmpty() ? null : last.elem;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        Node<E> node = first;
        while (node != null) {
            if (isNodeValueEqualsToObject(node, o))
                return removeNode(node);
            //noinspection unchecked
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<E> node = last;
        while (node != null) {
            if (isNodeValueEqualsToObject(node, o))
                return removeNode(node);
            //noinspection unchecked
            node = node.prev;
        }
        return false;
    }

    private boolean isNodeValueEqualsToObject(Node<E> node, Object o) {
        E elem = node.elem;
        return (o == null ? elem == null : o.equals(elem));
    }

    @SuppressWarnings("unchecked")
    private boolean removeNode(Node<E> node) {
        if (node == first)
            removeFirst();
        else if (node == last)
            removeLast();
        else {
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = node.next;
            next.prev = node.prev;
            size--;
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        checkItemForReturnIsExist();
        return first.elem;
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescIter();
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
        for (E elem : this) {
            if (o == null ? elem == null : o.equals(elem))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node<E> curNode = first;
        for (int i = 0; i < size; i++) {
            arr[i] = curNode.elem;
            //noinspection unchecked
            curNode = curNode.next;
        }
        return arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(toArray(), size, a.getClass());
        System.arraycopy(toArray(), 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
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
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null || c.size() == 0)
            return false;

        checkIndex(index);

        Node<E> firstNode = null;
        Node<E> prevNode = null;
        for (E elem : c) {
            if (firstNode == null) {
                prevNode = new Node<>(elem, null, null);
                firstNode = prevNode;
            } else {
                Node<E> curNode = new Node<>(elem, prevNode, null);
                prevNode.next = curNode;
                prevNode = curNode;
            }
        }

        Node<E> nodeAtIndex = getNodeByIndex(index);
        Node<E> beforeNode = nodeAtIndex.prev;

        nodeAtIndex.prev = prevNode;
        prevNode.next = nodeAtIndex;

        beforeNode.next = firstNode;
        firstNode.prev = beforeNode;

        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (contains(o))
                remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (int i = 0; i < size();) {
            if (!c.contains(get(i)))
                remove(i);
            else
                i++;
        }
        return false;
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).elem;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        Node<E> nodeAtIndex = getNodeByIndex(index);
        E oldValue = nodeAtIndex.elem;
        nodeAtIndex.elem = element;
        return oldValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(int index, E element) {
        checkIndex(index);
        Node<E> nodeAtIndex = getNodeByIndex(index);
        Node<E> prevNode = nodeAtIndex.prev;
        Node<E> newNode = new Node<>(element, prevNode, nodeAtIndex);
        prevNode.next = newNode;
        nodeAtIndex.prev = newNode;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        Node<E> nodeAtIndex = getNodeByIndex(index);

        E elem = nodeAtIndex.elem;

        if (size() == 1)
            clear();
        else {
            Node<E> prevNode = nodeAtIndex.prev;
            Node<E> nextNode = nodeAtIndex.next;
            if (prevNode != null)
                prevNode.next = nodeAtIndex.next;
            else
                first = nodeAtIndex.next;
            if (nextNode != null)
                nextNode.prev = nodeAtIndex.prev;
            else
                last = nodeAtIndex.prev;
            size--;
        }
        return elem;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> curNode = first;
        for (int i = 0; i < size; i++) {
            E elem = curNode.elem;
            if (o == null ? elem == null : o.equals(elem))
                return i;
            //noinspection unchecked
            curNode = curNode.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<E> curNode = last;
        for (int i = size - 1; i >= 0; i++) {
            E elem = curNode.elem;
            if (o == null ? elem == null : o.equals(elem))
                return i;
            //noinspection unchecked
            curNode = curNode.prev;
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIter(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> subList(int fromIndex, int toIndex) {
        Object[] allElems = toArray();
        int subArraySize = toIndex - fromIndex;
        Object[] subArray = new Object[subArraySize];
        System.arraycopy(allElems, fromIndex, subArray, 0, subArraySize);
        return Arrays.asList((E[]) subArray);
    }

    private void checkItemForReturnIsExist() {
        if (size == 0)
            throw new NoSuchElementException();
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
    }

    @SuppressWarnings("unchecked")
    private Node<E> getNodeByIndex(int index) {
        int midPoint = size / 2;
        Node<E> result = null;
        if (index < midPoint) {
            result = first;
            for (int i = 0; i < index; i++)
                result = result.next;
        } else {
            result = last;
            for (int i = size-1; i > index; i--)
                result = result.prev;
        }
        return result;
    }

    private static class Node<T> {

        T elem;
        Node prev;
        Node next;

        Node() {}

        Node(T elem, Node<T> prev, Node<T> next) {
            this.elem = elem;
            this.prev = prev;
            this.next = next;
        }
    }

    private class Iter implements Iterator<E> {

        int cursor;
        int lastElem = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            lastElem = cursor;
            return get(cursor++);
        }

        @Override
        public void remove() {
            if (lastElem == -1)
                throw new IllegalStateException();
            LinkedList.this.remove(lastElem);
            lastElem = -1;
            cursor--;
        }
    }

    private class ListIter extends Iter implements ListIterator<E> {

        ListIter(int index) {
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            lastElem = --cursor;
            return get(lastElem);
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
        public void set(E e) {
            if (lastElem == -1)
                throw new IllegalStateException();
            LinkedList.this.set(lastElem, e);
        }

        @Override
        public void add(E e) {
            LinkedList.this.add(cursor++, e);
            lastElem = -1;
        }
    }

    private class DescIter implements Iterator<E> {

        private final ListIter iter = new ListIter(size());

        @Override
        public boolean hasNext() {
            return iter.hasPrevious();
        }

        @Override
        public E next() {
            return iter.previous();
        }

        @Override
        public void remove() {
            iter.remove();
        }
    }
}
