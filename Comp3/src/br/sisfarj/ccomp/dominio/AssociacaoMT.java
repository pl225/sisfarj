package br.sisfarj.ccomp.dominio;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.aplicacao.ConstantesPiscina;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.bd.BDConnection;
import br.sisfarj.ccomp.bd.UpdatingQuery;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAssociacao;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.PessoaNaoEncontradaException;

public class AssociacaoMT {

	private ResultSet rs;

	public AssociacaoMT(ResultSet rs) {
		this.rs = rs;
	}
	
	public ResultSetAdapter getAssociacao (int matricula) throws SQLException, AssociacaoNaoEncontradaException {
		getMatricula(matricula);
		return new ResultSetAssociacao(this.rs);
	}

	public boolean temAcesso(int matricula) throws SQLException, AssociacaoNaoEncontradaException {
		this.rs.beforeFirst();
		while (this.rs.next()) {
			if (this.rs.getInt("matriculaAssociacao") == matricula) {
				return this.rs.getString("temAcesso").equals("T");
			}
		}
		throw new AssociacaoNaoEncontradaException(String.valueOf(matricula));
	}
	
	public int getMatricula(int matricula) throws SQLException, AssociacaoNaoEncontradaException {
		this.rs.beforeFirst();
		while (rs.next()) {
			if (rs.getInt("matriculaAssociacao") == matricula) return matricula;
		}
		throw new AssociacaoNaoEncontradaException(String.valueOf(matricula));
	}
	
	public String gerarSenha() {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(10);
		for( int i = 0; i < 10; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
	
	public ResultSet inserir(String numeroOficio, String dataOficio, String nome, 
			String sigla, String endereco, String telefone, 
			String numeroPagamento) throws SQLException, ParseException, CampoObrigatorioException {
		
		this.validarLancamentoInformacoesInsercao(numeroOficio, dataOficio, nome, sigla, endereco, telefone, numeroPagamento);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		this.rs.updateString("NOME", nome);
		this.rs.updateString("TELEFONE", telefone);
		this.rs.updateString("SIGLA", sigla);
		this.rs.updateString("ENDERECO", endereco);
		this.rs.updateString("NUMEROPAGAMENTO", numeroPagamento);
		this.rs.updateString("NUMEROOFICIO", numeroOficio);
		this.rs.updateString("DATAOFICIO", new Timestamp(simpleDateFormat.parse(dataOficio).getTime()).toString());
		this.rs.updateString("TEMACESSO", "T");
		this.rs.updateString("SENHA", gerarSenha());
		
		return rs;
	}
	
	public void validarLancamentoInformacoesInsercao(String numeroOficio, String dataOficio, String nome, 
			String sigla, String endereco, String telefone, 
			String numeroPagamento) throws CampoObrigatorioException {
		
		String msg = "Preencha todos os campos!";
		try {
			Integer.parseInt(numeroOficio);
			Integer.parseInt(numeroPagamento);
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(dataOficio);
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (nome == null || nome.isEmpty()) throw new CampoObrigatorioException(msg);
		if (sigla == null || sigla.isEmpty()) throw new CampoObrigatorioException(msg);
		if (endereco == null || endereco.isEmpty()) throw new CampoObrigatorioException(msg);
		if (telefone == null || telefone.isEmpty()) throw new CampoObrigatorioException(msg);
		
	}
	
	private void validarLancamentoInformacoesAtualizacao(String numeroOficio, String dataOficio, 
			String nome, String sigla, char temAcesso) throws CampoObrigatorioException {
		
		String msg = "Preencha todos os campos!";
		try {
			Integer.parseInt(numeroOficio);
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(dataOficio);
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (nome == null || nome.isEmpty()) throw new CampoObrigatorioException(msg);
		if (sigla == null || sigla.isEmpty()) throw new CampoObrigatorioException(msg);
		
	}
	
	public ResultSet atualizar(String matriculaAssociacao, String numeroOficio, String dataOficio, 
			String nome, String sigla, char temAcesso) throws SQLException, ParseException, CampoObrigatorioException, AssociacaoNaoEncontradaException {
		
		validarLancamentoInformacoesAtualizacao(numeroOficio, dataOficio, nome, sigla, temAcesso);
		
		getMatricula(Integer.parseInt(matriculaAssociacao));		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		Timestamp t = new Timestamp(simpleDateFormat.parse(dataOficio).getTime());
		
		this.rs.updateString("NOME", nome);
		this.rs.updateString("SIGLA", sigla);
		this.rs.updateString("NUMEROOFICIO", numeroOficio);
		this.rs.updateString("DATAOFICIO", t.toString());
		this.rs.updateString("TEMACESSO", "T");
	
		return this.rs;
		
	}

	public ResultSetAdapter listarTodas() throws NaoHaAssociacaoException, SQLException {
		if (!this.rs.next()) throw new NaoHaAssociacaoException();
		this.rs.beforeFirst();
		return new ResultSetAssociacao(this.rs);
	}

}
