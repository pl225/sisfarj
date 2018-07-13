package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.gateways.exceptions.AtletaNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.ProvaSemAtletaException;

public class AtletaProvaGateway {

	public ResultSet buscarAtletasProva(String nome, String classe, String categoria, String dataCompeticao, String endereco) throws SQLException, ParseException, ProvaSemAtletaException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t1 = new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime());
		
		System.out.println("SELECT a.matriculaAtleta, a.nome, ap.tempo FROM comp3.atletaprova ap "
						+ "INNER JOIN comp3.atleta a ON ap.matriculaAtleta = a.matriculaatleta "
						+ "WHERE ap.nomeProva = '" + nome + "' AND ap.classe = '" + classe + "'AND ap.categoria = '" + categoria
						+ "'AND ap.dataCompeticao = '" + t1 + "'AND ap.endereco = '" + endereco + "'");
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT a.matriculaAtleta, a.nome, ap.tempo FROM comp3.atletaprova ap "
						+ "INNER JOIN comp3.atleta a ON ap.matriculaAtleta = a.matriculaatleta "
						+ "WHERE ap.nomeProva = '" + nome + "' AND ap.classe = '" + classe + "'AND ap.categoria = '" + categoria
						+ "'AND ap.dataCompeticao = '" + t1 + "'AND ap.endereco = '" + endereco + "'"));
		System.out.println();
		if (!rs.next()) throw new ProvaSemAtletaException("Sem atletas inscritos na prova!");
		rs.beforeFirst();
		return rs;
		
		
		
	}

}
