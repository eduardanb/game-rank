package br.com.gamerank.structures.interfaces;

import br.com.gamerank.exceptions.StackOverflowException;
import br.com.gamerank.exceptions.StackUnderflowException;

public interface StackInterface<T> {

    void push(T elem) throws StackOverflowException;
    T pop() throws StackUnderflowException;
    T top();
    boolean isEmpty();
    boolean isFull();
    T[] toArray();
}