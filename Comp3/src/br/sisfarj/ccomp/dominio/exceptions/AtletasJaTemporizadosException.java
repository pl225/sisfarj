package br.sisfarj.ccomp.dominio.exceptions;

public class AtletasJaTemporizadosException extends Exception {
	public AtletasJaTemporizadosException() {
		super("Atletas da prova j� temporizados ou n�o existem atletas nessa prova.");
	}
}
