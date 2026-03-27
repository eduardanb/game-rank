package br.com.gamerank.model;

public class Score {

    private Player jogador;
    private int pontos;

    public Score(Player jogador, int pontos) {
        this.jogador = jogador;
        this.pontos = pontos;
    }

    public Player getJogador() {
        return jogador;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {
        return jogador.getNome() + " - " + pontos + " pontos";
    }
}