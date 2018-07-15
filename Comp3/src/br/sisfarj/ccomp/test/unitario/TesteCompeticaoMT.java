package br.sisfarj.ccomp.test.unitario;

import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.dominio.CompeticaoMT;


public class TesteCompeticaoMT {
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemDataCompeticao () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);
		competicaoMT.validarLancamentoInformacoes("", "Endereco Local Competicao");
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemLocalCompeticao () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);
		competicaoMT.validarLancamentoInformacoes("2018-05-03", "");
		
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemNome () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);		
		competicaoMT.validarPasso3("", "2018-05-03");
		
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemEndereco () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);	
		
		String [] nomesProva, categorias, classes;
		nomesProva = new String[1];
		categorias = new String[1];
		classes = new String[1];
		
		nomesProva[0] = "nomeProva";
		categorias[0] = "FEMININO";
		classes[0] = "MIRIM";
		
		competicaoMT.validarPasso5("", "25M", nomesProva, categorias, classes);
		
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemTipoPiscina () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);	
		
		String [] nomesProva, categorias, classes;
		nomesProva = new String[1];
		categorias = new String[1];
		classes = new String[1];
		
		nomesProva[0] = "nomeProva";
		categorias[0] = "FEMININO";
		classes[0] = "MIRIM";
		
		competicaoMT.validarPasso5("Endereco", "", nomesProva, categorias, classes);
		
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemNomesProva () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);	
		
		String [] nomesProva, categorias, classes;
		nomesProva = null;
		categorias = new String[1];
		classes = new String[1];
		
		categorias[0] = "FEMININO";
		classes[0] = "MIRIM";
		
		competicaoMT.validarPasso5("Endereco", "25M", nomesProva, categorias, classes);
		
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemCategorias () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);	
		
		String [] nomesProva, categorias, classes;
		nomesProva = new String[1];
		categorias = null;
		classes = new String[1];
		
		nomesProva[0] = "nomeProva";
		classes[0] = "MIRIM";
		
		competicaoMT.validarPasso5("Endereco", "25M", nomesProva, categorias, classes);
		
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testSemClasses () throws CampoObrigatorioException{
		ResultSet rs = MockCamadaDados.getMock();
		
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);	
		
		String [] nomesProva, categorias, classes;
		nomesProva = new String[1];
		categorias = new String[1];
		classes = null;
		
		nomesProva[0] = "nomeProva";
		categorias[0] = "FEMININO";
		
		competicaoMT.validarPasso5("Endereco", "25M", nomesProva, categorias, classes);
		
	}
	
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TesteCompeticaoMT.class);
		
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      
	      System.out.println(result.wasSuccessful());

	}

}
