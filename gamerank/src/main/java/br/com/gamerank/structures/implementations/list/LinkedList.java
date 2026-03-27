package br.com.gamerank.structures.implementations.list;

import br.com.gamerank.structures.interfaces.ListInterface;
import br.com.gamerank.structures.nodes.ListNode;

public class LinkedList<T> implements ListInterface<T> {

    private ListNode<T> head;
    private int size;

    public LinkedList() {
        this.head = new ListNode<>();
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return head.isNIL();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T search(T element) {
        if (element == null) return null;

        ListNode<T> current = head;

        while (!current.isNIL()) {
            if (current.getData().equals(element)) {
                return current.getData();
            }
            current = current.getNext();
        }

        return null;
    }

    @Override
    public void insert(T element) {
        if (element == null) return;

        ListNode<T> newNode = new ListNode<>(element, new ListNode<>());

        if (isEmpty()) {
            head = newNode;
        } else {
            ListNode<T> current = head;

            while (!current.getNext().isNIL()) {
                current = current.getNext();
            }

            current.setNext(newNode);
        }

        size++;
    }

    @Override
    public void remove(T element) {
        if (isEmpty() || element == null) return;

        ListNode<T> current = head;
        ListNode<T> previous = null;

        while (!current.isNIL()) {
            if (current.getData().equals(element)) {

                // remover cabeça
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }

                size--;
                return;
            }

            previous = current;
            current = current.getNext();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[size];

        ListNode<T> current = head;
        int i = 0;

        while (!current.isNIL()) {
            array[i++] = current.getData();
            current = current.getNext();
        }

        return array;
    }
}