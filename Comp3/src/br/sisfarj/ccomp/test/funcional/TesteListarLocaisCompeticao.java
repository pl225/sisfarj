package br.sisfarj.ccomp.test.funcional;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import br.sisfarj.ccomp.aplicacao.ConstantesPiscina;
import br.sisfarj.ccomp.dominio.AssociacaoMT;
import br.sisfarj.ccomp.dominio.AtletaMT;
import br.sisfarj.ccomp.dominio.CompeticaoMT;
import br.sisfarj.ccomp.dominio.LocalCompeticaoMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

public class TesteListarLocaisCompeticao extends TesteFuncional{
	
	public TesteListarLocaisCompeticao(String metodo) {
		super(metodo);
	}

	@Test
	public void testListarCompeticaoValida () throws SQLException, LocalNaoEncontradoException {

			LocalCompeticaoGateway localCompeticaoGateway = new LocalCompeticaoGateway();
			ResultSet rs = localCompeticaoGateway.listarTudo();
			LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(rs);
			
			ResultSetAdapter rsa;
			rsa = localCompeticaoMT.listarTudo();
			assertNotNull(rsa);
		
	}
	
	public static void main(String[] args) {
		try {
			new TesteListarLocaisCompeticao("listarLocalCompeticao").runTest();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
