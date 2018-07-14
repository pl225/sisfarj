package br.sisfarj.ccomp.dominio.exceptions;

public class NaoHaProvaException extends Exception {
		public NaoHaProvaException() {
			super("Não há provas registradas.");
		}
}
