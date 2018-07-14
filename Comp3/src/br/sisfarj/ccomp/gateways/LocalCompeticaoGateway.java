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
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.LocalJaExisteException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.PessoaNaoEncontradaException;
//
public class LocalCompeticaoGateway {
	
	public void inserir(String nome, String endereco, char piscina25, char piscina50) throws SQLException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("INSERT INTO comp3.localcompeticao (nome, endereco, piscina25, piscina50)"
				+ " VALUES ('" + nome + "', '" + endereco + "', '" + piscina25 + "', '"+ piscina50 + "')"));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
		
	}

	public ResultSet listarTudo() throws SQLException, LocalNaoEncontradoException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT * FROM comp3.LocalCompeticao"));
		return rs;
	}
	
	public ResultSet buscar(String endereco) throws SQLException, LocalNaoEncontradoException {
		
		BDConnection bdConnection = new BDConnection(false);
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT "
						+ "*"
						+ " FROM comp3.LocalCompeticao "
						+ "WHERE endereco = '" + endereco + "'")
		);
		
		if (!rs.next()) throw new LocalNaoEncontradoException(endereco);
		rs.beforeFirst();
		return rs;
		
	}
	
	public void atualizar(String endereco, String nome, String novoEndereco, char piscina25, char piscina50) throws SQLException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		System.out.println(new UpdatingQuery("UPDATE comp3.LocalCompeticao SET "
				+ "endereco = '" + novoEndereco + "', nome = '" + nome +	"', "
				+ "piscina25 = '" + piscina25 + "', piscina50 = '" + piscina50 + "' "
				+ "WHERE endereco = '" + endereco +"'"));
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("UPDATE comp3.LocalCompeticao SET "
				+ "endereco = '" + novoEndereco + "', nome = '" + nome +	"', "
				+ "piscina25 = '" + piscina25 + "', piscina50 = '" + piscina50 + "' "
				+ "WHERE endereco = '" + endereco +"'"));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}

	public void inserir(ResultSet rs) throws SQLException {
		rs.insertRow();
		rs.close();
	}

	public ResultSet criar(String endereco) throws SQLException, LocalJaExisteException {
		BDConnection bdConnection = new BDConnection(false);
		
		try {
			this.buscar(endereco);
			throw new LocalJaExisteException();
		} catch (LocalNaoEncontradoException e) {
			return bdConnection.execute(new CreatingQuery(
				"SELECT * FROM comp3.localcompeticao WHERE 1 = 2"
			));
		}
	}

}
