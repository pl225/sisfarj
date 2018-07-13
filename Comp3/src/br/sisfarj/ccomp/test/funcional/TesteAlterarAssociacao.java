package br.sisfarj.ccomp.test.funcional;

import java.io.File;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.gateways.AssociacaoGateway;

public class TesteAlterarAssociacao extends TesteFuncional {
	
	public TesteAlterarAssociacao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void filiarAssociacaoValida () throws Exception {
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		associacaoGateway.atualizar("1002003", "47364", "2018-11-21", "Novo nome para teste", "NNPT", 'F');
		
		IDataSet dadosSetBanco = getConnection().createDataSet();
		ITable tabelaAssociacao = dadosSetBanco.getTable("Associacao");
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/AlterarAssociacao.xml"));
        ITable expectedTable = expectedDataSet.getTable("associacao");
                
        Assertion.assertEquals(expectedTable, tabelaAssociacao);     
		
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
