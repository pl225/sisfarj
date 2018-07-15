package br.sisfarj.ccomp.dominio.adapter;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ResultSetCompeticao extends ResultSetAdapter {

	public ResultSetCompeticao(ResultSet rs) {
		super(rs);
	}

	@Override
	protected ArrayList<String> colunasPermitidas() {
		ArrayList<String> colunas = new ArrayList<String>();
		
		colunas.add("endereco");
		colunas.add("dataCompeticao");
		colunas.add("classe");
		colunas.add("categoria");
		colunas.add("nome");
		colunas.add("tipoPiscina");
		
		return colunas;
	}

}
