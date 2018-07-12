package br.sisfarj.ccomp.gateways;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.UpdatingQuery;

public class ProvaGateway {

	public void inserir(String dataCompeticao, String endereco, String nomeProva, String classe, String categoria) throws SQLException, ParseException {
		
		BDConnection bdConnection = new BDConnection(false);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		Timestamp timestampCompeticao = new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime());
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("INSERT INTO comp3.prova "
				+ "VALUES ('" + nomeProva + "', '" + classe + "', "
				+ "'" + categoria + "', '" + timestampCompeticao + 
				"', '" + endereco + "')"));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}

}
