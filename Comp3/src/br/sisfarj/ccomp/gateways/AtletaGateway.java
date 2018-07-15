package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.bd.CreatingQuery;
import br.sisfarj.ccomp.bd.UpdatingQuery;

import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.AtletaNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;


public class AtletaGateway {
	public void inserir(String numero, String oficio, String nome, String nascimento, String entrada,
		String associacao, String comprovante) throws SQLException, ParseException, AssociacaoNaoEncontradaException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		new AssociacaoGateway().buscar(associacao); 
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t1 = new Timestamp(simpleDateFormat.parse(oficio).getTime());
		Timestamp t2 = new Timestamp(simpleDateFormat.parse(nascimento).getTime());
		Timestamp t3 = new Timestamp(simpleDateFormat.parse(entrada).getTime());
		
		bdConnection.execute(new UpdatingQuery("INSERT INTO comp3.atleta (numeroOficio, dataOficio, nome, dataNascimento, matriculaAssociacao, numeroPagamento, dataEntrada) "
				+ "VALUES (" + numero + ", '" + t1 + "', '" + nome + "', '"
						+ t2 + "', " +  associacao + ", " + comprovante + ", '" + t3 + "')"), "MATRICULAATLETA");
		
	}
	
	
	public ResultSet listarTodos() throws SQLException, NaoHaAtletaException {
		
		BDConnection bdConnection = new BDConnection(false);
				
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT matriculaAtleta, nome FROM comp3.atleta"));
		
		if (!rs.next()) throw new NaoHaAtletaException();
		rs.beforeFirst();
		return rs;
	}
	
	public ResultSet buscar(String matriculaAtleta) throws SQLException, AtletaNaoEncontradoException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT * FROM comp3.atleta WHERE matriculaAtleta = " + matriculaAtleta)
		);
		
		if (!rs.next()) throw new AtletaNaoEncontradoException(matriculaAtleta);
		rs.beforeFirst();
		return rs;
		
	}
	
	
/*	public void atualizar(String matriculaAtleta, String matriculaAssociacao, String nome, String entrada, String numero, 
			String oficio) throws SQLException, ParseException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t1 = new Timestamp(simpleDateFormat.parse(oficio).getTime());
		Timestamp t2 = new Timestamp(simpleDateFormat.parse(entrada).getTime());
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("UPDATE comp3.atleta SET "
				+ "matriculaAssociacao = " + matriculaAssociacao + " numeroOficio = " + numero + ", dataOficio = '" + t1 +  "', nome = '" + nome +	"', " 
				+ "dataEntrada = '" + t2 + "' " 
				+ "WHERE matriculaAtleta = " + matriculaAtleta));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}*/
	
	public void atualizar(ResultSet rs) throws SQLException {
		rs.updateRow();
		rs.close();
	}
	
	public void transferirAtleta(String matriculaAtleta, String matriculaAssociacao, String numeroOficio, String dataOficio, 
			String numeroComprovante, String dataEntrada) throws SQLException, ParseException, AssociacaoNaoEncontradaException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		Timestamp t1 = new Timestamp(simpleDateFormat.parse(dataOficio).getTime());
		Timestamp t2 = new Timestamp(simpleDateFormat.parse(dataEntrada).getTime());
		
		AssociacaoGateway ag = new AssociacaoGateway();
		ag.buscar(matriculaAssociacao);
		
		int linhasAfetadas = bdConnection.execute(new UpdatingQuery("UPDATE comp3.atleta SET "
				+ "numeroOficio = " + numeroOficio + ", matriculaAssociacao = " + matriculaAssociacao + ", "
				+ "dataOficio = '" + t1 + "', numeroPagamento = " + numeroComprovante + ", "
				+ "dataEntrada = '" + t2 + "' "
				+ "WHERE matriculaAtleta = " + matriculaAtleta));
		
		if (linhasAfetadas <= 0) throw new SQLException();
		
	}
	
	public void inserir(ResultSet rs) throws SQLException {
		rs.insertRow();
		rs.close();
	}
	
	public ResultSet buscar() throws SQLException {
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs =  bdConnection.execute(new CreatingQuery(
			"SELECT * FROM comp3.atleta WHERE 1 = 2"
		));
		rs.moveToInsertRow();
		return rs;
	}
	
	public ResultSet buscarMatricula(String matriculaAtleta) throws SQLException {
		
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT * FROM comp3.atleta WHERE matriculaAtleta = " + matriculaAtleta)
		);
		
		return rs;
		
	}
	
	
}
