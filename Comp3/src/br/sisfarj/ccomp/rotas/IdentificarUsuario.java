package br.sisfarj.ccomp.rotas;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.sisfarj.ccomp.aplicacao.exception.CampoObrigatorioException;
import br.sisfarj.ccomp.dominio.PessoaMT;
import br.sisfarj.ccomp.gateways.PessoaGateway;
import br.sisfarj.ccomp.gateways.exceptions.PessoaNaoEncontradaException;

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
		request.getSession().invalidate();
		request.getRequestDispatcher("IdentificarUsuario.jsp").forward(request, response);
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
			
			ResultSet rs = new PessoaGateway().buscar(matricula, senha);
			PessoaMT pessoaMT = new PessoaMT(rs);
			
			request.getSession().setAttribute("matricula", pessoaMT.getMatricula(matricula));
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
			
		} catch (CampoObrigatorioException | PessoaNaoEncontradaException e) {
			request.setAttribute(ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
