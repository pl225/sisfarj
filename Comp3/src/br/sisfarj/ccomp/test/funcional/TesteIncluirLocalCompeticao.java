package br.sisfarj.ccomp.test.funcional;

import java.io.File;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;

public class TesteIncluirLocalCompeticao extends TesteFuncional {

	public TesteIncluirLocalCompeticao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void testIncluirLocalCompeticao () throws Exception {
		
		LocalCompeticaoGateway localCompeticaoGateway = new LocalCompeticaoGateway();
		localCompeticaoGateway.inserir("Piscina de Xerém", "Xerém, 25", 'F', 'T');
		
		IDataSet dadosSetBanco = getConnection().createDataSet();
		ITable tabelaLocalCompeticao = dadosSetBanco.getTable("LocalCompeticao");
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/IncluirLocalCompeticao.xml"));
        ITable expectedTable = expectedDataSet.getTable("localcompeticao");
                
        Assertion.assertEquals(expectedTable, tabelaLocalCompeticao);
		
	}
	
	public static void main(String[] args) {
		try {
			new TesteIncluirLocalCompeticao("testIncluirLocalCompeticao").run();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
