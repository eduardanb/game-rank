package br.com.gamerank.structures;

import br.com.gamerank.structures.implementations.list.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    @Test
    void testListaInicialmenteVazia() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testInsertUmElemento() {
        list.insert(10);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    void testInsertMultiplosElementos() {
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assertEquals(3, list.size());
    }

    @Test
    void testSearchElementoExistente() {
        list.insert(42);
        assertEquals(42, list.search(42));
    }

    @Test
    void testSearchElementoInexistente() {
        list.insert(10);
        assertNull(list.search(99));
    }

    @Test
    void testSearchListaVazia() {
        assertNull(list.search(5));
    }

    @Test
    void testRemoveElementoExistente() {
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.remove(2);
        assertEquals(2, list.size());
        assertNull(list.search(2));
    }

    @Test
    void testRemoveCabeca() {
        list.insert(1);
        list.insert(2);
        list.remove(1);
        assertEquals(1, list.size());
        assertNull(list.search(1));
        assertEquals(2, list.search(2));
    }

    @Test
    void testRemoveUltimo() {
        list.insert(1);
        list.insert(2);
        list.remove(2);
        assertEquals(1, list.size());
        assertNull(list.search(2));
    }

    @Test
    void testRemoveElementoInexistente() {
        list.insert(10);
        list.remove(99);
        assertEquals(1, list.size());
    }

    @Test
    void testRemoveListaVazia() {
        assertDoesNotThrow(() -> list.remove(5));
    }

    @Test
    void testToArrayOrdemCorreta() {
        list.insert(1);
        list.insert(2);
        list.insert(3);
        Object[] arr = list.toArray();
        assertEquals(3, arr.length);
        assertEquals(1, arr[0]);
        assertEquals(2, arr[1]);
        assertEquals(3, arr[2]);
    }

    @Test
    void testToArrayListaVazia() {
        Object[] arr = list.toArray();
        assertEquals(0, arr.length);
    }

    @Test
    void testInsertNullNaoAfetaLista() {
        list.insert(null);
        assertTrue(list.isEmpty());
    }

    @Test
    void testInsertRemoveSequencia() {
        list.insert(10);
        list.insert(20);
        list.remove(10);
        list.insert(30);
        assertEquals(2, list.size());
        assertNull(list.search(10));
        assertEquals(20, list.search(20));
        assertEquals(30, list.search(30));
    }
}