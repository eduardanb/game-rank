package br.com.gamerank.structures.interfaces;

public interface QueueInterface<T> {

    public void enqueue(T element);
    public T dequeue();
    public T head();
    public boolean isEmpty();
    public boolean isFull();
}