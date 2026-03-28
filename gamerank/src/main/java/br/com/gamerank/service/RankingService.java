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
        System.out.println("\n[PASSO 1] Verificando se jogador ja existe...");
        System.out.println("          -> Hash Table: containsKey(" + id + ")");

        if (jogadores.containsKey(id)) {
            System.out.println("          -> Jogador ja existe! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Nao encontrado. Pode cadastrar!");

        Player p = new Player(id, nome);

        System.out.println("\n[PASSO 2] Inserindo na Hash Table...");
        System.out.println("          -> hash(" + id + ") = " + (id % 100) + " (bucket " + (id % 100) + ")");
        jogadores.put(id, p);
        System.out.println("          -> Jogador armazenado em Hash[" + (id % 100) + "]");

        System.out.println("\n[PASSO 3] Inserindo na MaxHeap...");
        System.out.println("          -> heap.insert(" + nome + ", pts=" + p.getPontuacao() + ")");
        heap.insert(p);
        System.out.println("          -> Subindo elemento ate posicao correta (sift up)");
        System.out.println("          -> Heap atualizada! Tamanho: " + heap.size());

        System.out.println("\n[PASSO 4] Inserindo na AVL Tree...");
        System.out.println("          -> avl.insert(" + nome + ", pts=" + p.getPontuacao() + ")");
        avl.insert(p);
        System.out.println("          -> Arvore rebalanceada se necessario");
        System.out.println("          -> AVL atualizada! Tamanho: " + avl.size());

        System.out.println("\n[OK] Jogador \"" + nome + "\" cadastrado com sucesso!");
    }

    public void buscarJogador(int id) {
        System.out.println("\n[PASSO 1] Calculando bucket na Hash Table...");
        System.out.println("          -> hash(" + id + ") = " + (id % 100) + " (bucket " + (id % 100) + ")");

        System.out.println("\n[PASSO 2] Percorrendo o encadeamento no bucket " + (id % 100) + "...");
        System.out.println("          -> Hash Table: get(" + id + ")");

        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("          -> Jogador nao encontrado no bucket!");
        } else {
            System.out.println("          -> Jogador encontrado!");
            System.out.println("\n[OK] " + p);
        }
    }

    public void removerJogador(int id) {
        System.out.println("\n[PASSO 1] Buscando jogador na Hash Table...");
        System.out.println("          -> Hash Table: get(" + id + ")");

        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("          -> Jogador nao encontrado! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Jogador \"" + p.getNome() + "\" encontrado!");

        System.out.println("\n[PASSO 2] Removendo da Hash Table...");
        System.out.println("          -> Hash Table: remove(" + id + ") do bucket " + (id % 100));
        jogadores.remove(id);
        System.out.println("          -> Removido da Hash Table!");

        System.out.println("\n[PASSO 3] Removendo da AVL Tree...");
        System.out.println("          -> AVL Tree: remove(" + p.getNome() + ", pts=" + p.getPontuacao() + ")");
        avl.remove(p);
        System.out.println("          -> Arvore rebalanceada apos remocao");
        System.out.println("          -> AVL atualizada! Tamanho: " + avl.size());

        System.out.println("\n[PASSO 4] Reconstruindo a MaxHeap...");
        System.out.println("          -> Heap nao suporta remocao arbitraria");
        System.out.println("          -> Percorrendo todos os jogadores da Hash Table...");
        reconstruirHeap();
        System.out.println("          -> Heap reconstruida! Tamanho: " + heap.size());

        System.out.println("\n[OK] Jogador \"" + p.getNome() + "\" removido com sucesso!");
    }

    // ================= FILA =================

    public void entrarFila(int id) {
        System.out.println("\n[PASSO 1] Buscando jogador na Hash Table...");
        System.out.println("          -> Hash Table: get(" + id + ")");

        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("          -> Jogador nao encontrado! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Jogador \"" + p.getNome() + "\" encontrado!");

        System.out.println("\n[PASSO 2] Verificando se jogador ja esta na fila...");
        System.out.println("          -> Fila: contains(" + p.getNome() + ")");
        System.out.println("          -> Percorrendo stackEntrada e stackSaida...");

        if (fila.contains(p)) {
            System.out.println("          -> \"" + p.getNome() + "\" ja esta na fila! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Nao esta na fila. Pode entrar!");

        System.out.println("\n[PASSO 3] Adicionando na fila...");
        System.out.println("          -> Fila: enqueue(" + p.getNome() + ")");
        System.out.println("          -> stackEntrada.push(" + p.getNome() + ")");
        fila.enqueue(p);
        System.out.println("          -> Jogador empilhado em stackEntrada!");

        System.out.println("\n[OK] \"" + p.getNome() + "\" entrou na fila de espera!");
    }

    public void iniciarPartida() {
        System.out.println("\n[PASSO 1] Verificando se ha jogadores na fila...");

        if (fila.isEmpty()) {
            System.out.println("          -> Fila vazia! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Fila nao esta vazia. Iniciando...");

        System.out.println("\n[PASSO 2] Retirando primeiro jogador da fila...");
        System.out.println("          -> Fila: dequeue()");
        System.out.println("          -> stackSaida vazia? Transferindo stackEntrada -> stackSaida...");
        Player p1 = fila.dequeue();
        System.out.println("          -> Jogador 1: \"" + p1.getNome() + "\" retirado!");

        System.out.println("\n[PASSO 3] Retirando segundo jogador da fila...");
        System.out.println("          -> Fila: dequeue()");
        Player p2 = fila.dequeue();

        if (p2 == null) {
            System.out.println("          -> Apenas 1 jogador na fila! Devolvendo jogador...");
            fila.enqueue(p1);
            System.out.println("          -> \"" + p1.getNome() + "\" devolvido para a fila.");
            return;
        }

        System.out.println("          -> Jogador 2: \"" + p2.getNome() + "\" retirado!");

        System.out.println("\n[PASSO 4] Sorteando vencedor...");
        Player vencedor = Math.random() > 0.5 ? p1 : p2;
        Player perdedor = vencedor == p1 ? p2 : p1;
        System.out.println("          -> Vencedor sorteado: \"" + vencedor.getNome() + "\"");

        System.out.println("\n[PASSO 5] Atualizando pontuacao do vencedor...");
        System.out.println("          -> AVL Tree: remove(" + vencedor.getNome() + ", pts=" + vencedor.getPontuacao() + ")");
        avl.remove(vencedor);
        System.out.println("          -> Pontuacao anterior: " + vencedor.getPontuacao() + " pts");
        vencedor.setPontuacao(vencedor.getPontuacao() + 10);
        System.out.println("          -> Nova pontuacao: " + vencedor.getPontuacao() + " pts (+10)");
        System.out.println("          -> AVL Tree: insert(" + vencedor.getNome() + ", pts=" + vencedor.getPontuacao() + ")");
        avl.insert(vencedor);
        System.out.println("          -> Arvore rebalanceada!");

        System.out.println("\n[PASSO 6] Reconstruindo a MaxHeap...");
        System.out.println("          -> Pontuacao alterada, heap precisa ser atualizada");
        reconstruirHeap();
        System.out.println("          -> Heap reconstruida! Top atual: \""
                + heap.peek().getNome() + "\" com " + heap.peek().getPontuacao() + " pts");

        System.out.println("\n[OK] Partida encerrada!");
        System.out.println("     Vencedor : " + vencedor.getNome() + " (" + vencedor.getPontuacao() + " pts)");
        System.out.println("     Perdedor : " + perdedor.getNome() + " (" + perdedor.getPontuacao() + " pts)");
    }

    // ================= RANKING =================

    public void mostrarTopJogador() {
        System.out.println("\n[PASSO 1] Consultando raiz da MaxHeap...");
        System.out.println("          -> MaxHeap: peek() -> acessa heap[0] diretamente");

        Player top = heap.peek();

        if (top == null) {
            System.out.println("          -> Heap vazia! Nenhum jogador cadastrado.");
            return;
        }

        System.out.println("          -> Raiz da heap encontrada em O(1)!");
        System.out.println("\n[OK] Top jogador: " + top);
    }

    public void listarRanking() {
        System.out.println("\n[PASSO 1] Verificando se a AVL Tree esta vazia...");

        if (avl.isEmpty()) {
            System.out.println("          -> AVL vazia! Nenhum jogador cadastrado.");
            return;
        }

        System.out.println("          -> AVL nao esta vazia. Iniciando percurso...");
        System.out.println("\n[PASSO 2] Realizando percurso in-order na AVL Tree...");
        System.out.println("          -> In-order: visita esquerda -> raiz -> direita");
        System.out.println("          -> Resultado: jogadores em ordem crescente de pontuacao\n");

        System.out.println("=== Ranking ===");
        int[] pos = {1};
        avl.inOrder(p -> {
            System.out.println(pos[0]++ + "o - " + p);
        });
        System.out.println("===============");
    }

    public void atualizarPontuacao(int id, int pontos) {
        System.out.println("\n[PASSO 1] Buscando jogador na Hash Table...");
        System.out.println("          -> Hash Table: get(" + id + ")");

        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("          -> Jogador nao encontrado! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Jogador \"" + p.getNome() + "\" encontrado!");

        System.out.println("\n[PASSO 2] Removendo da AVL Tree antes de alterar pontuacao...");
        System.out.println("          -> AVL Tree: remove(" + p.getNome() + ", pts=" + p.getPontuacao() + ")");
        System.out.println("          -> Necessario pois a AVL ordena por pontuacao!");
        avl.remove(p);
        System.out.println("          -> Removido e arvore rebalanceada!");

        System.out.println("\n[PASSO 3] Atualizando pontuacao...");
        System.out.println("          -> Pontuacao anterior: " + p.getPontuacao() + " pts");
        p.setPontuacao(pontos);
        System.out.println("          -> Nova pontuacao: " + pontos + " pts");

        System.out.println("\n[PASSO 4] Reinserindo na AVL Tree com nova pontuacao...");
        System.out.println("          -> AVL Tree: insert(" + p.getNome() + ", pts=" + pontos + ")");
        avl.insert(p);
        System.out.println("          -> Arvore rebalanceada!");

        System.out.println("\n[PASSO 5] Reconstruindo a MaxHeap...");
        System.out.println("          -> Pontuacao alterada, heap precisa ser atualizada");
        reconstruirHeap();
        System.out.println("          -> Heap reconstruida! Top atual: \""
                + heap.peek().getNome() + "\" com " + heap.peek().getPontuacao() + " pts");

        System.out.println("\n[OK] Pontuacao de \"" + p.getNome() + "\" atualizada para " + pontos + " pts!");
    }

    // ================= AUXILIAR =================

    private void reconstruirHeap() {
        heap = new MaxHeap<>(100);
        jogadores.forEach(p -> heap.insert(p));
    }
}