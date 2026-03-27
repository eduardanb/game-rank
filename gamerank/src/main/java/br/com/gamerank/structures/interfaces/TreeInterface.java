package br.com.gamerank.structures.interfaces;

public interface TreeInterface<T extends Comparable<T>> {

    void insert(T element);

    void remove(T element);

    boolean contains(T element);

    boolean isEmpty();

    int size();
}