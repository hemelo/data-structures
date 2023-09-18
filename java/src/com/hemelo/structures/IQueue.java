package com.hemelo.structures;

import com.hemelo.interfaces.Collectable;
import com.hemelo.interfaces.Default;

/**
 * A queue is a first in, first out (FIFO) abstract data type
 * @param <T>
 */
public interface IQueue<T>  extends Collectable<T>, Default<java.util.Queue<T>>  {
    boolean add(T value);
    boolean contains(T value);
    boolean validate();
    boolean remove(T value);
    T poll();
    T peek();
    int size();
    void clear();
}
