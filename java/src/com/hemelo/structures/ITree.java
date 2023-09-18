package com.hemelo.structures;

import com.hemelo.interfaces.Collectable;

/**
 * Collection of nodes (starting at a root node)
 * @param <T>
 */
public interface ITree<T> extends Collectable<T> {
    boolean add(T value);
    boolean contains(T value);
    boolean validate();
    T remove(T value);
    void clear();
}
