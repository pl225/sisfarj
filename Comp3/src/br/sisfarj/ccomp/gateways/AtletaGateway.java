package br.sisfarj.ccomp.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.ConsultingQuery;
import br.sisfarj.ccomp.bd.UpdatingQuery;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.AtletaNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

//request.getParameter("numero"), num,
//request.getParameter("oficio"), date, ok
//request.getParameter("nome"), string,
//request.getParameter("nascimento"), date, ok
//request.getParameter("entrada"), date, ok
//request.getParameter("associacao"), num,
//request.getParameter("comprovante"), num,

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
	
	public ResultSet listarTodos() throws SQLException, AtletaNaoEncontradoException{
		
		BDConnection bdConnection = new BDConnection(false);
		
		ResultSet rs = bdConnection.execute(new ConsultingQuery("SELECT * FROM comp3.Atleta"));
		if (!rs.next()) throw new AtletaNaoEncontradoException();
		rs.beforeFirst();
		return rs;
	}
	
	public ResultSet buscar(String matriculaAtleta) throws SQLException, AtletaNaoEncontradoException {
		
		BDConnection bdConnection = new BDConnection(false);
		ResultSet rs = bdConnection.execute(
				new ConsultingQuery("SELECT "
						+ "*"
						+ " FROM comp3.Atleta "
						+ "WHERE matriculaAtleta = " + matriculaAtleta));
		
		if (!rs.next()) throw new AtletaNaoEncontradoException();
		rs.beforeFirst();
		return rs;
		
	}
	
	public void atualizar(String matriculaAtleta, String matriculaAssociacao, String numeroOficio, String dataOficio, 
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


}
