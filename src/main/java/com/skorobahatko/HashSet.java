package com.skorobahatko;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class HashSet<E> implements Set<E> {

    private static final Object DEFAULT_VALUE = new Object();

    private HashMap<E, Object> map;

    public HashSet() {
        map = new HashMap<>();
    }

    public HashSet(Collection<? extends E> c) {
        int capacity = 16;
        if (!c.isEmpty())
            capacity = c.size() + (int)(c.size() * 0.25);
        map = new HashMap<>(capacity);
        addAll(c);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return map.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return map.keySet().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return map.put(e, DEFAULT_VALUE) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == DEFAULT_VALUE;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!map.containsKey(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E e : c) {
            if (add(e))
                result = true;
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean[] result = new boolean[1];
        map.keySet().removeIf(e -> {
            if (!c.contains(e)) {
                result[0] = true;
                return true;
            }
            return false;
        });
        return result[0];
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        Iterator<E> it = map.keySet().iterator();
        while (it.hasNext()) {
            E e = it.next();
            if (c.contains(e)) {
                it.remove();
                result = true;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
