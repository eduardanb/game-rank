package br.com.gamerank.structures;

import br.com.gamerank.structures.implementations.stack.StackLinkedList;
import br.com.gamerank.exceptions.StackOverflowException;
import br.com.gamerank.exceptions.StackUnderflowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    private StackLinkedList<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new StackLinkedList<>(5);
    }

    @Test
    void testPilhaInicialmenteVazia() {
        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
    }

    @Test
    void testPushUmElemento() throws StackOverflowException {
        stack.push(10);
        assertFalse(stack.isEmpty());
        assertEquals(10, stack.top());
    }

    @Test
    void testPushMultiplosElementos() throws StackOverflowException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.top());
    }

    @Test
    void testPopRetornaUltimo() throws StackOverflowException, StackUnderflowException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.top());
    }

    @Test
    void testPopOrdemLIFO() throws StackOverflowException, StackUnderflowException {
        stack.push(10);
        stack.push(20);
        stack.push(30);
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testTopNaoRemove() throws StackOverflowException {
        stack.push(42);
        assertEquals(42, stack.top());
        assertEquals(42, stack.top());
        assertFalse(stack.isEmpty());
    }

    @Test
    void testTopPilhaVaziaRetornaNull() {
        assertNull(stack.top());
    }

    @Test
    void testPilhaCheia() throws StackOverflowException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertTrue(stack.isFull());
    }

    @Test
    void testPushPilhaCheiaLancaExcecao() throws StackOverflowException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertThrows(StackOverflowException.class, () -> stack.push(6));
    }

    @Test
    void testPopPilhaVaziaLancaExcecao() {
        assertThrows(StackUnderflowException.class, () -> stack.pop());
    }

    @Test
    void testPushPopIntercalados() throws StackOverflowException, StackUnderflowException {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testIsFullDepoisDePopNaoEstaCheia() throws StackOverflowException, StackUnderflowException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.pop();
        assertFalse(stack.isFull());
    }

    @Test
    void testToArrayOrdemCorreta() throws StackOverflowException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Object[] arr = stack.toArray();
        assertEquals(3, arr.length);
        assertEquals(1, arr[0]);
        assertEquals(2, arr[1]);
        assertEquals(3, arr[2]);
    }

    @Test
    void testToArrayPilhaVazia() {
        Object[] arr = stack.toArray();
        assertEquals(0, arr.length);
    }

    @Test
    void testToArrayNaoAlteraPilha() throws StackOverflowException {
        stack.push(10);
        stack.push(20);
        stack.toArray();
        assertFalse(stack.isEmpty());
        assertEquals(20, stack.top());
    }
}