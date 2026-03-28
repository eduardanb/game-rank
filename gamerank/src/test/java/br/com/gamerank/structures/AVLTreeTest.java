package br.com.gamerank.structures;

import br.com.gamerank.structures.implementations.tree.AVLTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    private AVLTree<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new AVLTree<>();
    }

    @Test
    void testArvoreInicialmenteVazia() {
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

    @Test
    void testInsertUmElemento() {
        tree.insert(10);
        assertFalse(tree.isEmpty());
        assertEquals(1, tree.size());
        assertTrue(tree.contains(10));
    }

    @Test
    void testInsertMultiplosElementos() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        assertEquals(3, tree.size());
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(20));
        assertTrue(tree.contains(30));
    }

    @Test
    void testContainsElementoInexistente() {
        tree.insert(10);
        assertFalse(tree.contains(99));
    }

    @Test
    void testInsertDuplicadoNaoAumentaSize() {
        tree.insert(10);
        tree.insert(10);
        assertEquals(1, tree.size());
    }

    @Test
    void testInOrderRetornaOrdemCrescente() {
        tree.insert(30);
        tree.insert(10);
        tree.insert(20);
        List<Integer> result = new ArrayList<>();
        tree.inOrder(result::add);
        assertEquals(List.of(10, 20, 30), result);
    }

    @Test
    void testBalanceamentoRotacaoLL() {
        tree.insert(30);
        tree.insert(20);
        tree.insert(10);
        List<Integer> result = new ArrayList<>();
        tree.inOrder(result::add);
        assertEquals(List.of(10, 20, 30), result);
        assertEquals(3, tree.size());
    }

    @Test
    void testBalanceamentoRotacaoRR() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        List<Integer> result = new ArrayList<>();
        tree.inOrder(result::add);
        assertEquals(List.of(10, 20, 30), result);
    }

    @Test
    void testBalanceamentoRotacaoLR() {
        tree.insert(30);
        tree.insert(10);
        tree.insert(20);
        List<Integer> result = new ArrayList<>();
        tree.inOrder(result::add);
        assertEquals(List.of(10, 20, 30), result);
    }

    @Test
    void testBalanceamentoRotacaoRL() {
        tree.insert(10);
        tree.insert(30);
        tree.insert(20);
        List<Integer> result = new ArrayList<>();
        tree.inOrder(result::add);
        assertEquals(List.of(10, 20, 30), result);
    }

    @Test
    void testRemoveElementoExistente() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.remove(20);
        assertEquals(2, tree.size());
        assertFalse(tree.contains(20));
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(30));
    }

    @Test
    void testRemoveRaiz() {
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.remove(20);
        assertFalse(tree.contains(20));
        assertEquals(2, tree.size());
        List<Integer> result = new ArrayList<>();
        tree.inOrder(result::add);
        assertEquals(List.of(10, 30), result);
    }

    @Test
    void testRemoveComDoisFilhos() {
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(25);
        tree.insert(35);
        tree.remove(30);
        assertFalse(tree.contains(30));
        assertEquals(4, tree.size());
        List<Integer> result = new ArrayList<>();
        tree.inOrder(result::add);
        assertEquals(List.of(10, 20, 25, 35), result);
    }

    @Test
    void testRemoveElementoInexistenteNaoAlteraArvore() {
        tree.insert(10);
        tree.insert(20);
        tree.remove(99);
        assertEquals(2, tree.size());
    }

    @Test
    void testRemoveUnicoElemento() {
        tree.insert(10);
        tree.remove(10);
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

    @Test
    void testInsertMuitosElementosMantemOrdem() {
        int[] vals = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27};
        for (int v : vals) tree.insert(v);
        List<Integer> result = new ArrayList<>();
        tree.inOrder(result::add);
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i) < result.get(i + 1));
        }
        assertEquals(vals.length, tree.size());
    }
}