package br.sisfarj.ccomp.test.funcional;

import java.io.File;
import java.sql.ResultSet;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.dominio.AtletaMT;
import br.sisfarj.ccomp.dominio.CompeticaoMT;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;

public class TesteCriarCompeticao extends TesteFuncional{
	
	public TesteCriarCompeticao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void testCadastrarAtleta () throws Exception {
		
		CompeticaoGateway competicaoGateway = new CompeticaoGateway();
		ResultSet rs = competicaoGateway.buscar("2017-05-01 00:00:00.0", "Barra");
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);
		
		competicaoMT.inserir("Campeonato de Verao", "2017-05-01 00:00:00.0", "Barra", "25M", new String[] {"L50"}, new String[] {"MIRIM"}, new String[] {"MASCULINO"});
		
		IDataSet dadosSetBanco = getConnection().createDataSet();
		ITable tabelaCompeticao = dadosSetBanco.getTable("competicao");
				
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/criarCompeticao.xml"));
        ITable expectedTable = expectedDataSet.getTable("competicao");
                
        Assertion.assertEqualsByQuery(expectedTable, getConnection(), "competicao",
        		"SELECT * FROM COMP3.COMPETICAO ORDER BY DATACOMPETICAO DESC"
        		+ " FETCH FIRST 1 ROWS ONLY", new String[]{""});
		
	}
	
}
