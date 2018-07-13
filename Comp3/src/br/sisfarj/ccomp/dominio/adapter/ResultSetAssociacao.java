package br.sisfarj.ccomp.dominio.adapter;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ResultSetAssociacao extends ResultSetAdapter {

	public ResultSetAssociacao(ResultSet rs) {
		super(rs);
	}

	@Override
	protected ArrayList<String> colunasPermitidas() {
		ArrayList<String> colunas =  new ArrayList<String>();
		
		colunas.add("matriculaAssociacao");
		colunas.add("nome");
		colunas.add("numeroOficio");
		colunas.add("dataOficio");
		colunas.add("sigla");
		colunas.add("temAcesso");
		
		return colunas;
	}

}
