package br.sisfarj.ccomp.gateways.exceptions;

public class AssociacaoNaoEncontradaException extends Exception {
	public AssociacaoNaoEncontradaException(String matricula) {
		super("A associação de matrícula " + matricula + " não existe.");
	}
}
