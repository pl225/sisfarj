package br.sisfarj.ccomp.rotas;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.aplicacao.VerificarIdentificacaoUsuario;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

/**
 * Servlet implementation class ListarLocaisDeCompeticao
 */
@WebServlet("/ListarLocaisDeCompeticao")
public class ListarLocaisDeCompeticao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarLocaisDeCompeticao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			
			LocalCompeticaoGateway localCompeticaoGateway = new LocalCompeticaoGateway();
			ResultSet rs = localCompeticaoGateway.listarTudo();
			request.setAttribute("dados", rs);
			
			request.getRequestDispatcher("localCompeticao/ListarLocalCompeticao.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException e) {
			response.getWriter().println(e.getMessage());
		} catch (LocalNaoEncontradoException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("Menu").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
