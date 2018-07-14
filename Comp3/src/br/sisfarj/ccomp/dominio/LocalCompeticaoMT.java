package br.sisfarj.ccomp.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.aplicacao.ConstantesPiscina;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.adapter.ResultSetLocalCompeticao;
import br.sisfarj.ccomp.dominio.exceptions.InformacoesInvalidasException;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.LocalJaExisteException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

public class LocalCompeticaoMT {

	private ResultSet rs;

	public LocalCompeticaoMT(ResultSet rs) {
		this.rs = rs;
	}
	
	public ResultSetAdapter getLocalCompeticao (String endereco) throws SQLException, LocalNaoEncontradoException {
		getEndereco(endereco);
		return new ResultSetLocalCompeticao(this.rs);
	}
	
	public ResultSet alterar(String nome, String endereco, String piscina25, String piscina50) throws SQLException, CampoObrigatorioException, InformacoesInvalidasException, LocalNaoEncontradoException {
		
		char p25, p50;
		
		if(piscina25 == null) p25 = ConstantesPiscina.FALSE.getValor(); else p25 = ConstantesPiscina.TRUE.getValor();
		if(piscina50 == null) p50 = ConstantesPiscina.FALSE.getValor(); else p50 = ConstantesPiscina.TRUE.getValor();
		
		validarLancamentoInformacoesAtualizacao(nome, endereco, p25, p50);
		
		getEndereco(endereco);
		
		this.rs.updateString("NOME", nome);
		this.rs.updateString("ENDERECO", endereco);
		this.rs.updateString("PISCINA25", String.valueOf(p25));
		this.rs.updateString("PISCINA50", String.valueOf(p50));
		
		return rs;
		
	}
	
	public String getEndereco(String endereco) throws SQLException, LocalNaoEncontradoException {
		this.rs.beforeFirst();
		while (rs.next()) {
			if (rs.getString("endereco").equals(endereco)) return endereco;
		}
		throw new LocalNaoEncontradoException(String.valueOf(endereco));
	}
	
	public ResultSet inserir(String nome, String endereco, String piscina25, String piscina50) throws InformacoesInvalidasException, SQLException, CampoObrigatorioException {
		
		char p25, p50;
		
		if(piscina25 == null) p25 = ConstantesPiscina.FALSE.getValor(); else p25 = ConstantesPiscina.TRUE.getValor();
		if(piscina50 == null) p50 = ConstantesPiscina.FALSE.getValor(); else p50 = ConstantesPiscina.TRUE.getValor();
		
		validarLancamentoInformacoes(nome, endereco, p25, p50);
		
		this.rs.moveToInsertRow();
		this.rs.updateString("NOME", nome);
		this.rs.updateString("ENDERECO", endereco);
		this.rs.updateString("PISCINA25", String.valueOf(p25));
		this.rs.updateString("PISCINA50", String.valueOf(p50));
		
		return rs;
		
	}

	private void validarLancamentoInformacoes(String nome, String endereco, char piscina25, char piscina50) throws InformacoesInvalidasException, CampoObrigatorioException {
		
		if (nome == null || nome.isEmpty()) throw new CampoObrigatorioException("Preencha todos os campos!");
		if (endereco == null || endereco.isEmpty()) throw new CampoObrigatorioException("Preencha todos os campos!");
		if (piscina25 == 'F' && piscina50 == 'F') 
			throw new InformacoesInvalidasException("Um local de competição deve possuir "
					+ "ao menos um tipo de piscina");
	}
	
	private void validarLancamentoInformacoesAtualizacao(String nome, String endereco, char piscina25, char piscina50) throws InformacoesInvalidasException, CampoObrigatorioException {
		
		if (nome == null || nome.isEmpty()) throw new CampoObrigatorioException("Preencha todos os campos!");
		if (endereco == null || endereco.isEmpty()) throw new CampoObrigatorioException("Preencha todos os campos!");
		if (piscina25 == 'F' && piscina50 == 'F') 
			throw new InformacoesInvalidasException("Um local de competição deve possuir "
					+ "ao menos um tipo de piscina");
	}
	

	public ResultSetAdapter listarTudo() throws LocalNaoEncontradoException, SQLException {
		if (!rs.next()) throw new LocalNaoEncontradoException("Nenhum local de competi��o encontrado");
		rs.beforeFirst();
		return new ResultSetLocalCompeticao(this.rs);
	}

	
}
