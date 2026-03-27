package br.com.gamerank.model;

public class Player implements Comparable<Player> {

    private int id;
    private String nome;
    private int pontuacao;

    public Player(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.pontuacao = 0;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getPontuacao() { return pontuacao; }
    public void setPontuacao(int pontuacao) { this.pontuacao = pontuacao; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Pontos: " + pontuacao;
    }

    @Override
    public int compareTo(Player outro) {
        return Integer.compare(this.pontuacao, outro.pontuacao);
    }
}