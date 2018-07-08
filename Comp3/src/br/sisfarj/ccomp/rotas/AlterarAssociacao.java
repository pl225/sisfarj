package br.sisfarj.ccomp.rotas;

import java.io.IOException;
import java.sql.ResultSet;
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
import br.sisfarj.ccomp.aplicacao.exception.CampoObrigatorioException;
import br.sisfarj.ccomp.aplicacao.exception.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;

/**
 * Servlet implementation class AlterarAssociacao
 */
@WebServlet("/AlterarAssociacao")
public class AlterarAssociacao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarAssociacao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			AssociacaoGateway associacaoGateway = new AssociacaoGateway();
			ResultSet rs;
			
			if (request.getParameter("matriculaAssociacao") != null) {
				rs = associacaoGateway.buscar(request.getParameter("matriculaAssociacao"));
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("associacao/EditarAssociacao.jsp").forward(request, response);
			} else {			
				rs = associacaoGateway.listarTodas();
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("associacao/AlterarAssociacao.jsp").forward(request, response);
			}
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (SQLException e) {
			response.getWriter().println(e.getMessage());
		} catch (NaoHaAssociacaoException | AssociacaoNaoEncontradaException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			validarLancamentoInformacoes(request);
			
			AssociacaoGateway associacaoGateway = new AssociacaoGateway();
			associacaoGateway.atualizar(
				request.getParameter("matriculaAssociacao"),
				request.getParameter("numeroOficio"), 
				request.getParameter("dataOficio"), 
				request.getParameter("nome"),
				request.getParameter("sigla")	
			);
			
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (CampoObrigatorioException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		}
	}
	
	private void validarLancamentoInformacoes(HttpServletRequest request) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos!";
		try {
			Integer.parseInt(request.getParameter("numeroOficio"));
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(request.getParameter("dataOficio"));
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (request.getParameter("nome") == null) throw new CampoObrigatorioException(msg);
		if (request.getParameter("sigla") == null) throw new CampoObrigatorioException(msg);
		
	}

}
