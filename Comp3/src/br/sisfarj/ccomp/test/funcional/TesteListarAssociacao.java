package br.sisfarj.ccomp.test.funcional;

import java.sql.ResultSet;

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
	public void testListarAssociacaoValida () {
		try {
			AssociacaoGateway associacaoGateway = new AssociacaoGateway();
			ResultSet rs = associacaoGateway.listarTodas();
			AssociacaoMT associacaoMT = new AssociacaoMT(rs);
			
			ResultSetAdapter rsa;
			rsa = associacaoMT.listarTodas();
		} catch (NaoHaAssociacaoException e) {
			
			fail("Erro ao listar associações: " + e.toString());
			
		} catch (Throwable t){
			  //do nothing since other exceptions are OK
		}
		
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
