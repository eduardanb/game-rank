package br.com.gamerank.structures.implementations.queue;

import br.com.gamerank.structures.interfaces.QueueInterface;
import br.com.gamerank.structures.implementations.stack.StackLinkedList;
import br.com.gamerank.exceptions.StackOverflowException;
import br.com.gamerank.exceptions.StackUnderflowException;

public class QueueWithStacks<T> implements QueueInterface<T> {

    private StackLinkedList<T> stackEntrada;
    private StackLinkedList<T> stackSaida;
    private int capacity;

    public QueueWithStacks(int capacity) {
        this.capacity = capacity;
        this.stackEntrada = new StackLinkedList<>(capacity);
        this.stackSaida = new StackLinkedList<>(capacity);
    }

    @Override
    public void enqueue(T element) {
        try {
            if (isFull()) {
                System.out.println("Fila cheia!");
                return;
            }

            stackEntrada.push(element);

        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public T dequeue() {
        try {
            if (isEmpty()) {
                System.out.println("Fila vazia!");
                return null;
            }

            if (stackSaida.isEmpty()) {
                transferir();
            }

            return stackSaida.pop();

        } catch (StackUnderflowException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (StackOverflowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T head() {
        try {
            if (isEmpty()) return null;

            if (stackSaida.isEmpty()) {
                transferir();
            }

            return stackSaida.top();

        } catch (Exception e) {
            return null;
        }
    }

    private void transferir() throws StackUnderflowException, StackOverflowException {
        while (!stackEntrada.isEmpty()) {
            stackSaida.push(stackEntrada.pop());
        }
    }

    @Override
    public boolean isEmpty() {
        return stackEntrada.isEmpty() && stackSaida.isEmpty();
    }

    @Override
    public boolean isFull() {
        return stackEntrada.isFull();
    }
}