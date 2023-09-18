package com.hemelo.structures;

import com.hemelo.interfaces.Collectable;

/**
 * If element A is a parent node of B then A is ordered with respect to key (B) with the same ordering applying across the heap.
 * Either the keys of parent nodes are always greater than or equal to those of the children
 * and the highest key is in the root node (this kind of heap is called max heap) or
 * the keys of parent nodes are less than or equal to those of the children (min heap).
 * @param <T>
 */
public interface IHeap<T> extends Collectable<T> {
    boolean add(T value);
    T getHeadValue();
    T removeHead();
    T remove(T value);
    void clear();
    boolean contains(T value);
    int size();
    boolean validate();
}
