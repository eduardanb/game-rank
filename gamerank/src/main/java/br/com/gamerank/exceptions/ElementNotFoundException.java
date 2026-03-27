package br.com.gamerank.exceptions;

public class ElementNotFoundException extends Exception {
    public ElementNotFoundException() {
        super("Elemento não encontrado na estrutura!");
    }
    public ElementNotFoundException(String msg) {
        super(msg);
    }
}