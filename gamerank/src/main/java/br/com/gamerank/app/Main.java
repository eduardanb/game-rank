package br.com.gamerank.app;

import br.com.gamerank.service.RankingService;
import br.com.gamerank.util.ConsoleUtils;

public class Main {

    private static void mostrarBanner() {
        System.out.println("--------------------------------------------------------------------");
        System.out.println(" ██████╗  █████╗ ███╗   ███╗███████╗    ██████╗  █████╗ ███╗   ██╗██╗  ██╗");
        System.out.println("██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝");
        System.out.println("██║  ███╗███████║██╔████╔██║█████╗      ██████╔╝███████║██╔██╗ ██║█████╔╝ ");
        System.out.println("██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██╔══██╗██╔══██║██║╚██╗██║██╔═██╗ ");
        System.out.println("╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ██║  ██║██║  ██║██║ ╚████║██║  ██╗");
        System.out.println(" ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝    ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝");
        System.out.println("CARA OU COROA-------------------------------------------------------");
    }

    public static void main(String[] args) {

        RankingService service = new RankingService();
        int opcao = -1;

        while (opcao != 0) {

            System.out.println();
            mostrarBanner();
            System.out.println("1 - Cadastrar jogador");
            System.out.println("2 - Buscar jogador");
            System.out.println("3 - Listar ranking");
            System.out.println("4 - Mostrar top jogador(es)");
            System.out.println("5 - Entrar na fila");
            System.out.println("6 - Iniciar partida");
            System.out.println("7 - Atualizar pontuação");
            System.out.println("8 - Remover jogador");
            System.out.println("0 - Sair");

            opcao = ConsoleUtils.lerInt("Escolha: ");

            switch (opcao) {

                case 1: {
                    int id = ConsoleUtils.lerInt("ID: ");
                    String nome = ConsoleUtils.lerString("Nome: ");
                    service.cadastrarJogador(id, nome);
                    break;
                }

                case 2: {
                    int id = ConsoleUtils.lerInt("ID do jogador: ");
                    service.buscarJogador(id);
                    break;
                }

                case 3:
                    service.listarRanking();
                    break;

                case 4:
                    service.mostrarTopJogador();
                    break;

                case 5: {
                    int id = ConsoleUtils.lerInt("ID do jogador: ");
                    service.entrarFila(id);
                    break;
                }

                case 6:
                    service.iniciarPartida();
                    break;

                case 7: {
                    int id = ConsoleUtils.lerInt("ID do jogador: ");
                    int pontos = ConsoleUtils.lerInt("Nova pontuação: ");
                    service.atualizarPontuacao(id, pontos);
                    break;
                }

                case 8: {
                    int id = ConsoleUtils.lerInt("ID do jogador: ");
                    service.removerJogador(id);
                    break;
                }

                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        ConsoleUtils.fecharScanner();
    }
}