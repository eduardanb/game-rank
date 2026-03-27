package br.com.gamerank.structures.implementations.tree;

import br.com.gamerank.structures.interfaces.TreeInterface;
import br.com.gamerank.structures.nodes.AVLNode;

public class AVLTree<T extends Comparable<T>> implements TreeInterface<T> {

    private AVLNode<T> root;
    private int size;

    // ================= INSERT =================

    @Override
    public void insert(T element) {
        if (!contains(element)) size++;
        root = insert(root, element);
    }

    private AVLNode<T> insert(AVLNode<T> node, T element) {
        if (node == null) return new AVLNode<>(element);

        if (element.compareTo(node.getData()) < 0) {
            node.setLeft(insert(node.getLeft(), element));
        } else if (element.compareTo(node.getData()) > 0) {
            node.setRight(insert(node.getRight(), element));
        } else {
            return node; // não permite duplicado
        }

        atualizarAltura(node);
        return balancear(node);
    }

    // ================= REMOVE =================

    @Override
    public void remove(T element) {
        if (contains(element)) size--;
        root = remove(root, element);
    }

    private AVLNode<T> remove(AVLNode<T> node, T element) {
        if (node == null) return null;

        if (element.compareTo(node.getData()) < 0) {
            node.setLeft(remove(node.getLeft(), element));
        } else if (element.compareTo(node.getData()) > 0) {
            node.setRight(remove(node.getRight(), element));
        } else {
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();

            // CORRIGIDO: usa setData em vez de criar nó novo (evita perder filhos)
            AVLNode<T> sucessor = menor(node.getRight());
            node.setData(sucessor.getData());
            node.setRight(remove(node.getRight(), sucessor.getData()));
        }

        atualizarAltura(node);
        return balancear(node);
    }

    private AVLNode<T> menor(AVLNode<T> node) {
        while (node.getLeft() != null) node = node.getLeft();
        return node;
    }

    // ================= BALANCEAMENTO =================

    private AVLNode<T> balancear(AVLNode<T> node) {
        int fb = fatorBalanceamento(node);

        if (fb > 1 && fatorBalanceamento(node.getLeft()) >= 0)
            return rotacaoDireita(node);

        if (fb < -1 && fatorBalanceamento(node.getRight()) <= 0)
            return rotacaoEsquerda(node);

        if (fb > 1 && fatorBalanceamento(node.getLeft()) < 0) {
            node.setLeft(rotacaoEsquerda(node.getLeft()));
            return rotacaoDireita(node);
        }

        if (fb < -1 && fatorBalanceamento(node.getRight()) > 0) {
            node.setRight(rotacaoDireita(node.getRight()));
            return rotacaoEsquerda(node);
        }

        return node;
    }

    // ================= ROTAÇÕES =================

    private AVLNode<T> rotacaoDireita(AVLNode<T> y) {
        AVLNode<T> x = y.getLeft();
        AVLNode<T> t2 = x.getRight();

        x.setRight(y);
        y.setLeft(t2);

        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    private AVLNode<T> rotacaoEsquerda(AVLNode<T> x) {
        AVLNode<T> y = x.getRight();
        AVLNode<T> t2 = y.getLeft();

        y.setLeft(x);
        x.setRight(t2);

        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    // ================= AUXILIARES =================

    private int altura(AVLNode<T> node) {
        return node == null ? 0 : node.getHeight();
    }

    private void atualizarAltura(AVLNode<T> node) {
        node.setHeight(1 + Math.max(altura(node.getLeft()), altura(node.getRight())));
    }

    private int fatorBalanceamento(AVLNode<T> node) {
        return altura(node.getLeft()) - altura(node.getRight());
    }

    // ================= BUSCA =================

    @Override
    public boolean contains(T element) {
        return contains(root, element);
    }

    private boolean contains(AVLNode<T> node, T element) {
        if (node == null) return false;

        if (element.compareTo(node.getData()) < 0)
            return contains(node.getLeft(), element);
        else if (element.compareTo(node.getData()) > 0)
            return contains(node.getRight(), element);
        else
            return true;
    }

    // ================= IN-ORDER =================

    public void inOrder(java.util.function.Consumer<T> action) {
        inOrder(root, action);
    }

    private void inOrder(AVLNode<T> node, java.util.function.Consumer<T> action) {
        if (node == null) return;
        inOrder(node.getLeft(), action);
        action.accept(node.getData());
        inOrder(node.getRight(), action);
    }

    // ================= INTERFACE =================

    @Override
    public boolean isEmpty() { return root == null; }

    @Override
    public int size() { return size; }
}