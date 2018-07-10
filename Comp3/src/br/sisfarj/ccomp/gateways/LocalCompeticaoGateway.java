package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.PessoaNaoEncontradaException;

public class LocalCompeticaoGateway {

	public ResultSet listarTudo() throws SQLException, LocalNaoEncontradoException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT * FROM comp3.LocalCompeticao"));
		if (!rs.next()) throw new LocalNaoEncontradoException("Nenhum local de competição encontrado");
		return rs;
	}

}
