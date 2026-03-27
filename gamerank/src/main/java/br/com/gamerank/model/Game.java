package br.com.gamerank.model;

public class Game {

    private int id;
    private Player jogador1;
    private Player jogador2;
    private Player vencedor;

    public Game(int id, Player jogador1, Player jogador2) {
        this.id = id;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
    }

    public int getId() {
        return id;
    }

    public Player getJogador1() {
        return jogador1;
    }

    public Player getJogador2() {
        return jogador2;
    }

    public Player getVencedor() {
        return vencedor;
    }

    public void setVencedor(Player vencedor) {
        this.vencedor = vencedor;
    }

    @Override
    public String toString() {
        return "Partida " + id + " | "
                + jogador1.getNome() + " vs "
                + jogador2.getNome()
                + " | Vencedor: "
                + (vencedor != null ? vencedor.getNome() : "Não definido");
    }
}