package br.sisfarj.ccomp.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;

public class CompeticaoProvaMT {

	private ResultSet rs;

	public CompeticaoProvaMT(ResultSet rs) {
		this.rs = rs;
	}

	public ResultSet getProvasDaCompeticao(String dataCompeticao, String endereco) throws SQLException, CompeticaoNaoEncontradaException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT * FROM comp3.competicaoProva "
				+ "WHERE endereco = '" + endereco + "' AND dataCompeticao='" + dataCompeticao + "'"));
		
		if (!rs.next()) throw new CompeticaoNaoEncontradaException();
		rs.beforeFirst();
		return rs;
	}

}
