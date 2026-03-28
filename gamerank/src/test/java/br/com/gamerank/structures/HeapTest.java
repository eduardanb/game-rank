package br.com.gamerank.structures;

import br.com.gamerank.structures.implementations.heap.MaxHeap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeapTest {

    private MaxHeap<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new MaxHeap<>(10);
    }

    @Test
    void testHeapInicialmenteVazia() {
        assertTrue(heap.isEmpty());
        assertFalse(heap.isFull());
        assertEquals(0, heap.size());
    }

    @Test
    void testInsertUmElemento() {
        heap.insert(10);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        assertEquals(10, heap.peek());
    }

    @Test
    void testPeekRetornaMaior() {
        heap.insert(10);
        heap.insert(40);
        heap.insert(20);
        assertEquals(40, heap.peek());
    }

    @Test
    void testPeekNaoRemove() {
        heap.insert(30);
        assertEquals(30, heap.peek());
        assertEquals(30, heap.peek());
        assertEquals(1, heap.size());
    }

    @Test
    void testPeekHeapVaziaRetornaNull() {
        assertNull(heap.peek());
    }

    @Test
    void testExtractMaxRetornaMaior() {
        heap.insert(10);
        heap.insert(50);
        heap.insert(30);
        assertEquals(50, heap.extractMax());
    }

    @Test
    void testExtractMaxOrdemDecrescente() {
        heap.insert(10);
        heap.insert(40);
        heap.insert(20);
        heap.insert(30);
        assertEquals(40, heap.extractMax());
        assertEquals(30, heap.extractMax());
        assertEquals(20, heap.extractMax());
        assertEquals(10, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testExtractMaxHeapVaziaRetornaNull() {
        assertNull(heap.extractMax());
    }

    @Test
    void testPropriedadeMaxHeapAposInserts() {
        heap.insert(5);
        heap.insert(3);
        heap.insert(8);
        heap.insert(1);
        heap.insert(9);
        heap.insert(2);
        assertEquals(9, heap.peek());
    }

    @Test
    void testHeapCheio() {
        MaxHeap<Integer> h = new MaxHeap<>(3);
        h.insert(1);
        h.insert(2);
        h.insert(3);
        assertTrue(h.isFull());
    }

    @Test
    void testSizeAposInsertEExtract() {
        heap.insert(10);
        heap.insert(20);
        heap.insert(30);
        assertEquals(3, heap.size());
        heap.extractMax();
        assertEquals(2, heap.size());
    }

    @Test
    void testInsertMesmoValor() {
        heap.insert(10);
        heap.insert(10);
        assertEquals(2, heap.size());
        assertEquals(10, heap.extractMax());
        assertEquals(10, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testPropriedadeHeapAposExtract() {
        heap.insert(10);
        heap.insert(50);
        heap.insert(30);
        heap.insert(20);
        heap.insert(40);
        heap.extractMax(); // remove 50
        assertEquals(40, heap.peek());
    }

    @Test
    void testInsertExtractIntercalados() {
        heap.insert(10);
        heap.insert(30);
        assertEquals(30, heap.extractMax());
        heap.insert(50);
        heap.insert(20);
        assertEquals(50, heap.extractMax());
        assertEquals(20, heap.extractMax());
        assertEquals(10, heap.extractMax());
        assertTrue(heap.isEmpty());
    }
}