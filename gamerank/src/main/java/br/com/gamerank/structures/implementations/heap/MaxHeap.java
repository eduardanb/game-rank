package br.com.gamerank.structures.implementations.heap;

import br.com.gamerank.structures.interfaces.HeapInterface;

@SuppressWarnings("unchecked")
public class MaxHeap<T extends Comparable<T>> implements HeapInterface<T> {

    private T[] heap;
    private int size;
    private int capacity;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    @Override
    public void insert(T element) {
        if (isFull()) {
            System.out.println("Heap cheia!");
            return;
        }

        heap[size] = element;
        subir(size);
        size++;
    }

    @Override
    public T extractMax() {
        if (isEmpty()) return null;

        T max = heap[0];

        heap[0] = heap[size - 1];
        size--;

        descer(0);

        return max;
    }

    @Override
    public T peek() {
        if (isEmpty()) return null;
        return heap[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public int size() {
        return size;
    }

    // ================= MÉTODOS AUXILIARES =================

    private void subir(int index) {
        int pai = (index - 1) / 2;

        while (index > 0 && heap[index].compareTo(heap[pai]) > 0) {
            trocar(index, pai);
            index = pai;
            pai = (index - 1) / 2;
        }
    }

    private void descer(int index) {
        int maior = index;
        int esquerda = 2 * index + 1;
        int direita = 2 * index + 2;

        if (esquerda < size && heap[esquerda].compareTo(heap[maior]) > 0) {
            maior = esquerda;
        }

        if (direita < size && heap[direita].compareTo(heap[maior]) > 0) {
            maior = direita;
        }

        if (maior != index) {
            trocar(index, maior);
            descer(maior);
        }
    }

    private void trocar(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}