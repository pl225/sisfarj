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

public class AtletaMT {

	private ResultSet rs;

	public AtletaMT(ResultSet rs) {
		this.rs = rs;
	}

	public ResultSet inserir(String numero, String oficio, String nome, String nascimento,
			String entrada, String associacao, String comprovante) throws SQLException, ParseException, CampoObrigatorioException {
		
		this.validarLancamentoInformacoesInsercao(numero, oficio, nome, nascimento, entrada, associacao, comprovante);
		
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

}

