package br.sisfarj.ccomp.test.funcional;

import java.io.File;
import java.sql.ResultSet;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.aplicacao.ConstantesPiscina;
import br.sisfarj.ccomp.dominio.AssociacaoMT;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;

public class TesteAlterarAssociacao extends TesteFuncional {
	
	public TesteAlterarAssociacao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void filiarAssociacaoValida () throws Exception {
		
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		ResultSet rs = associacaoGateway.buscar("1000302");
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		rs = associacaoMT.atualizar(
				"1000302", "47364", "2018-11-21", "Novo nome para teste", "NNPT", 'F' 
		);
		
		associacaoGateway.atualizar(rs);
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/AlterarAssociacao.xml"));
        ITable expectedTable = expectedDataSet.getTable("associacao");
        
        Assertion.assertEqualsByQuery(expectedTable, getConnection(), "associacao",
        		"SELECT * FROM COMP3.ASSOCIACAO WHERE MATRICULAASSOCIACAO = 1000302 "
        		+ "ORDER BY MATRICULAASSOCIACAO DESC"
        		+ " FETCH FIRST 1 ROWS ONLY", new String[]{"MATRICULAASSOCIACAO", "SENHA"});     
		
	}

	public static void main(String[] args) {
		try {
			new TesteAlterarAssociacao("filiarAssociacaoValida").runTest();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
