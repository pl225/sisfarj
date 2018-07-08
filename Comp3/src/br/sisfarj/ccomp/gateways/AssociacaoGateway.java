package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.bd.UpdatingQuery;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;

public class AssociacaoGateway {

	public int inserir(String numeroOficio, String dataOficio, String nome, String sigla, String endereco,
			String telefone, String numeroPagamento) throws SQLException, ParseException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t = new Timestamp(simpleDateFormat.parse(dataOficio).getTime());
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("INSERT INTO comp3.associacao "
				+ "VALUES (" + numeroOficio + ", '" + nome + "', '" + telefone + "', '" + sigla + "', '"
						+ endereco + "', " + numeroPagamento + ", " + numeroOficio + ", '" + t + "')"));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		return Integer.parseInt(numeroOficio);
		
	}

	public ResultSet listarTodas() throws SQLException, NaoHaAssociacaoException {
		
		BDConnection bdConnection = new BDConnection(false);
				
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT matriculaAssociacao, nome FROM comp3.associacao"));
		
		if (!rs.next()) throw new NaoHaAssociacaoException();
		rs.beforeFirst();
		return rs;
	}

	public ResultSet buscar(String matriculaAssociacao) throws SQLException, AssociacaoNaoEncontradaException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT "
						+ "matriculaAssociacao, "
						+ "nome, "
						+ "sigla, "
						+ "numeroOficio, "
						+ "dataOficio "
						+ "FROM comp3.associacao "
						+ "WHERE matriculaAssociacao = " + matriculaAssociacao)
		);
		
		if (!rs.next()) throw new AssociacaoNaoEncontradaException(matriculaAssociacao);
		rs.beforeFirst();
		return rs;
		
	}

	public void atualizar(String matriculaAssociacao, String numeroOficio, String dataOficio, 
			String nome, String sigla) throws SQLException, ParseException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t = new Timestamp(simpleDateFormat.parse(dataOficio).getTime());
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("UPDATE comp3.associacao SET "
				+ "numeroOficio = " + numeroOficio + ", nome = '" + nome +	"', "
				+ "dataOficio = '" + t + "', sigla = '" + sigla + "' "
				+ "WHERE matriculaAssociacao = " + matriculaAssociacao));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}

}
