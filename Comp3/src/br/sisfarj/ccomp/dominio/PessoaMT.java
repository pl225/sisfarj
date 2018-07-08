package br.sisfarj.ccomp.dominio;

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
	
}
