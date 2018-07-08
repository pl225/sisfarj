package br.sisfarj.ccomp.bd;

public abstract class SQLQuery {
	private String query;
	
	public SQLQuery (String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return this.query;
	}
}
