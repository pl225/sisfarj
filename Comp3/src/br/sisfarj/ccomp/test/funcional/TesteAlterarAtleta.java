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
import br.sisfarj.ccomp.dominio.AtletaMT;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.AtletaGateway;

public class TesteAlterarAtleta extends TesteFuncional {
	
	public TesteAlterarAtleta(String metodo) {
		super(metodo);
	}
	
	@Test
	public void testAlterarAtletaValido () throws Exception {
		
		AtletaGateway atletaGateway = new AtletaGateway();
		ResultSet rs = atletaGateway.buscar("1000000");
		AtletaMT atletaMT = new AtletaMT(rs);
		
		rs = atletaMT.atualizar(
				"1000000", "Novo nome para teste", "2018-11-21", "50", "1"  
		);
		
		atletaGateway.atualizar(rs);
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/AlterarAtleta.xml"));
        ITable expectedTable = expectedDataSet.getTable("atleta");
        
        Assertion.assertEqualsByQuery(expectedTable, getConnection(), "atleta",
        		"SELECT * FROM COMP3.ATLETA WHERE MATRICULATLETA = 1000000 "
        		+ "ORDER BY MATRICULAATLETA DESC"
        		+ " FETCH FIRST 1 ROWS ONLY", new String[]{"MATRICULAATLETA"});     
		
	}

	public static void main(String[] args) {
		try {
			new TesteAlterarAtleta("alterarAtletaValido ").runTest();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
