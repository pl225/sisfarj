package br.sisfarj.ccomp.dominio;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.sisfarj.ccomp.aplicacao.ConstantesPiscina;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.PessoaNaoEncontradaException;

public class AssociacaoMT {

	private ResultSet rs;

	public AssociacaoMT(ResultSet rs) {
		this.rs = rs;
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
	
	public static String gerarSenha() {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(10);
		for( int i = 0; i < 10; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}

}
