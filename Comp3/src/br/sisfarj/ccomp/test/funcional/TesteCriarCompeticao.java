package br.sisfarj.ccomp.test.funcional;

import java.io.File;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;

public class TesteCriarCompeticao extends TesteFuncional{
	
	public TesteCriarCompeticao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void testIncluirLocalCompeticao () throws Exception {
		
		CompeticaoGateway competicaoGateway = new CompeticaoGateway();
		
		AtletaGateway atletaGateway = new AtletaGateway();
		atletaGateway.inserir(
			"47563",
			"2017-05-01 09:35:00.0",
			"Victor Diniz",
			"1996-08-24 10:00:05.0",
			"2009-09-09 14:25:00.0",
			"1000000",
			"435241"
		);
		
		IDataSet dadosSetBanco = getConnection().createDataSet();
		ITable tabelaAtleta = dadosSetBanco.getTable("Atleta");
		ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(tabelaAtleta, new String[]{"MATRICULAASSOCIACAO"});
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/cadastrarAtleta.xml"));
        ITable expectedTable = expectedDataSet.getTable("atleta");
                
        Assertion.assertEquals(expectedTable, filteredTable);
		
	}
	
}
