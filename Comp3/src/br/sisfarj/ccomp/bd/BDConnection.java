package br.sisfarj.ccomp.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDConnection {
	private static final String URL = "jdbc:derby:/home/matheus/sisfarj;create=true";
	private static final String USER = "pl225";
	private static final String PASSWORD = "Pl2252122*";
	
	private static Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private boolean transacao;
	
	public BDConnection (boolean transacao) throws SQLException {
		if (conn == null) {
			DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		}
		this.transacao = transacao;
		if (this.transacao) conn.setAutoCommit(false);
	}
	
	public void desconectar () throws SQLException{
		try {
			if (this.stmt != null) this.stmt.close();
			if (this.rs != null) this.rs.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				DriverManager.getConnection(
					    URL + ";user=" + USER + ";password=" + PASSWORD + ";shutdown=true");
			} catch (SQLException e) {}
		}
	}
	
	public int execute (UpdatingQuery uq) throws SQLException {
		if (this.stmt != null) this.stmt.close();
		this.stmt = conn.createStatement();
		return this.stmt.executeUpdate(uq.toString());
	}
	
	public int execute (UpdatingQuery uq, String coluna) throws SQLException {
		if (this.stmt != null) this.stmt.close();
		this.stmt = conn.createStatement();
		int afetadas = this.stmt.executeUpdate(uq.toString(), new String [] {coluna});
		if (afetadas <= 0) throw new SQLException();
		
		try (ResultSet ids = this.stmt.getGeneratedKeys()) {
			if (ids.next()) return ids.getInt(1);
			else throw new SQLException();
		}
		
	}
	
	public ResultSet execute (ConsultingQuery cq) throws SQLException {
		if (this.rs != null) this.rs.close();
		if (this.stmt != null) this.stmt.close();
		this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		this.rs = this.stmt.executeQuery(cq.toString());
		return this.rs;
	}
	
	public boolean proximo () throws SQLException {
		return this.rs != null && this.rs.next();
	}
	
	public <T> T getDado (String nome, Class<T> tipo) throws SQLException {
		return tipo.cast(this.rs.getObject(nome));
	}
	
	public <T> T getDado (int coluna, Class<T> tipo) throws SQLException {
		return tipo.cast(this.rs.getObject(coluna));
	}
	
	public void commit () throws SQLException {
		if (this.transacao) conn.commit();
	}
	
	public void rollback () throws SQLException {
		if (this.transacao) conn.rollback();
	}
	
	public void algo () {
		
	}
}
