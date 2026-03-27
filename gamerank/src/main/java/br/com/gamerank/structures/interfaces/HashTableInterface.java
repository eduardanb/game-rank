package br.com.gamerank.structures.interfaces;

public interface HashTableInterface<K, V> {

    public void put(K key, V value);
    public V get(K key);
    public void remove(K key);
    public boolean containsKey(K key);
    public boolean isEmpty();
    public int size();
}