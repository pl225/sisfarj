package br.sisfarj.ccomp.test.funcional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.sisfarj.ccomp.dominio.AssociacaoMT;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;

public class TesteAssociacaoMT {

	@Test
	public void testGetMatricula () throws SQLException, AssociacaoNaoEncontradaException {
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		ResultSet rs = associacaoGateway.buscar("1000000");
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		assertEquals(1000000, associacaoMT.getMatricula(1000000));	
		
	}
	
	@Test
	public void testTemAcesso () throws SQLException, AssociacaoNaoEncontradaException {
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		ResultSet rs = associacaoGateway.buscar("1000000");
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		assertTrue(associacaoMT.temAcesso(1000000));	
		
	}
	
	@Test
	public void testGetAssociacao () throws SQLException, AssociacaoNaoEncontradaException {
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		ResultSet rs = associacaoGateway.buscar("1000000");
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		assertNotNull(associacaoMT.getAssociacao(1000000));	
		
	}
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TesteAssociacaoMT.class);
		
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());

	}
	
}
