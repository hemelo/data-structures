package com.hemelo.interfaces;

import java.util.Collection;

public interface Collectable<T> {
    Collection<T> toCollection();
}
