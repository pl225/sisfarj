package br.sisfarj.ccomp.rotas;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.aplicacao.VerificarIdentificacaoUsuario;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;


/**
 * Servlet implementation class FiliarAssociacao
 */
@WebServlet("/CadastrarAtleta")
public class CadastrarAtleta extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastrarAtleta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			request.getRequestDispatcher("atleta/CadastrarAtleta.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		}
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			validarLancamentoInformacoes(request);
			
			AtletaGateway atletaGateway = new AtletaGateway();
			int matriculaGerada = atletaGateway.inserir(request.getParameter("numero"), 
					request.getParameter("oficio"), 
					request.getParameter("nome"),
					request.getParameter("nascimento"),
					request.getParameter("entrada"),
					request.getParameter("associacao"),
					request.getParameter("comprovante"));
		
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (CampoObrigatorioException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		} catch (AssociacaoNaoEncontradaException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		}
	
	}
	
	private void validarLancamentoInformacoes(HttpServletRequest request) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos!";
		try {
			Integer.parseInt(request.getParameter("numero"));
			Integer.parseInt(request.getParameter("comprovante"));
			Integer.parseInt(request.getParameter("associacao"));
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(request.getParameter("oficio"));
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(request.getParameter("entrada"));
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(request.getParameter("nascimento"));
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (request.getParameter("nome") == null) throw new CampoObrigatorioException(msg);
				
	}
}
