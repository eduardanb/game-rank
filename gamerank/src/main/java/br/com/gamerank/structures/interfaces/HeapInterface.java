package br.com.gamerank.structures.interfaces;

public interface HeapInterface<T extends Comparable<T>> {

    public void insert(T element);
    public T extractMax();
    public T peek();
    public boolean isEmpty();
    public boolean isFull();
    public int size();
}