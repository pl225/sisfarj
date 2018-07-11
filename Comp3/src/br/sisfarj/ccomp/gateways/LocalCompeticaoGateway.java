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
		if (!rs.next()) throw new LocalNaoEncontradoException("Nenhum local de competição encontrado");
		rs.beforeFirst();
		return rs;
	}

}
