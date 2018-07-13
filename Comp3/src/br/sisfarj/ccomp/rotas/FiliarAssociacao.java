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
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.dominio.AssociacaoMT;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;

/**
 * Servlet implementation class FiliarAssociacao
 */
@WebServlet("/FiliarAssociacao")
public class FiliarAssociacao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FiliarAssociacao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			request.getRequestDispatcher("associacao/FiliarAssociacao.jsp").forward(request, response);
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
		
			AssociacaoGateway associacaoGateway = new AssociacaoGateway();
			ResultSet rs = associacaoGateway.buscar();
			AssociacaoMT associacaoMT = new AssociacaoMT(rs);
			
			rs = associacaoMT.inserir(request.getParameter("numeroOficio"), 
					request.getParameter("dataOficio"), 
					request.getParameter("nome"),
					request.getParameter("sigla"),
					request.getParameter("endereco"),
					request.getParameter("telefone"),
					request.getParameter("numeroComprovantePagamento"));
			
			associacaoGateway.inserir(rs);
			
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

}
