package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.CreatingQuery;
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

	
	public ResultSet buscar() throws SQLException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs =  bdConnection.execute(new CreatingQuery(
			"SELECT * FROM comp3.prova WHERE 1 = 2"
		));
		rs.moveToInsertRow();
		return rs;
	}


	public void inserir(ResultSet rs) throws SQLException {
		rs.insertRow();
		rs.close();
	}

}
