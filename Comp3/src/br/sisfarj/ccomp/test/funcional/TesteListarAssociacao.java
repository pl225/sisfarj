package br.sisfarj.ccomp.test.funcional;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import br.sisfarj.ccomp.dominio.AssociacaoMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;

public class TesteListarAssociacao extends TesteFuncional{
	
	public TesteListarAssociacao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void testListarAssociacaoValida () throws NaoHaAssociacaoException, SQLException {
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		ResultSet rs = associacaoGateway.listarTodas();
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		ResultSetAdapter rsa;
		rsa = associacaoMT.listarTodas();
		assertNotNull(rsa);
		
		
	}

	public static void main(String[] args) {
		try {
			new TesteListarAssociacao("listarAssociacao").runTest();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
