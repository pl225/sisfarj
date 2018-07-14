package br.sisfarj.ccomp.rotas;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.aplicacao.VerificarIdentificacaoUsuario;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.dominio.CompeticaoMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaPontuacaoException;
import br.sisfarj.ccomp.gateways.AtletaProvaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;

/**
 * Servlet implementation class ListarPontuacaoFinal
 */
@WebServlet("/ListarPontuacaoFinal")
public class ListarPontuacaoFinal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarPontuacaoFinal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			CompeticaoGateway competicaoGateway = new CompeticaoGateway();
			ResultSet rs;
			
			if (request.getParameter("dataCompeticao") != null && request.getParameter("endereco") != null) {
				
				rs = competicaoGateway.buscar(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				CompeticaoMT competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.listarPontuacaoFinal(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("pontuacaoFinal/ListarPontuacao.jsp").forward(request, response);
				
			} else {
				rs = competicaoGateway.listarTodas();
				CompeticaoMT competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.listarTodas();
				
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("pontuacaoFinal/ListarCompeticoes.jsp").forward(request, response);
			}
			
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException | ParseException e) {
			e.printStackTrace(response.getWriter());
			//response.getWriter().println(e.getMessage());
		} catch (NaoHaCompeticaoException | CompeticaoNaoEncontradaException  | NaoHaPontuacaoException e) {
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
