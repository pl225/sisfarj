package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.bd.CreatingQuery;
import br.sisfarj.ccomp.bd.UpdatingQuery;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.LocalJaExisteException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

public class AssociacaoGateway {

	public void inserir(String numeroOficio, String dataOficio, String nome, 
			String sigla, String endereco, String telefone, 
			String numeroPagamento, String senha) throws SQLException, ParseException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t = new Timestamp(simpleDateFormat.parse(dataOficio).getTime());
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("INSERT INTO comp3.associacao "
				+ "(nome, telefone, sigla, endereco, numeroPagamento, numeroOficio, dataOficio, temAcesso, senha) "
				+ "VALUES ('" + nome + "', '" + telefone + "', '" + sigla + "', '"
						+ endereco + "', " + numeroPagamento + ", " + numeroOficio + ", '" + t + "', 'T', '" + senha + "')"));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}

	public ResultSet listarTodas() throws SQLException {
		
		BDConnection bdConnection = new BDConnection(false);
				
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT * FROM comp3.associacao"));
		
		return rs;
	}

	public ResultSet buscar(String matriculaAssociacao) throws SQLException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT "
						+ "matriculaAssociacao, "
						+ "nome, "
						+ "sigla, "
						+ "numeroOficio, "
						+ "dataOficio,"
						+ "temAcesso "
						+ "FROM comp3.associacao "
						+ "WHERE matriculaAssociacao = " + matriculaAssociacao)
		);
		
		return rs;
		
	}
	
	public ResultSet listarPorNome() throws SQLException, NaoHaAssociacaoException {
		
		BDConnection bdConnection = new BDConnection(false);
				
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT matriculaAssociacao, nome FROM comp3.associacao ORDER BY nome"));
		
		if (!rs.next()) throw new NaoHaAssociacaoException();
		rs.beforeFirst();
		return rs;
	}
	
	public void atualizar(String matriculaAssociacao, String numeroOficio, String dataOficio, 
			String nome, String sigla, char temAcesso) throws SQLException, ParseException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t = new Timestamp(simpleDateFormat.parse(dataOficio).getTime());
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("UPDATE comp3.associacao SET "
				+ "numeroOficio = " + numeroOficio + ", nome = '" + nome +	"', "
				+ "dataOficio = '" + t + "', sigla = '" + sigla + "', "
				+ "temAcesso = '" + temAcesso + "' "
				+ "WHERE matriculaAssociacao = " + matriculaAssociacao));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}

	public ResultSet buscar(int matricula, String senha) throws SQLException, AssociacaoNaoEncontradaException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT "
						+ "matriculaAssociacao, temAcesso  "
						+ "FROM comp3.associacao "
						+ "WHERE matriculaAssociacao = " + matricula + " AND "
								+ "senha = '" + senha + "' "
								+ "AND temAcesso = 'T'")
		);
		
		if (!rs.next()) throw new AssociacaoNaoEncontradaException(String.valueOf(matricula));
		rs.beforeFirst();
		return rs;
	}

	public ResultSet buscar() throws SQLException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs =  bdConnection.execute(new CreatingQuery(
			"SELECT * FROM comp3.associacao WHERE 1 = 2"
		));
		rs.moveToInsertRow();
		return rs;
	}

	public void inserir(ResultSet rs) throws SQLException {
		rs.insertRow();
		rs.close();
	}

	public void atualizar(ResultSet rs) throws SQLException {
		rs.updateRow();
		rs.close();
	}

}
