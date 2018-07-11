package br.sisfarj.ccomp.dominio;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.sisfarj.ccomp.gateways.exceptions.PessoaNaoEncontradaException;

public class PessoaMT {

	private ResultSet rs;

	public PessoaMT(ResultSet rs) {
		this.rs = rs;
	}

	public int getMatricula(int matricula) throws PessoaNaoEncontradaException, SQLException {
		this.rs.beforeFirst();
		while (rs.next()) {
			if (rs.getInt("matricula") == matricula) return matricula;
		}
		throw new PessoaNaoEncontradaException("Matrícula não encontrada!");
	}
	
	public enum TipoPessoa {
		SECRETARIO, DIRETOR_TECNICO, TECNICO_ASSOSSIACAO, PESSOA
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
