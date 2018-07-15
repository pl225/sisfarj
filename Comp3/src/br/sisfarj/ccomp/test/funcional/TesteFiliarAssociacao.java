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
import br.sisfarj.ccomp.gateways.AssociacaoGateway;

public class TesteFiliarAssociacao extends TesteFuncional {

	public TesteFiliarAssociacao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void filiarAssociacaoValida () throws Exception {
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		ResultSet rs = associacaoGateway.buscar();
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		
		rs = associacaoMT.inserir(
				"54732", 
				"2018-08-01", 
				"Primeiro teste",
				"PT",
				"Rua teste, 1",
				"5362522",
				"3758362"
		);
		
		associacaoGateway.inserir(rs);
				
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/filiarAssociacao.xml"));
        ITable expectedTable = expectedDataSet.getTable("associacao");
        
        Assertion.assertEqualsByQuery(expectedTable, getConnection(), "associacao",
        		"SELECT * FROM COMP3.ASSOCIACAO ORDER BY MATRICULAASSOCIACAO DESC"
        		+ " FETCH FIRST 1 ROWS ONLY", new String[]{"MATRICULAASSOCIACAO", "SENHA"});
        		
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
