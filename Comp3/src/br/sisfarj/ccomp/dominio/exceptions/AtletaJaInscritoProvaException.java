package br.sisfarj.ccomp.dominio.exceptions;

public class AtletaJaInscritoProvaException extends Exception {
	public AtletaJaInscritoProvaException() {
		super("Atleta já inscrito na prova.");
	}
}
