package com.skorobahatko;

import java.util.*;
import java.util.stream.Collectors;

public class HashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_TABLE_CAPACITY = 16;

    private Entry<K, V>[] table;

    private final double loadFactor = 0.75;

    private int size;

    public HashMap() {
        this(DEFAULT_TABLE_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        table = (Entry<K, V>[]) new Entry[capacity];
    }

    public HashMap(Map<? extends K, ? extends V> m) {
        putAll(m);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (table == null || isEmpty())
            return false;
        Entry<K, V> e = getEntryByKey(key);
        if (e == null)
            return false;
        return (key == null) ? e.getKey() == null : key.equals(e.getKey());
    }

    @Override
    public boolean containsValue(Object value) {
        if (table == null || isEmpty())
            return false;
        for (Entry<K, V> e : table) {
            if (e != null) {
                if (value == null ?
                        e.getValue() == null :
                        value.equals(e.getValue()))
                    return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Entry<K, V> e = getEntryByKey(key);
        return (e == null) ? null : e.getValue();
    }

    @Override
    public V put(K key, V value) {
        V result = null;
        if (containsKey(key)) {
            Entry<K, V> e = getEntryByKey(key);
            result = e.getValue();
            e.setValue(value);
        } else {
            int newSize = size + 1;
            int threshold = (int) (table.length * loadFactor);
            if (newSize == threshold)
                reinitialize();
            int index = getIndexByKey(key);
            addEntry(index, key, value);
            size = newSize;
        }
        return result;
    }

    @Override
    public V remove(Object key) {
        V result = null;
        if (containsKey(key)) {
            Entry<K, V> e = getEntryByKey(key);
            result = e.getValue();
            table[getIndexByKey(key)] = null;
            size--;
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach((k, v) -> put(k, v));
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++)
            table[i] = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .map(Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<K, V> e = it.next();
            sb.append(e.getKey())
                    .append("=")
                    .append(e.getValue());
            if (!it.hasNext()) {
                sb.append("}");
                break;
            }
            sb.append(", ");
        }
        return sb.toString();
    }

    private int getIndexByKey(Object key) {
        return (key == null) ? 0 : key.hashCode() % table.length;
    }

    private Entry<K, V> getEntryByKey(Object key) {
        int index = getIndexByKey(key);
        return table[index];
    }

    private void addEntry(int index, K key, V value) {
        table[index] = new Entry<>(key, value);
    }

    @SuppressWarnings("unchecked")
    private void reinitialize() {
        int newTableSize = table.length * 2;
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newTableSize];
        for (Entry<K, V> e : table) {
            if (e != null) {
                int indexToPut = getIndexByKey(e.getKey());
                newTable[indexToPut] = e;
            }
        }
        table = newTable;
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (o == this)
                return true;
            if (!(o instanceof Entry))
                return false;
            Entry<K, V> that = (Entry<K, V>) o;
            return (that.getKey()==null ?
                    this.getKey()==null : that.getKey().equals(this.getKey()))  &&
                    (that.getValue()==null ?
                            this.getValue()==null : that.getValue().equals(this.getValue()));
        }

        @Override
        public int hashCode() {
            return (getKey()==null   ? 0 : getKey().hashCode()) ^
                    (getValue()==null ? 0 : getValue().hashCode());
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private class EntrySet implements Set<Map.Entry<K, V>> {

        @Override
        public int size() {
            return HashMap.this.size();
        }

        @Override
        public boolean isEmpty() {
            return HashMap.this.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return HashMap.this.containsKey(o);
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new Iterator<Map.Entry<K, V>>() {

                int cursor = 0;
                int lastRet = -1;

                @Override
                public boolean hasNext() {
                    return cursor < table.length;
                }

                @Override
                public Map.Entry<K, V> next() {
                    if (!hasNext())
                        throw new NoSuchElementException();
                    lastRet = cursor++;
                    return table[lastRet];
                }

                @Override
                public void remove() {
                    if (lastRet == -1)
                        throw new IllegalStateException();
                    table[lastRet] = null;
                    lastRet = -1;
                    HashMap.this.size--;
                }
            };
        }

        @Override
        public Object[] toArray() {
            //TODO
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            //TODO
            return null;
        }

        @Override
        public boolean add(Map.Entry<K, V> kvEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            return HashMap.this.remove(o) != null;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object o : c) {
                if (!HashMap.this.containsKey(o))
                    return false;
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends Map.Entry<K, V>> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean result = false;
            for (int i = 0; i < table.length; i++) {
                Entry<K, V> e = table[i];
                if (!c.contains(e)) {
                    table[i] = null;
                    HashMap.this.size--;
                    result = true;
                }
            }
            return result;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean result = false;
            for (Object o : c) {
                if (HashMap.this.containsKey(o)) {
                    HashMap.this.remove(o);
                    result = true;
                }
            }
            return result;
        }

        @Override
        public void clear() {
            HashMap.this.clear();
        }
    }
}
