package br.sisfarj.ccomp.dominio.exceptions;

public class NaoHaPontuacaoException extends Exception {
	public NaoHaPontuacaoException() {
		super("N�o h� nenhuma pontua��o a ser mostrada.");
	}
}
