package com.hemelo.structures;

import com.hemelo.interfaces.Collectable;
import com.hemelo.interfaces.Default;

public interface ISet<T> extends Collectable<T>, Default<java.util.Set<T>> {
    boolean add(T value);
    boolean remove(T value);
    boolean contains(T value);
    boolean validate();
    void clear();
    int size();
}
