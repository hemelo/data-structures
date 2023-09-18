package com.hemelo.structures;

import com.hemelo.interfaces.Default;

public interface IMap<K, V> extends Default<java.util.Map<K,V>> {
    V put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean validate();
    boolean contains(K key);
    void clear();
    int size();
}
