package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.bd.UpdatingQuery;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;

public class CompeticaoGateway {

	public ResultSet listarTodas() throws SQLException, NaoHaCompeticaoException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT nome, dataCompeticao, endereco FROM comp3.competicao"));
		
		if (!rs.next()) throw new NaoHaCompeticaoException();
		rs.beforeFirst();
		return rs;
	}

	public ResultSet buscar(String dataCompeticao, String endereco) throws SQLException, CompeticaoNaoEncontradaException, ParseException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT * FROM comp3.competicao "
				+ "WHERE endereco = '" + endereco + "' AND dataCompeticao='" + dataCompeticao + "'"));
		
		if (!rs.next()) throw new CompeticaoNaoEncontradaException();
		rs.beforeFirst();
		return rs;
	}

	public void alterar(String dataCompeticao, String localCompeticao, 
			String dataCompeticaoAntiga, String endereco) throws ParseException, SQLException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp timestampCompeticao = new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime());		
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("UPDATE comp3.competicao SET "
				+ "dataCompeticao = '" + timestampCompeticao + "', endereco = '" + localCompeticao +	"' "
				+ "WHERE dataCompeticao = '" + dataCompeticaoAntiga + "' AND "
						+ "endereco = '" + endereco + "'"));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}

}
