package br.sisfarj.ccomp.dominio.exceptions;

public class AtletaJaInscritoProvaException extends Exception {
	public AtletaJaInscritoProvaException() {
		super("Atleta j� inscrito na prova.");
	}
}
