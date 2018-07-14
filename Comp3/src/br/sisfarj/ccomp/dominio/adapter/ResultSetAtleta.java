package br.sisfarj.ccomp.dominio.adapter;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ResultSetAtleta extends ResultSetAdapter {

	public ResultSetAtleta(ResultSet rs) {
		super(rs);
	}

	@Override
	protected ArrayList<String> colunasPermitidas() {
		ArrayList<String> colunas =  new ArrayList<String>();
		
		colunas.add("matriculaAtleta");
		colunas.add("matriculaAssociacao");
		colunas.add("nome");
		colunas.add("numeroOficio");
		colunas.add("dataOficio");
		colunas.add("dataEntrada");
		
		return colunas;
	}

}
