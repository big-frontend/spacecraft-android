package com.electrolytej.collection;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashSet<E> extends AbstractSet<E> implements Serializable {
    private final Set<E> weakHashSet;

    public WeakHashSet() {
        weakHashSet = Collections.newSetFromMap(new WeakHashMap<>());
    }

    @Override
    public boolean add(E element) {
        return weakHashSet.add(element);
    }

    @Override
    public boolean remove(Object element) {
        return weakHashSet.remove(element);
    }

    @Override
    public boolean contains(Object element) {
        return weakHashSet.contains(element);
    }

    @Override
    public int size() {
        return weakHashSet.size();
    }

    @Override
    public void clear() {
        weakHashSet.clear();
    }

    @Override
    public boolean isEmpty() {
        return weakHashSet.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return weakHashSet.iterator();
    }
}
