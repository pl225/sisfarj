package br.sisfarj.ccomp.test.funcional;

import java.io.File;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.gateways.AssociacaoGateway;

public class TesteFiliarAssociacao extends TesteFuncional {

	public TesteFiliarAssociacao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void filiarAssociacaoValida () throws Exception {
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		associacaoGateway.inserir(
				"54732", 
				"2018-08-01", 
				"Primeiro teste",
				"PT",
				"Rua teste, 1",
				"5362522",
				"3758362",
				"123456"
		);
		
		IDataSet dadosSetBanco = getConnection().createDataSet();
		ITable tabelaAssociacao = dadosSetBanco.getTable("Associacao");
		ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(tabelaAssociacao, new String[]{"MATRICULAASSOCIACAO"});
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/filiarAssociacao.xml"));
        ITable expectedTable = expectedDataSet.getTable("associacao");
        
        Assertion.assertEquals(expectedTable, filteredTable);     
		
	}

	public static void main(String[] args) {
		try {
			new TesteFiliarAssociacao("filiarAssociacaoValida").runTest();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
