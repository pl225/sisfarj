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
import br.sisfarj.ccomp.dominio.CompeticaoMT;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;


public class TesteAlterarCompeticao extends TesteFuncional {
	
	public TesteAlterarCompeticao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void testAlterarCompeticaoValido () throws Exception {
		
		CompeticaoGateway competicaoGateway = new CompeticaoGateway();
		ResultSet rs = competicaoGateway.buscar("2018-07-15", "rural");
		CompeticaoMT competicaoMT = new CompeticaoMT(rs);
		
		rs = competicaoMT.alterar(
				"2018-07-13", "rural", "2018-07-15", "Nova Iguacu"  
		);
		
		competicaoGateway.atualizar(rs);
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("bdtestes/AlterarCompeticao.xml"));
        ITable expectedTable = expectedDataSet.getTable("competicao");
        
        Assertion.assertEqualsByQuery(expectedTable, getConnection(), "competicao",
        		"SELECT * FROM COMP3.COMPETICAO WHERE DATA = 2018-07-13 AND ENDERECO = Nova Iguacu "
        		+ "ORDER BY ENDERECO DESC"
        		+ " FETCH FIRST 1 ROWS ONLY", new String[]{"ENDERECO" , "DATA"});     
		
	}


}
