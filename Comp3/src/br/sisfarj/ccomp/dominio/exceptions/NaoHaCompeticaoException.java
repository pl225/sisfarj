package br.sisfarj.ccomp.dominio.exceptions;

public class NaoHaCompeticaoException extends Exception {
	public NaoHaCompeticaoException() {
		super("Não há competições registradas.");
	}
}
