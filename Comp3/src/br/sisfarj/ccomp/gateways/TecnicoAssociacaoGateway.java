package br.sisfarj.ccomp.gateways;

import java.sql.SQLException;

import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.UpdatingQuery;
import br.sisfarj.ccomp.dominio.PessoaMT.TipoPessoa;

public class TecnicoAssociacaoGateway {

	public void inserir(int matriculaGerada) throws SQLException {
		PessoaGateway pessoaGateway = new PessoaGateway();
		pessoaGateway.inserir(matriculaGerada, String.valueOf(matriculaGerada), TipoPessoa.TECNICO_ASSOSSIACAO);
		
		BDConnection bdConnection = new BDConnection(false);
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("INSERT INTO comp3.tecnicoassociacao "
				+ "VALUES (" + matriculaGerada + ", " + matriculaGerada +  ")"));
		
		if (linhasAfetadas <= 0) throw new SQLException();
	}

}
