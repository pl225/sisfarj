package br.sisfarj.ccomp.gateways.exceptions;

public class AtletaNaoEncontradoException  extends Exception {
	public AtletaNaoEncontradoException(String matricula) {
		super("O atleta de matricula " + matricula + " não existe.");
	}
}
