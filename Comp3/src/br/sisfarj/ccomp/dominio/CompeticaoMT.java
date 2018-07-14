package br.sisfarj.ccomp.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.derby.tools.sysinfo;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.UpdatingQuery;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.adapter.ResultSetCompeticao;
import br.sisfarj.ccomp.dominio.exceptions.CompeticaoJaExisteException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;
import br.sisfarj.ccomp.gateways.CompeticaoProvaGateway;
import br.sisfarj.ccomp.gateways.ProvaGateway;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;

public class CompeticaoMT {

	private ResultSet rs;

	public CompeticaoMT(ResultSet rs) {
		this.rs = rs;
	}
	
	public ResultSet getProvas(String dataCompeticao, String endereco) throws SQLException, CompeticaoNaoEncontradaException {
		
		this.rs.beforeFirst();
		while (rs.next()) {
			if (rs.getString("dataCompeticao").equals(dataCompeticao) && rs.getString(endereco).equals(endereco)) {
				CompeticaoProvaGateway competicaoProvaGateway = new CompeticaoProvaGateway();
				ResultSet rs = competicaoProvaGateway.getProvasPelaCompeticao(dataCompeticao, endereco);
				//CompeticaoProvaMT competicaoProvaMT = new CompeticaoProvaMT(rs);
				//return competicaoProvaMT.getProvasDaCompeticao(dataCompeticao, endereco);
				return rs;
			}
		}
		throw new CompeticaoNaoEncontradaException();
		
		
		
	}

	private ResultSet competicaoProvaMT(String dataCompeticao, String endereco) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSetAdapter listarTodas() throws SQLException, NaoHaCompeticaoException {
		if (!this.rs.next()) throw new NaoHaCompeticaoException();
		this.rs.beforeFirst();
		return new ResultSetCompeticao(this.rs);
	}

	public ResultSetAdapter getCompeticao(String dataCompeticao, String endereco) throws SQLException, CompeticaoNaoEncontradaException, ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		Timestamp timestampCompeticao = new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime());
		this.rs.beforeFirst();
		while(this.rs.next()) {
			if (this.rs.getTimestamp("dataCompeticao").equals(timestampCompeticao) 
					&& this.rs.getString("endereco").equals(endereco)) {
				return new ResultSetCompeticao(this.rs);
			}
		}
		throw new CompeticaoNaoEncontradaException();
	}
	
	public ResultSet alterar(String dataCompeticao, String localCompeticao, 
			String dataCompeticaoAntiga, String endereco) throws ParseException, SQLException, CampoObrigatorioException, CompeticaoNaoEncontradaException {
		
		validarLancamentoInformacoes(dataCompeticao, localCompeticao);
		
		getCompeticao(dataCompeticaoAntiga, endereco);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		Timestamp timestampCompeticao = new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime());
		System.out.println(timestampCompeticao.toString());
		
		this.rs.updateString("DATACOMPETICAO", timestampCompeticao.toString());
		this.rs.updateString("ENDERECO", localCompeticao);
		
		return this.rs;
		
	}
	
	private void validarLancamentoInformacoes(String dataCompeticao, String localCompeticao) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos!";
		
		if (dataCompeticao == null ||  dataCompeticao.isEmpty()) throw new CampoObrigatorioException(msg);
		if (localCompeticao == null || localCompeticao.isEmpty()) throw new CampoObrigatorioException(msg);
		
	}
	
	public void inserir(String nomeCompeticao, String dataCompeticao, String endereco, 
			String tipoPiscina, String[] nomesProva,
			String[] classes, String[] categorias) throws SQLException, ParseException, CompeticaoJaExisteException, CampoObrigatorioException {
		
		validarPasso3(nomeCompeticao, dataCompeticao);
		validarPasso5(endereco, tipoPiscina, nomesProva, categorias, classes);
		if (this.rs.next()) throw new CompeticaoJaExisteException();
		
		this.rs.moveToInsertRow();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		Timestamp timestampCompeticao = new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime());
		
		this.rs.updateString("nome", nomeCompeticao);
		this.rs.updateString("endereco", endereco);
		this.rs.updateString("tipoPiscina", tipoPiscina);
		this.rs.updateTimestamp("dataCompeticao", timestampCompeticao);
		
		new CompeticaoGateway().inserir(this.rs);
		
		ProvaGateway provaGateway = new ProvaGateway();
		ResultSet rs = provaGateway.buscar();
		ProvaMT provaMT = new ProvaMT(rs);
		
		for (String nomeProva : nomesProva) {
			for (String classe : classes) {
				for (String categoria : categorias) {
					rs = provaMT.inserir(timestampCompeticao, endereco, nomeProva, classe, categoria);
					provaGateway.inserir(rs);
					rs = provaGateway.buscar();
					provaMT = new ProvaMT(rs);
				}
			}
		}
	}
	
	private void validarPasso3(String nome, String dataCompeticao) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos.";
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(dataCompeticao);
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (nome == null || nome.isEmpty()) throw new CampoObrigatorioException(msg);
		
	}
	
	private void validarPasso5(String endereco, String tipoPiscina, String [] nomesProva,
			String [] categorias, String [] classes)  throws CampoObrigatorioException {
		
		String msg = "Preencha todos os campos.";
		
		if (endereco == null || endereco.isEmpty()) throw new CampoObrigatorioException(msg);
		if (tipoPiscina == null || tipoPiscina.isEmpty()) throw new CampoObrigatorioException(msg);
		if (nomesProva == null || nomesProva.length == 0) throw new CampoObrigatorioException(msg);
		if (classes == null || classes.length == 0) throw new CampoObrigatorioException(msg);
		if (categorias == null || categorias.length == 0) throw new CampoObrigatorioException(msg);
		
	}
	
}
