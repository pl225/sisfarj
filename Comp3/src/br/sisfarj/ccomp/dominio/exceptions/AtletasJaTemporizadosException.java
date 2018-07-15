package br.sisfarj.ccomp.dominio.exceptions;

public class AtletasJaTemporizadosException extends Exception {
	public AtletasJaTemporizadosException() {
		super("Atletas da prova já temporizados ou não existem atletas nessa prova.");
	}
}
