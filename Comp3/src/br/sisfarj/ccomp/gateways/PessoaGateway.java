package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.bd.UpdatingQuery;
import br.sisfarj.ccomp.dominio.PessoaMT.TipoPessoa;
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

	public void inserir(int matriculaGerada, String senha, TipoPessoa tipoPessoa) throws SQLException {
		BDConnection bdConnection = new BDConnection(false);
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("INSERT INTO comp3.pessoa "
				+ "VALUES (" + matriculaGerada + ", '" + senha + "', '" + tipoPessoa.toString() + "')"));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}

}
