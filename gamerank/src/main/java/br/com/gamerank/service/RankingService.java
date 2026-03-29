package br.com.gamerank.service;

import br.com.gamerank.model.Player;
import br.com.gamerank.structures.implementations.hash.HashTableChaining;
import br.com.gamerank.structures.implementations.queue.QueueWithStacks;
import br.com.gamerank.structures.implementations.heap.MaxHeap;
import br.com.gamerank.structures.implementations.tree.AVLTree;

public class RankingService {

    private static final int CAPACIDADE_FILA = 100;

    private HashTableChaining<Integer, Player> jogadores;
    private QueueWithStacks<Player> fila;
    private MaxHeap<Player> heap;
    private AVLTree<Player> avl;

    public RankingService() {
        jogadores = new HashTableChaining<>(100);
        fila = new QueueWithStacks<>(CAPACIDADE_FILA);
        heap = new MaxHeap<>(100);
        avl = new AVLTree<>();
    }

    // ================= JOGADORES =================

    public void cadastrarJogador(int id, String nome) {
        System.out.println("\n1. Verificando se jogador ja existe...");
        System.out.println("          -> Hash Table: containsKey(" + id + ")");

        if (jogadores.containsKey(id)) {
            System.out.println("          -> Jogador ja existe! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Nao encontrado. Pode cadastrar!");

        Player p = new Player(id, nome);

        System.out.println("\n2. Inserindo na Hash Table...");
        System.out.println("          -> hash(" + id + ") = " + (id % 100) + " (bucket " + (id % 100) + ")");
        jogadores.put(id, p);
        System.out.println("          -> Jogador armazenado em Hash[" + (id % 100) + "]");

        System.out.println("\n3. Inserindo na MaxHeap...");
        System.out.println("          -> heap.insert(" + nome + ", pts=" + p.getPontuacao() + ")");
        heap.insert(p);
        System.out.println("          -> Subindo elemento ate posicao correta (sift up)");
        System.out.println("          -> Heap atualizada! Tamanho: " + heap.size());

        System.out.println("\n4. Inserindo na AVL Tree...");
        System.out.println("          -> avl.insert(" + nome + ", pts=" + p.getPontuacao() + ")");
        avl.insert(p);
        System.out.println("          -> Arvore rebalanceada se necessario");
        System.out.println("          -> AVL atualizada! Tamanho: " + avl.size());

        System.out.println("\n-> Jogador \"" + nome + "\" cadastrado com sucesso!");
    }

    public void buscarJogador(int id) {
        System.out.println("\n1. Calculando bucket na Hash Table...");
        System.out.println("          -> hash(" + id + ") = " + (id % 100) + " (bucket " + (id % 100) + ")");

        System.out.println("\n2. Percorrendo o encadeamento no bucket " + (id % 100) + "...");
        System.out.println("          -> Hash Table: get(" + id + ")");

        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("          -> Jogador nao encontrado no bucket!");
        } else {
            System.out.println("          -> Jogador encontrado!");
            System.out.println("\n-> " + p);
        }
    }

    public void removerJogador(int id) {
        System.out.println("\n1. Buscando jogador na Hash Table...");
        System.out.println("          -> Hash Table: get(" + id + ")");

        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("          -> Jogador nao encontrado! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Jogador \"" + p.getNome() + "\" encontrado!");

        System.out.println("\n2. Removendo da Hash Table...");
        System.out.println("          -> Hash Table: remove(" + id + ") do bucket " + (id % 100));
        jogadores.remove(id);
        System.out.println("          -> Removido da Hash Table!");

        System.out.println("\n2.1 Removendo da fila de espera (se estiver)...");
        removerDaFilaPorId(id);
        System.out.println("          -> Fila atualizada!");

        System.out.println("\n3. Removendo da AVL Tree...");
        System.out.println("          -> AVL Tree: remove(" + p.getNome() + ", pts=" + p.getPontuacao() + ")");
        avl.remove(p);
        System.out.println("          -> Arvore rebalanceada apos remocao");
        System.out.println("          -> AVL atualizada! Tamanho: " + avl.size());

        System.out.println("\n4. Reconstruindo a MaxHeap...");
        System.out.println("          -> Heap nao suporta remocao arbitraria");
        System.out.println("          -> Percorrendo todos os jogadores da Hash Table...");
        reconstruirHeap();
        System.out.println("          -> Heap reconstruida! Tamanho: " + heap.size());

        System.out.println("\n-> Jogador \"" + p.getNome() + "\" removido com sucesso!");
    }

    // ================= FILA =================

