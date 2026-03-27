package br.com.gamerank.service;

import br.com.gamerank.model.Player;
import br.com.gamerank.structures.implementations.hash.HashTableChaining;
import br.com.gamerank.structures.implementations.queue.QueueWithStacks;
import br.com.gamerank.structures.implementations.heap.MaxHeap;
import br.com.gamerank.structures.implementations.tree.AVLTree;

public class RankingService {

    private HashTableChaining<Integer, Player> jogadores;
    private QueueWithStacks<Player> fila;
    private MaxHeap<Player> heap;
    private AVLTree<Player> avl;

    public RankingService() {
        jogadores = new HashTableChaining<>(100);
        fila = new QueueWithStacks<>(100);
        heap = new MaxHeap<>(100);
        avl = new AVLTree<>();
    }

    // ================= JOGADORES =================

    public void cadastrarJogador(int id, String nome) {
        if (jogadores.containsKey(id)) {
            System.out.println("Jogador já existe!");
            return;
        }

        Player p = new Player(id, nome);
        jogadores.put(id, p);
        heap.insert(p);
        avl.insert(p);

        System.out.println("Jogador cadastrado: " + p.getNome());
    }

    public void buscarJogador(int id) {
        Player p = jogadores.get(id);

        if (p == null) System.out.println("Jogador não encontrado!");
        else System.out.println(p);
    }

    public void removerJogador(int id) {
        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("Jogador não encontrado!");
            return;
        }

        jogadores.remove(id);
        avl.remove(p);
        // Heap não suporta remoção arbitrária — reconstruir do hash
        reconstruirHeap();

        System.out.println("Jogador removido: " + p.getNome());
    }

    // ================= FILA =================

    public void entrarFila(int id) {
        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("Jogador não existe!");
            return;
        }

        fila.enqueue(p);
        System.out.println(p.getNome() + " entrou na fila.");
    }

    public void iniciarPartida() {
        if (fila.isEmpty()) {
            System.out.println("Fila vazia!");
            return;
        }

        Player p1 = fila.dequeue();
        Player p2 = fila.dequeue();

        if (p2 == null) {
            System.out.println("Não há jogadores suficientes na fila!");
            // devolve p1 pra fila
            fila.enqueue(p1);
            return;
        }

        System.out.println("Partida iniciada: " + p1.getNome() + " vs " + p2.getNome());

        Player vencedor = Math.random() > 0.5 ? p1 : p2;

        // CORRIGIDO: remove da AVL antes de mudar pontuação, depois reinsere
        avl.remove(vencedor);
        vencedor.setPontuacao(vencedor.getPontuacao() + 10);
        avl.insert(vencedor);

        // Heap: reconstruir para refletir nova pontuação
        reconstruirHeap();

        System.out.println("Vencedor: " + vencedor.getNome()
                + " | Nova pontuação: " + vencedor.getPontuacao());
    }

    // ================= RANKING =================

    public void mostrarTopJogador() {
        Player top = heap.peek();

        if (top == null) System.out.println("Nenhum jogador cadastrado.");
        else System.out.println("Top jogador: " + top);
    }

    public void listarRanking() {
        if (avl.isEmpty()) {
            System.out.println("Nenhum jogador cadastrado.");
            return;
        }

        System.out.println("=== Ranking (ordem crescente de pontuação) ===");
        int[] pos = {1};
        avl.inOrder(p -> {
            System.out.println(pos[0]++ + "º - " + p);
        });
    }

    public void atualizarPontuacao(int id, int pontos) {
        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("Jogador não encontrado!");
            return;
        }

        // CORRIGIDO: remove antes de mudar, reinsere depois
        avl.remove(p);
        p.setPontuacao(pontos);
        avl.insert(p);

        reconstruirHeap();

        System.out.println("Pontuação de " + p.getNome() + " atualizada para " + pontos);
    }

    // ================= AUXILIAR =================

    // Reconstrói a heap do zero a partir do hash (necessário pois heap não remove arbitrariamente)
    private void reconstruirHeap() {
        heap = new MaxHeap<>(100);
        jogadores.forEach(p -> heap.insert(p));
    }
}