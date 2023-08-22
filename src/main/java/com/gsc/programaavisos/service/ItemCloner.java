package com.gsc.programaavisos.service;

@FunctionalInterface
public interface ItemCloner<T> {
    T cloneItem(T item);
}
