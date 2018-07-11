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


}
