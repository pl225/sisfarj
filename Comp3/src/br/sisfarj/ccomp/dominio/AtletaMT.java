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
import br.sisfarj.ccomp.dominio.adapter.ResultSetAtleta;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.AtletaNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.PessoaNaoEncontradaException;

public class AtletaMT {

	private ResultSet rs;

	public AtletaMT(ResultSet rs) {
		this.rs = rs;
	}

	public ResultSet inserir(String numero, String oficio, String nome, String nascimento,
			String entrada, String associacao, String comprovante) throws SQLException, ParseException, CampoObrigatorioException, AssociacaoNaoEncontradaException {
		
		this.validarLancamentoInformacoesInsercao(numero, oficio, nome, nascimento, entrada, associacao, comprovante);
		
		AssociacaoGateway associacaoGateway = new AssociacaoGateway();
		ResultSet rs = associacaoGateway.buscar(associacao);
		AssociacaoMT associacaoMT = new AssociacaoMT(rs);
		associacaoMT.getAssociacao(Integer.parseInt(associacao));
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		this.rs.updateString("NUMEROOFICIO", numero);
		this.rs.updateString("DATAOFICIO", new Timestamp(simpleDateFormat.parse(oficio).getTime()).toString());
		this.rs.updateString("NOME", nome);
		this.rs.updateString("DATANASCIMENTO", new Timestamp(simpleDateFormat.parse(nascimento).getTime()).toString());
		this.rs.updateString("DATAENTRADA", new Timestamp(simpleDateFormat.parse(entrada).getTime()).toString());
		this.rs.updateString("MATRICULAASSOCIACAO", associacao);
		this.rs.updateString("NUMEROPAGAMENTO", comprovante);
		
		return rs;

	}

	private void validarLancamentoInformacoesInsercao(String numero, String oficio, String nome, String nascimento,
			String entrada, String associacao, String comprovante) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos!";
		try {
			Integer.parseInt(numero);
			Integer.parseInt(comprovante);
			Integer.parseInt(associacao);
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(oficio);
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(entrada);
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(nascimento);
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (nome == null || nome.isEmpty()) throw new CampoObrigatorioException(msg);
				
	}

	public ResultSetAdapter getAtleta(int matricula) throws SQLException, AtletaNaoEncontradoException {
		getMatricula(matricula);
		return new ResultSetAtleta(this.rs);
	}
	
	public int getMatricula(int matricula) throws SQLException, AtletaNaoEncontradoException {
		this.rs.beforeFirst();
		while (rs.next()) {
			if (rs.getInt("matriculaAtleta") == matricula) return matricula;
		}
		throw new AtletaNaoEncontradoException(String.valueOf(matricula));
	}

	public ResultSetAdapter listarTodos() throws NaoHaAtletaException, SQLException {
		if (!this.rs.next()) throw new NaoHaAtletaException();
			this.rs.beforeFirst();
			return new ResultSetAtleta(this.rs);
	}

	public ResultSet atualizar(String matriculaAtleta, String nome, String entrada,
			String numero, String oficio) throws SQLException, ParseException, CampoObrigatorioException, AtletaNaoEncontradoException {

		validarLancamentoInformacoesAtualizacao(nome, entrada,
				numero, oficio);
		
		getMatricula(Integer.parseInt(matriculaAtleta));		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		Timestamp t1 = new Timestamp(simpleDateFormat.parse(entrada).getTime());
		Timestamp t2 = new Timestamp(simpleDateFormat.parse(oficio).getTime());
		
		this.rs.updateString("NOME", nome);
		this.rs.updateString("NUMEROOFICIO", numero);
		this.rs.updateString("DATAOFICIO", t2.toString());
		this.rs.updateString("DATAENTRADA", t1.toString());
	
		return this.rs;

	}
	
	public ResultSet transferir(String matriculaAtleta, String matriculaAssociacao, String numeroPagamento, String entrada,
			String numero, String oficio) throws SQLException, ParseException, CampoObrigatorioException, AtletaNaoEncontradoException {

		validarLancamentoInformacoesTransferencia(matriculaAssociacao, numeroPagamento, entrada,
				numero, oficio);
		
		getMatricula(Integer.parseInt(matriculaAtleta));		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		Timestamp t1 = new Timestamp(simpleDateFormat.parse(entrada).getTime());
		Timestamp t2 = new Timestamp(simpleDateFormat.parse(oficio).getTime());
		
		this.rs.updateString("MATRICULAASSOCIACAO", matriculaAssociacao);
		this.rs.updateString("NUMEROPAGAMENTO", numeroPagamento);
		this.rs.updateString("NUMEROOFICIO", numero);
		this.rs.updateString("DATAOFICIO", t2.toString());
		this.rs.updateString("DATAENTRADA", t1.toString());
	
		return this.rs;

	}

	private void validarLancamentoInformacoesTransferencia(String matriculaAssociacao, String numeroComprovante, String entrada, String numero,
			String oficio) throws CampoObrigatorioException {
		
		String msg = "Preencha todos os campos!";
		try {
			Integer.parseInt(numero);
			Integer.parseInt(numeroComprovante);
			Integer.parseInt(matriculaAssociacao);
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(oficio);
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(entrada);
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		
	}

	private void validarLancamentoInformacoesAtualizacao(String nome, String entrada, String numero, String oficio)  throws CampoObrigatorioException {
		
		String msg = "Preencha todos os campos!";
		try {
			Integer.parseInt(numero);
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(oficio);
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(entrada);
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (nome == null || nome.isEmpty()) throw new CampoObrigatorioException(msg);
		
	}
	
	
}

