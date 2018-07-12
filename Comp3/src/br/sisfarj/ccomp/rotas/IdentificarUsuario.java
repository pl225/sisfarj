package br.sisfarj.ccomp.rotas;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.sisfarj.ccomp.aplicacao.VerificarIdentificacaoUsuario;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.dominio.AssociacaoMT;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;

/**
 * Servlet implementation class IdentificarUsuario
 */
@WebServlet("/IdentificarUsuario")
public class IdentificarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ERRO = "erro";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdentificarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.getSession().invalidate();
			request.getRequestDispatcher("IdentificarUsuario.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int matricula;
			try {
				matricula = Integer.parseInt(request.getParameter("matricula"));
			} catch (NumberFormatException e) {
				throw new CampoObrigatorioException("O campo matrícula é de preenchimento obrigatório");
			}
			String senha = request.getParameter("senha");
			if ("".equals(senha)) throw new CampoObrigatorioException("O campo senha é de preenchimento obrigatório");
			
			ResultSet rs = new AssociacaoGateway().buscar(matricula, senha);
			AssociacaoMT associacaoMT = new AssociacaoMT(rs);
			
			if (associacaoMT.temAcesso(matricula)) {
				request.getSession().setAttribute("matricula", associacaoMT.getMatricula(matricula));
				request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
			} else {
				doGet(request, response);
			}
			
		} catch (CampoObrigatorioException | AssociacaoNaoEncontradaException e) {
			request.setAttribute(ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
