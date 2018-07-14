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
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.gateways.CompeticaoProvaGateway;
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
	
}
