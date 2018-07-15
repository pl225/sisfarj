package br.sisfarj.ccomp.test.unitario;

import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.dominio.AssociacaoMT;
import br.sisfarj.ccomp.dominio.LocalCompeticaoMT;
import br.sisfarj.ccomp.dominio.exceptions.InformacoesInvalidasException;

public class TesteLocalCompeticaoMT {
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemNome () throws CampoObrigatorioException, InformacoesInvalidasException {
		ResultSet rs = MockCamadaDados.getMock();
		
		LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(rs);
		localCompeticaoMT.validarLancamentoInformacoes("", 
				"Endereco", 
				'T',
				'T');
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemEndereco () throws CampoObrigatorioException, InformacoesInvalidasException {
		ResultSet rs = MockCamadaDados.getMock();
		
		LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(rs);
		localCompeticaoMT.validarLancamentoInformacoes("Nome", 
				"", 
				'T',
				'T');
	}
	
	@Test(expected = InformacoesInvalidasException.class)
	public void testTipoPiscinaNaoSelecionado () throws CampoObrigatorioException, InformacoesInvalidasException {
		ResultSet rs = MockCamadaDados.getMock();
		
		LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(rs);
		localCompeticaoMT.validarLancamentoInformacoes("Nome", 
				"Endereco", 
				'F',
				'F');
	}
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TesteLocalCompeticaoMT.class);
		
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());

	}

}
