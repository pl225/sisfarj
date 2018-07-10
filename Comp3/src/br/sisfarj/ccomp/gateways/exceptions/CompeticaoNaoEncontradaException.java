package br.sisfarj.ccomp.gateways.exceptions;

public class CompeticaoNaoEncontradaException extends Exception {
	public CompeticaoNaoEncontradaException() {
		super("A competição requisitada não existe.");
	}
}
