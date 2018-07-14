package br.sisfarj.ccomp.dominio.exceptions;

public class NaoHaPontuacaoException extends Exception {
	public NaoHaPontuacaoException() {
		super("Não há nenhuma pontuação a ser mostrada.");
	}
}