    public void entrarFila(int id) {
        System.out.println("\n1. Buscando jogador na Hash Table...");
        System.out.println("          -> Hash Table: get(" + id + ")");

        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("          -> Jogador nao encontrado! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Jogador \"" + p.getNome() + "\" encontrado!");

        System.out.println("\n2. Verificando se a fila atingiu o limite tecnico...");

        if (fila.isFull()) {
            System.out.println("          -> Fila cheia! Operacao cancelada.");
            return;
        }

        System.out.println("\n3. Verificando se jogador ja esta na fila...");
        System.out.println("          -> Fila: contains(" + p.getNome() + ")");
        System.out.println("          -> Percorrendo stackEntrada e stackSaida...");

        if (fila.contains(p)) {
            System.out.println("          -> \"" + p.getNome() + "\" ja esta na fila! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Nao esta na fila. Pode entrar!");

        System.out.println("\n4. Adicionando na fila...");
        System.out.println("          -> Fila: enqueue(" + p.getNome() + ")");
        System.out.println("          -> stackEntrada.push(" + p.getNome() + ")");
        fila.enqueue(p);
        System.out.println("          -> Jogador empilhado em stackEntrada!");

        System.out.println("\n-> \"" + p.getNome() + "\" entrou na fila de espera!");
    }

    public void iniciarPartida() {
        System.out.println("\n1. Verificando se ha jogadores na fila...");

        limparFilaDeJogadoresRemovidos();

        if (fila.isEmpty()) {
            System.out.println("          -> Fila vazia! Operacao cancelada.");
            return;
        }

        String competidoresDaRodada = descreverFilaAtual();

        System.out.println("          -> Jogadores na fila: " + fila.size());
        System.out.println("          -> Iniciando sorteio da partida...");

        System.out.println("\n2. Sorteando 1 jogador dentre todos da fila...");
        Player vencedor = sortearJogadorDaFila();

        if (vencedor == null) {
            System.out.println("          -> Erro interno ao sortear jogador da fila.");
            return;
        }

        System.out.println("          -> Jogador sorteado para pontuar: \"" + vencedor.getNome() + "\"");

        System.out.println("\n3. Atualizando pontuacao do vencedor...");
        System.out.println("          -> AVL Tree: remove(" + vencedor.getNome() + ", pts=" + vencedor.getPontuacao() + ")");
        avl.remove(vencedor);
        System.out.println("          -> Pontuacao anterior: " + vencedor.getPontuacao() + " pts");
        vencedor.setPontuacao(vencedor.getPontuacao() + 10);
        System.out.println("          -> Nova pontuacao: " + vencedor.getPontuacao() + " pts (+10)");
        System.out.println("          -> AVL Tree: insert(" + vencedor.getNome() + ", pts=" + vencedor.getPontuacao() + ")");
        avl.insert(vencedor);
        System.out.println("          -> Arvore rebalanceada!");

        System.out.println("\n4. Reconstruindo a MaxHeap...");
        System.out.println("          -> Pontuacao alterada, heap precisa ser atualizada");
        reconstruirHeap();
        Player topAtual = heap.peek();
        if (topAtual == null) {
            System.out.println("          -> Heap reconstruida! Sem jogadores cadastrados no ranking.");
        } else {
            System.out.println("          -> Heap reconstruida! Top atual: \""
                    + topAtual.getNome() + "\" com " + topAtual.getPontuacao() + " pts");
        }

        System.out.println("\n-> Partida encerrada!");
        System.out.println("     Competindo nesta partida: " + competidoresDaRodada);
        System.out.println("     Jogador sorteado da fila: " + vencedor.getNome());
        System.out.println("     Nova pontuacao: " + vencedor.getPontuacao() + " pts");
        System.out.println("     Jogadores que seguem na fila para a proxima: " + fila.size());
    }

    // ================= RANKING =================

    public void mostrarTopJogador() {
        System.out.println("\n1. Verificando jogadores cadastrados...");

        if (jogadores.isEmpty()) {
            System.out.println("          -> Nenhum jogador cadastrado.");
            return;
        }

        int maiorPontuacao = obterMaiorPontuacao();

        if (maiorPontuacao == 0) {
            System.out.println("          -> Todos os jogadores estao com pontuacao zerada.");
        }

        System.out.println("\n2. Listando top jogador(es)...");
        System.out.println("          -> Maior pontuacao atual: " + maiorPontuacao);
        System.out.println("\n=== Top jogador(es) ===");

        int[] total = {0};
        jogadores.forEach(p -> {
            if (p.getPontuacao() == maiorPontuacao) {
                total[0]++;
                System.out.println(total[0] + "o - " + p);
            }
        });

        System.out.println("======================");
    }

    public void listarRanking() {
        System.out.println("\n1. Verificando se a AVL Tree esta vazia...");

        if (jogadores.isEmpty()) {
            System.out.println("          -> AVL vazia! Nenhum jogador cadastrado.");
            return;
        }

        if (avl.size() != jogadores.size()) {
            System.out.println("          -> Tamanho da AVL inconsistente com total de jogadores.");
            System.out.println("          -> Reconstruindo AVL para garantir todos no ranking...");
            reconstruirAvl();
        }

        System.out.println("          -> AVL nao esta vazia. Iniciando percurso...");
        System.out.println("\n2. Realizando percurso in-order na AVL Tree...");
        System.out.println("          -> In-order: visita esquerda -> raiz -> direita");
        System.out.println("          -> Resultado: jogadores em ordem crescente de pontuacao\n");

        if (todosComPontuacaoZero()) {
            System.out.println("Todos os jogadores estao com pontuacao zerada.");
        }

        System.out.println("=== Ranking ===");
        int[] pos = {1};
        avl.inOrder(p -> {
            System.out.println(pos[0]++ + "o - " + p);
        });
        System.out.println("===============");
    }

    public void atualizarPontuacao(int id, int pontos) {
        System.out.println("\n1. Buscando jogador na Hash Table...");
        System.out.println("          -> Hash Table: get(" + id + ")");

        Player p = jogadores.get(id);

        if (p == null) {
            System.out.println("          -> Jogador nao encontrado! Operacao cancelada.");
            return;
        }

        System.out.println("          -> Jogador \"" + p.getNome() + "\" encontrado!");

        System.out.println("\n2. Removendo da AVL Tree antes de alterar pontuacao...");
        System.out.println("          -> AVL Tree: remove(" + p.getNome() + ", pts=" + p.getPontuacao() + ")");
        System.out.println("          -> Necessario pois a AVL ordena por pontuacao!");
        avl.remove(p);
        System.out.println("          -> Removido e arvore rebalanceada!");

        System.out.println("\n3. Atualizando pontuacao...");
        System.out.println("          -> Pontuacao anterior: " + p.getPontuacao() + " pts");
        p.setPontuacao(pontos);
        System.out.println("          -> Nova pontuacao: " + pontos + " pts");

        System.out.println("\n4. Reinserindo na AVL Tree com nova pontuacao...");
        System.out.println("          -> AVL Tree: insert(" + p.getNome() + ", pts=" + pontos + ")");
        avl.insert(p);
        System.out.println("          -> Arvore rebalanceada!");

        System.out.println("\n5. Reconstruindo a MaxHeap...");
        System.out.println("          -> Pontuacao alterada, heap precisa ser atualizada");
        reconstruirHeap();
        System.out.println("          -> Heap reconstruida! Top atual: \""
                + heap.peek().getNome() + "\" com " + heap.peek().getPontuacao() + " pts");

        System.out.println("\n-> Pontuacao de \"" + p.getNome() + "\" atualizada para " + pontos + " pts!");
    }

    // ================= AUXILIAR =================

    private void reconstruirHeap() {
        heap = new MaxHeap<>(100);
        jogadores.forEach(p -> heap.insert(p));
    }

    private void reconstruirAvl() {
        avl = new AVLTree<>();
        jogadores.forEach(avl::insert);
    }

    private boolean todosComPontuacaoZero() {
        if (jogadores.isEmpty()) return false;

        boolean[] todosZero = {true};
        jogadores.forEach(p -> {
            if (p.getPontuacao() != 0) {
                todosZero[0] = false;
            }
        });

        return todosZero[0];
    }

    private int obterMaiorPontuacao() {
        Player top = heap.peek();
        if (top != null) return top.getPontuacao();

        int[] maior = {Integer.MIN_VALUE};
        jogadores.forEach(p -> {
            if (p.getPontuacao() > maior[0]) {
                maior[0] = p.getPontuacao();
            }
        });

        return maior[0] == Integer.MIN_VALUE ? 0 : maior[0];
    }

    private Player sortearJogadorDaFila() {
        int total = fila.size();
        if (total == 0) return null;

        Player[] participantes = new Player[total];
        for (int i = 0; i < total; i++) {
            Player atual = fila.dequeue();
            if (atual == null) {
                for (int j = 0; j < i; j++) {
                    fila.enqueue(participantes[j]);
                }
                return null;
            }
            participantes[i] = atual;
        }

        int sorteado = (int) (Math.random() * total);
        Player vencedor = participantes[sorteado];

        for (int i = 0; i < total; i++) {
            fila.enqueue(participantes[i]);
        }

        return vencedor;
    }

    private String descreverFilaAtual() {
        int total = fila.size();
        if (total == 0) return "ninguem";

        Player[] participantes = new Player[total];
        StringBuilder nomes = new StringBuilder();

        for (int i = 0; i < total; i++) {
            Player atual = fila.dequeue();
            if (atual == null) {
                for (int j = 0; j < i; j++) {
                    fila.enqueue(participantes[j]);
                }
                return "indisponivel";
            }
            participantes[i] = atual;
            if (i > 0) {
                nomes.append(" x ");
            }
            nomes.append(atual.getNome());
        }

        for (int i = 0; i < total; i++) {
            fila.enqueue(participantes[i]);
        }

        return nomes.toString();
    }

    private void removerDaFilaPorId(int id) {
        int total = fila.size();

        for (int i = 0; i < total; i++) {
            Player atual = fila.dequeue();
            if (atual != null && atual.getId() != id) {
                fila.enqueue(atual);
            }
        }
    }

    private void limparFilaDeJogadoresRemovidos() {
        int total = fila.size();

        for (int i = 0; i < total; i++) {
            Player atual = fila.dequeue();
            if (atual != null && jogadores.containsKey(atual.getId())) {
                fila.enqueue(atual);
            }
        }
    }

}