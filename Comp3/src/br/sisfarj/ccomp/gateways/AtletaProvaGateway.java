package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.gateways.exceptions.ProvaSemAtletaException;

public class AtletaProvaGateway {

	public ResultSet buscarAtletasProva(String nome, String classe, String categoria, String dataCompeticao, String endereco) throws SQLException, ParseException, ProvaSemAtletaException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t1 = new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime());
		
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

	public ResultSet buscarAtletasCompeticao(String dataCompeticao, String localCompeticao) throws SQLException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT * FROM comp3.atletaprova "
						+ "WHERE dataCompeticao = '" + dataCompeticao + "' AND endereco = '" + localCompeticao + "'"));
		return rs;
	}

	public void alterarLinha(ResultSet rs) throws SQLException {
		rs.updateRow();
	}

	public void terminarAlterarLinhas(ResultSet rs) throws SQLException {
		rs.close();
	}

	public ResultSet calcularPontuacao(String dataCompeticao, String endereco) throws SQLException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT  SUM(pontuacao) as pontuacao, " + 
						"ass.nome, " + 
						"sigla, " + 
						"ass.matriculaAssociacao " + 
						"FROM comp3.atletaprova ap " + 
						"INNER JOIN comp3.atleta a ON a.matriculaAtleta = ap.matriculaAtleta " + 
						"INNER JOIN comp3.associacao ass ON ass.matriculaAssociacao = a.matriculaAssociacao " + 
						"WHERE ap.dataCompeticao = '" + dataCompeticao + "' AND ap.endereco = '" + endereco + "' " + 
						"GROUP BY ass.matriculaassociacao, ass.nome, sigla ORDER BY pontuacao DESC"));
		return rs;
	}
	
	
	public ResultSet buscarAtletasProvaPontuacao(String nome, String classe, String categoria, String dataCompeticao, String endereco) throws SQLException, ParseException, ProvaSemAtletaException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t1 = new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime());
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT pontuacao, " + 
						"sigla, " + 
						"ass.matriculaAssociacao, " +
						"a.nome, "+
						"a.matriculaAtleta, " +
						"tempo " +
						"FROM comp3.atletaprova ap " + 
						"INNER JOIN comp3.atleta a ON a.matriculaAtleta = ap.matriculaAtleta " + 
						"INNER JOIN comp3.associacao ass ON ass.matriculaAssociacao = a.matriculaAssociacao " + 
						"WHERE " + "ap.nomeProva = '" + nome + "' AND ap.classe = '" + classe +  "' AND ap.categoria = '" + categoria +
						"' AND ap.dataCompeticao = '" + t1 + "' AND ap.endereco = '" + endereco + "' " + 
						"ORDER BY tempo DESC"));
		
		if (!rs.next()) throw new ProvaSemAtletaException("Sem atletas inscritos na prova!");
		rs.beforeFirst();
		return rs;
		
		
		
	}

}
