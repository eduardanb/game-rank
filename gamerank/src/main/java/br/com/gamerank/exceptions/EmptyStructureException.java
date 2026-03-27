package br.com.gamerank.exceptions;

public class EmptyStructureException extends Exception {
    public EmptyStructureException() {
        super("A estrutura está vazia!");
    }
    public EmptyStructureException(String msg) {
        super(msg);
    }
}