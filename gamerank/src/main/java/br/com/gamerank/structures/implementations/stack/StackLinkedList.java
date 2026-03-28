package br.com.gamerank.structures.implementations.stack;

import br.com.gamerank.exceptions.StackOverflowException;
import br.com.gamerank.exceptions.StackUnderflowException;
import br.com.gamerank.structures.interfaces.StackInterface;
import br.com.gamerank.structures.implementations.list.LinkedList;

public class StackLinkedList<T> implements StackInterface<T> {

    private LinkedList<T> list;
    private int capacity;

    public StackLinkedList(int capacity) {
        this.list = new LinkedList<>();
        this.capacity = capacity;
    }

    @Override
    public void push(T elem) throws StackOverflowException {
        if (isFull()) {
            throw new StackOverflowException("Pilha cheia!");
        }
        list.insert(elem);
    }

    @Override
    public T pop() throws StackUnderflowException {
        if (isEmpty()) {
            throw new StackUnderflowException("Pilha vazia!");
        }
        T[] array = list.toArray();
        T elemento = array[array.length - 1];
        list.remove(elemento);
        return elemento;
    }

    @Override
    public T top() {
        if (isEmpty()) return null;
        T[] array = list.toArray();
        return array[array.length - 1];
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean isFull() {
        return list.size() == capacity;
    }

    @Override
    public T[] toArray() {
        return list.toArray();
    }
}