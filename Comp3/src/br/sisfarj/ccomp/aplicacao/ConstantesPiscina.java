package br.sisfarj.ccomp.aplicacao;

public enum ConstantesPiscina {
	
	TRUE('T'), FALSE('F');
	
	private char valor;
	
	ConstantesPiscina(char valor) {
			
		this.valor = valor;
	}
	
	public char getValor() {
		return this.valor;
	}

}


