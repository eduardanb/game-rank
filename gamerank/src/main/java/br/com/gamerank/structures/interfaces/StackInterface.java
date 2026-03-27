package br.com.gamerank.structures.interfaces;

import br.com.gamerank.exceptions.StackOverflowException;
import br.com.gamerank.exceptions.StackUnderflowException;

public interface StackInterface<T> {

    public void push(T elem) throws StackOverflowException;
    public T pop() throws StackUnderflowException;
    public T top();
    public boolean isEmpty();
    public boolean isFull();
}