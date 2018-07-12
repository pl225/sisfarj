package br.sisfarj.ccomp.dominio.exceptions;

public class NaoHaAtletaException extends Exception{
	public NaoHaAtletaException() {
		super("Não há atletas registrados.");
	}
}



