package br.sisfarj.ccomp.dominio.adapter;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ResultSetProva extends ResultSetAdapter{

	public ResultSetProva(ResultSet rs) {
		super(rs);
	}
	
	@Override
	protected ArrayList<String> colunasPermitidas() {
		ArrayList<String> colunas =  new ArrayList<String>();
		
		colunas.add("nomeProva");
		colunas.add("categoria");
		colunas.add("classe");
		colunas.add("tipoPiscina");
		
		return colunas;
	}
}
