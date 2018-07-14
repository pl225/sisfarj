package br.sisfarj.ccomp.dominio.exceptions;

public class CompeticaoJaExisteException extends Exception {
	public CompeticaoJaExisteException() {
		super("Competição já existe.");
	}
}
