package br.sisfarj.ccomp.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.adapter.ResultSetLocalCompeticao;
import br.sisfarj.ccomp.dominio.exceptions.InformacoesInvalidasException;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.LocalJaExisteException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

public class LocalCompeticaoMT {

	private ResultSet rs;

	public LocalCompeticaoMT(ResultSet novo) {
		this.rs = novo;
	}
	
	public void insert (String nome, String endereco, char piscina25, char piscina50) throws InformacoesInvalidasException, SQLException {
		validarLancamentoInformacoes(nome, endereco, piscina25, piscina50);
		
		this.rs.moveToInsertRow();
		this.rs.updateString("NOME", nome);
		this.rs.updateString("ENDERECO", endereco);
		this.rs.updateString("PISCINA25", String.valueOf(piscina25));
		this.rs.updateString("PISCINA50", String.valueOf(piscina50));
		
		new LocalCompeticaoGateway().inserir(this.rs);
		
	}

	private void validarLancamentoInformacoes(String nome, String endereco, char piscina25, char piscina50) throws InformacoesInvalidasException {
		if (piscina25 == 'F' && piscina50 == 'F') 
			throw new InformacoesInvalidasException("Um local de competição deve possuir "
					+ "ao menos um tipo de piscina");		
	}
	
	public static void main(String[] args) {
		
		String nome = "Novo place";
		String endereco = "Place, 27";
		char piscina25 = 'T';
		char piscina50 = 'F';
		
		try {
			ResultSet rs = new LocalCompeticaoGateway().criar(endereco);
			LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(rs);
			localCompeticaoMT.insert(nome, endereco, piscina25, piscina50);
		} catch (InformacoesInvalidasException | SQLException | LocalJaExisteException e) {
			System.out.println(e.getMessage());
		}
	}

	public ResultSetAdapter listarTudo() throws LocalNaoEncontradoException, SQLException {
		if (!rs.next()) throw new LocalNaoEncontradoException("Nenhum local de competi��o encontrado");
		rs.beforeFirst();
		return new ResultSetLocalCompeticao(this.rs);
	}

	
}
