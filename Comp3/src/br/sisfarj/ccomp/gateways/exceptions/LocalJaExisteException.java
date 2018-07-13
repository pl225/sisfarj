package br.sisfarj.ccomp.gateways.exceptions;

public class LocalJaExisteException extends Exception {
	public LocalJaExisteException() {
		super("Local já existente.");
	}
}
