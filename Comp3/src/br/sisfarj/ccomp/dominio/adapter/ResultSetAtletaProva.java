package br.sisfarj.ccomp.dominio.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetAtletaProva extends ResultSetAdapter{

	public ResultSetAtletaProva(ResultSet rs) {
		super(rs);
	}
	
	@Override
	protected ArrayList<String> colunasPermitidas() {
		ArrayList<String> colunas =  new ArrayList<String>();
		
		colunas.add("matriculaAtleta");
		colunas.add("nome");
		colunas.add("tempo");
		colunas.add("pontuacao");
		colunas.add("sigla");
		
		return colunas;
	}

}