package br.sisfarj.ccomp.dominio.adapter;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ResultSetLocalCompeticao extends ResultSetAdapter {

	public ResultSetLocalCompeticao(ResultSet rs) {
		super(rs);
	}

	@Override
	protected ArrayList<String> colunasPermitidas() {
		ArrayList<String> colunas = new ArrayList<>();

		colunas.add("nome");
		colunas.add("endereco");
		colunas.add("piscina25");
		colunas.add("piscina50");
		
		return colunas;
	}

}
