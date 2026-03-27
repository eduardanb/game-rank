package br.com.gamerank.structures.interfaces;

public interface ListInterface<T> {

    public boolean isEmpty();
    public int size();
    public T search(T element);
    public void insert(T element);
    public void remove(T element);
    public T[] toArray();
}