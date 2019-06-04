package com.cafe24.app.discount.core.store;

public interface Store<T> {

    T get(String key);

    void put(String key, T value);

    void remove(String key);

    boolean contains(String key);
}
