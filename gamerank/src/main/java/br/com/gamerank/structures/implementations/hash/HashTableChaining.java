package br.com.gamerank.structures.implementations.hash;

import br.com.gamerank.structures.interfaces.HashTableInterface;
import br.com.gamerank.structures.nodes.HashNode;

public class HashTableChaining<K, V> implements HashTableInterface<K, V> {

    private HashNode<K, V>[] table;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTableChaining(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = new HashNode[capacity];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    @Override
    public void put(K key, V value) {
        int index = hash(key);

        HashNode<K, V> current = table[index];

        // verifica se já existe → atualiza
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }

        // insere no início (chaining)
        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.setNext(table[index]);
        table[index] = newNode;

        size++;
    }

    @Override
    public V get(K key) {
        int index = hash(key);

        HashNode<K, V> current = table[index];

        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }

        return null;
    }

    @Override
    public void remove(K key) {
        int index = hash(key);

        HashNode<K, V> current = table[index];
        HashNode<K, V> previous = null;

        while (current != null) {
            if (current.getKey().equals(key)) {

                if (previous == null) {
                    table[index] = current.getNext();
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

    // Adicionar dentro de HashTableChaining.java
    public void forEach(java.util.function.Consumer<V> action) {
        for (int i = 0; i < capacity; i++) {
            HashNode<K, V> current = table[i];
            while (current != null) {
                action.accept(current.getValue());
                current = current.getNext();
            }
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
}