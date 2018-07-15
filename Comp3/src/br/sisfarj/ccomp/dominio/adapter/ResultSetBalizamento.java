package br.sisfarj.ccomp.dominio.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetBalizamento extends ResultSetAdapter {

	private int balizamento;
	private int serie = 1;

	public ResultSetBalizamento(ResultSet rs) {
		super(rs);
		// TODO Auto-generated constructor stub
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

	@Override
	public boolean next() throws SQLException {
		this.balizamento++; 
		if (this.balizamento>8) {
			this.serie++;
			return false; 
		}
		return super.next();
	}

	public boolean balizamento() throws SQLException {
		this.balizamento = 0;
		return (!this.rs.isAfterLast());
	
	}

	public int getSerie() {
		return serie;
	}

}
