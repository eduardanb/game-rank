package br.com.gamerank.structures;

import br.com.gamerank.structures.implementations.queue.QueueWithStacks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

    private QueueWithStacks<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new QueueWithStacks<>(10);
    }

    @Test
    void testFilaInicialmenteVazia() {
        assertTrue(queue.isEmpty());
        assertFalse(queue.isFull());
    }

    @Test
    void testEnqueueUmElemento() {
        queue.enqueue(10);
        assertFalse(queue.isEmpty());
        assertEquals(10, queue.head());
    }

    @Test
    void testOrdemFIFO() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testDequeueRetornaPrimeiro() {
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.head());
    }

    @Test
    void testHeadNaoRemove() {
        queue.enqueue(42);
        assertEquals(42, queue.head());
        assertEquals(42, queue.head());
        assertFalse(queue.isEmpty());
    }

    @Test
    void testDequeueFilaVaziaRetornaNull() {
        assertNull(queue.dequeue());
    }

    @Test
    void testHeadFilaVaziaRetornaNull() {
        assertNull(queue.head());
    }

    @Test
    void testEnqueueDequeueIntercalados() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        queue.enqueue(3);
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testTransferenciaEntreAsDuasPilhas() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        queue.enqueue(4);
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
    }

    @Test
    void testFilaCheia() {
        QueueWithStacks<Integer> q = new QueueWithStacks<>(3);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        assertTrue(q.isFull());
    }

    @Test
    void testMultiplosEnqueueDequeue() {
        for (int i = 1; i <= 5; i++) queue.enqueue(i);
        for (int i = 1; i <= 5; i++) assertEquals(i, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testFilaVaziaAposRemoverTudo() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testContainsElementoNaStackEntrada() {
        queue.enqueue(10);
        queue.enqueue(20);
        assertTrue(queue.contains(10));
        assertTrue(queue.contains(20));
    }

    @Test
    void testContainsElementoNaStackSaida() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.dequeue(); // força transferência para stackSaida, remove 1
        assertTrue(queue.contains(2));
        assertTrue(queue.contains(3));
    }

    @Test
    void testContainsElementoInexistente() {
        queue.enqueue(10);
        assertFalse(queue.contains(99));
    }

    @Test
    void testContainsFilaVaziaRetornaFalse() {
        assertFalse(queue.contains(5));
    }

    @Test
    void testContainsNaoPermiteDuplicataNaFila() {
        queue.enqueue(42);
        assertTrue(queue.contains(42));
        if (!queue.contains(42)) {
            queue.enqueue(42);
        }
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testContainsDepoisDeDequeueElementoSai() {
        queue.enqueue(10);
        assertTrue(queue.contains(10));
        queue.dequeue();
        assertFalse(queue.contains(10));
    }

    @Test
    void testContainsComElementosEmAmbasPilhas() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.dequeue(); // força transferência, remove 1
        queue.enqueue(4); // 4 vai para stackEntrada, 2 e 3 estão em stackSaida
        assertTrue(queue.contains(2));
        assertTrue(queue.contains(3));
        assertTrue(queue.contains(4));
        assertFalse(queue.contains(1));
    }

}