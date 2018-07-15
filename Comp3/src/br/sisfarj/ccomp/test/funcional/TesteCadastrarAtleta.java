package br.sisfarj.ccomp.test.funcional;

import java.io.File;
import java.sql.ResultSet;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.dominio.AssociacaoMT;
import br.sisfarj.ccomp.dominio.AtletaMT;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.AtletaGateway;

public class TesteCadastrarAtleta extends TesteFuncional {

	public TesteCadastrarAtleta(String metodo) {
		super(metodo);
	}
	
	@Test
	public void testCadastrarAtleta () throws Exception {
		
		AtletaGateway atletaGateway = new AtletaGateway();
		ResultSet rs = atletaGateway.buscar();
		AtletaMT atletaMT = new AtletaMT(rs);
		
		rs = atletaMT.inserir(
			"47563",
			"2017-05-01 09:35:00.0",
			"Victor Diniz",
			"1996-08-24 10:00:05.0",
			"2009-09-09 14:25:00.0",
			"1000000",
			"435241"
		);
		
		atletaGateway.inserir(rs);
		
		IDataSet dadosSetBanco = getConnection().createDataSet();
		ITable tabelaAtleta = dadosSetBanco.getTable("Atleta");
		ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(tabelaAtleta, new String[]{"MATRICULAATLETA"});
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/cadastrarAtleta.xml"));
        ITable expectedTable = expectedDataSet.getTable("atleta");
                
        Assertion.assertEqualsByQuery(expectedTable, getConnection(), "atleta",
        		"SELECT * FROM COMP3.ATLETA ORDER BY MATRICULAATLETA DESC"
        		+ " FETCH FIRST 1 ROWS ONLY", new String[]{"MATRICULAATLETA"});
		
	}

}
