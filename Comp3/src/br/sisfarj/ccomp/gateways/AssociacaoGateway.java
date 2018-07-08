package br.sisfarj.ccomp.gateways;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.UpdatingQuery;

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

}
