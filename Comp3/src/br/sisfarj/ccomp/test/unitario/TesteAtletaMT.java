package br.sisfarj.ccomp.test.unitario;

import java.sql.ResultSet;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.dominio.AtletaMT;
import br.sisfarj.ccomp.dominio.CompeticaoMT;

public class TesteAtletaMT {
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemMatriculaAssociacao () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		AtletaMT atletaMT = new AtletaMT(rs);
		atletaMT.validarLancamentoInformacoesTransferencia("", "123456", "2000-03-03", "123456", "2000-03-03");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemNumeroPagamento () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		AtletaMT atletaMT = new AtletaMT(rs);
		atletaMT.validarLancamentoInformacoesTransferencia("123456", "", "2000-03-03", "123456", "2000-03-03");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemDataEntrada () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		AtletaMT atletaMT = new AtletaMT(rs);
		atletaMT.validarLancamentoInformacoesTransferencia("123456", "123456", "", "123456", "2000-03-03");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemNumeroOficio () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		AtletaMT atletaMT = new AtletaMT(rs);
		atletaMT.validarLancamentoInformacoesTransferencia("123456", "123456", "2000-03-03", "", "2000-03-03");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemDataOficio () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		AtletaMT atletaMT = new AtletaMT(rs);
		atletaMT.validarLancamentoInformacoesTransferencia("123456", "123456", "2000-03-03", "123456", "");
	}
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TesteAtletaMT.class);
		
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      
	      System.out.println(result.wasSuccessful());

	}


}
