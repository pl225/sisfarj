package br.sisfarj.ccomp.dominio.exceptions;

public class NaoHaAssociacaoException extends Exception {
	public NaoHaAssociacaoException() {
		super("Não há associações registradas.");
	}
}
