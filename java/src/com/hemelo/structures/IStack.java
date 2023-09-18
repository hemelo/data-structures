package com.hemelo.structures;

/**
 * A stack is a last in, first out (LIFO) abstract data type
 * @param <T>
 */
public interface IStack<T> {
    boolean push(T value);
    boolean contains(T value);
    boolean validate();
    boolean remove(T value);
    T pop();
    T peek();
    int size();
    void clear();
}
