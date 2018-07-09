package br.sisfarj.ccomp.aplicacao;

import javax.servlet.http.HttpServletRequest;

import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;

public class VerificarIdentificacaoUsuario {
	
	public static int verificarAutenticacao (HttpServletRequest request) throws UsuarioNaoIdentificadoException {
		try {
			int matricula = (Integer) request.getSession().getAttribute("matricula");
			return matricula;
		} catch (NullPointerException e) {
			throw new UsuarioNaoIdentificadoException();
		}
	}
}
