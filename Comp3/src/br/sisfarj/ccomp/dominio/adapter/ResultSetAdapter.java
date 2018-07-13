package br.sisfarj.ccomp.dominio.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public abstract class ResultSetAdapter {
	
	private ResultSet rs;
	private ArrayList<String> colunasPermitidas;

	public ResultSetAdapter (ResultSet rs) {
		this.rs = rs;
		colunasPermitidas = colunasPermitidas();
	}
	
	protected abstract ArrayList<String> colunasPermitidas();
	
	public int getInt (String coluna) throws SQLException {
		if (colunasPermitidas.contains(coluna)) return this.rs.getInt(coluna);
		else throw new RuntimeException("Coluna não permitida.");
	}
	
	public String getString (String coluna) throws SQLException {
		if (colunasPermitidas.contains(coluna)) return this.rs.getString(coluna);
		else throw new RuntimeException("Coluna não permitida.");
	}
	
	public Timestamp getTimestamp (String coluna) throws SQLException {
		if (colunasPermitidas.contains(coluna)) return this.rs.getTimestamp(coluna);
		else throw new RuntimeException("Coluna não permitida.");
	}
	
	public boolean next() throws SQLException {
		return this.rs.next();
	}

}
