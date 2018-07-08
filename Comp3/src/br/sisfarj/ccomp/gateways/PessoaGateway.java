package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.gateways.exceptions.PessoaNaoEncontradaException;

public class PessoaGateway {

	public ResultSet buscar(int matricula, String senha) throws SQLException, PessoaNaoEncontradaException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT * "
				+ "FROM comp3.Pessoa p "
				+ "WHERE p.matricula = " + matricula + " AND "
						+ "p.senha = '" + senha + "'"));
		if (!rs.next()) throw new PessoaNaoEncontradaException("Matrícula e/ou senha inválidos!");
		return rs;
	}

}
