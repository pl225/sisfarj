package br.sisfarj.ccomp.test.unitario;

import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.dominio.AssociacaoMT;

public class TesteAssociacaoMT {

	@Test(expected = CampoObrigatorioException.class)
	public void testSemNumeroOficio () throws CampoObrigatorioException {
		ResultSet rs = MockCamadaDados.getMock();
		
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		associacaoMT.validarLancamentoInformacoesInsercao("", 
				"2018-05-03", 
				"Um nome",
				"UN",
				"Local, 35",
				"5643535",
				"555555");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemData () throws CampoObrigatorioException {
		ResultSet rs = MockCamadaDados.getMock();
		
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		associacaoMT.validarLancamentoInformacoesInsercao("555555", 
				"", 
				"Um nome",
				"UN",
				"Local, 35",
				"5643535",
				"555555");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemSigla () throws CampoObrigatorioException {
		ResultSet rs = MockCamadaDados.getMock();
		
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		associacaoMT.validarLancamentoInformacoesInsercao("555555", 
				"2018-05-03", 
				"Um nome",
				"",
				"Local, 35",
				"5643535",
				"555555");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemEndereco () throws CampoObrigatorioException {
		ResultSet rs = MockCamadaDados.getMock();
		
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		associacaoMT.validarLancamentoInformacoesInsercao("555555", 
				"2018-05-03", 
				"Um nome",
				"UN",
				"",
				"5643535",
				"555555");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemTelefone () throws CampoObrigatorioException {
		ResultSet rs = MockCamadaDados.getMock();
		
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		associacaoMT.validarLancamentoInformacoesInsercao("555555", 
				"2018-05-03", 
				"Um nome",
				"UN",
				"Local, 23",
				"",
				"555555");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemNumeroPagamento () throws CampoObrigatorioException {
		ResultSet rs = MockCamadaDados.getMock();
		
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		associacaoMT.validarLancamentoInformacoesInsercao("555555", 
				"2018-05-03", 
				"Um nome",
				"UN",
				"Local, 23",
				"746362",
				"");
	}
	
	@Test
	public void gerarSenha () {
		ResultSet rs = MockCamadaDados.getMock();
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		String senha = associacaoMT.gerarSenha();
		
		assertNotNull(senha);
	}
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TesteAssociacaoMT.class);
		
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());

	}
	
}
