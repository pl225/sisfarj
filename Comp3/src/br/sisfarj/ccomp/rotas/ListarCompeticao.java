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
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.gateways.AtletaProvaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;
import br.sisfarj.ccomp.gateways.CompeticaoProvaGateway;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.ProvaSemAtletaException;

/**
 * Servlet implementation class ListarCompeticao
 */
@WebServlet("/ListarCompeticao")
public class ListarCompeticao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarCompeticao() {
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
			CompeticaoProvaGateway cpg = new CompeticaoProvaGateway();
			ResultSet rs, rsProvas;
			
			if (request.getParameter("nome") != null && request.getParameter("classe") != null && 
					request.getParameter("categoria") != null) {
				
				rs = new AtletaProvaGateway().buscarAtletasProva(request.getParameter("nome"),
						                                         request.getParameter("classe"), 
						                                         request.getParameter("categoria"), 
						                                         request.getParameter("dataCompeticao"), 
						                                         request.getParameter("endereco"));
				
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("competicao/ListarCompeticaoProva.jsp").forward(request, response);
				
			} else if (request.getParameter("dataCompeticao") != null && request.getParameter("endereco") != null) {
				rs = competicaoGateway.buscar(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				rsProvas = cpg.getProvasPelaCompeticao(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				request.setAttribute("dados", rsProvas);
				request.setAttribute("dadosCompeticao", rs);
				request.getRequestDispatcher("competicao/ListarCompeticao.jsp").forward(request, response);
			
			} else {
				rs = competicaoGateway.listarTodas();
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("competicao/ListarCompeticoes.jsp").forward(request, response);	
			}
			
			
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		} catch (NaoHaCompeticaoException | CompeticaoNaoEncontradaException | ProvaSemAtletaException e) {
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
