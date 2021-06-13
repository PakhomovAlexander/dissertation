package com.github.sbst.core;

public interface GenericBuilder<T, B extends GenericBuilder<T, B>> {
    T build();
}