package br.com.gamerank.util;

import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner sc = new Scanner(System.in);

    public static int lerInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Digite um número válido: ");
        }
        int valor = sc.nextInt();
        sc.nextLine(); // limpar buffer
        return valor;
    }

    public static String lerString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    public static void fecharScanner() {
        sc.close();
    }
}