package br.sisfarj.ccomp.dominio.exceptions;

public class NaoHaProvaException extends Exception {
		public NaoHaProvaException() {
			super("N�o h� provas registradas.");
		}
}
