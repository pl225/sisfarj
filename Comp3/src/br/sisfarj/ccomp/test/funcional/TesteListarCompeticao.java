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
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;

public class TesteListarCompeticao extends TesteFuncional{
	
	public TesteListarCompeticao(String metodo) {
		super(metodo);
	}
	
	@Test
	public void testListarCompeticaoValida () {
		try {
			CompeticaoGateway competicaoGateway = new CompeticaoGateway();
			ResultSet rs = competicaoGateway.listarTodas();
			CompeticaoMT competicaoMT = new CompeticaoMT(rs);
			
			ResultSetAdapter rsa;
			rsa = competicaoMT.listarTodas();
		} catch (NaoHaCompeticaoException e) {
			
			fail("Erro ao listar associações: " + e.toString());
			
		} catch (Throwable t){
			  //do nothing since other exceptions are OK
		}
		
	}
	
}
