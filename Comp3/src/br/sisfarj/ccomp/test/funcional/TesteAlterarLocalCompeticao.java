package br.sisfarj.ccomp.test.funcional;

import java.io.File;
import java.sql.ResultSet;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.dominio.LocalCompeticaoMT;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;

public class TesteAlterarLocalCompeticao extends TesteFuncional{
	
	public TesteAlterarLocalCompeticao(String metodo) {
		super(metodo);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void testAlterarLocalCompeticao () throws Exception {
		
		LocalCompeticaoGateway localCompeticaoGateway = new LocalCompeticaoGateway();
		ResultSet rs = localCompeticaoGateway.buscar();
		LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(rs);
		
		rs = localCompeticaoMT.alterar("Nome Local Alterado", "Avenida R, 4", "T","F");
				
		localCompeticaoGateway.atualizar(rs);
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/alterarLocalCompeticao.xml"));
        ITable expectedTable = expectedDataSet.getTable("associacao");
        
        Assertion.assertEqualsByQuery(expectedTable, getConnection(), "associacao",
        		"SELECT * FROM COMP3.LOCALCOMPETICAO WHERE ENDERECO = ''"
        		+ "ORDER BY ENDERECO DESC"
        		+ " FETCH FIRST 1 ROWS ONLY", new String[]{"ENDERECO"});
		
	}
	
	public static void main(String[] args) {
		try {
			new TesteAlterarLocalCompeticao("testAlterarLocalCompeticao").run();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
