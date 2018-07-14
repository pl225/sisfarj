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
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.dominio.CompeticaoMT;
import br.sisfarj.ccomp.dominio.LocalCompeticaoMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

/**
 * Servlet implementation class AlterarCompeticao
 */
@WebServlet("/AlterarCompeticao")
public class AlterarCompeticao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarCompeticao() {
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
			CompeticaoMT competicaoMT;
			ResultSet rs;
			
			if (request.getParameter("dataCompeticao") != null && request.getParameter("endereco") != null) {
				
				rs = competicaoGateway.buscar(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.getCompeticao(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				
				
				ResultSet locaisCompeticaoRs = new LocalCompeticaoGateway().listarTudo();
				LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(locaisCompeticaoRs);
				ResultSetAdapter locaisCompeticaoRsa = localCompeticaoMT.listarTudo();
				
				request.setAttribute("dados", rsa);
				request.setAttribute("locaisCompeticao", locaisCompeticaoRsa);
				request.getRequestDispatcher("competicao/EditarCompeticao.jsp").forward(request, response);
			} else {
				
				rs = competicaoGateway.listarTodas();
				competicaoMT  = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.listarTodas();
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("competicao/AlterarCompeticao.jsp").forward(request, response);	
			}
			
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		} catch (NaoHaCompeticaoException | CompeticaoNaoEncontradaException | LocalNaoEncontradoException e) {
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
			
			CompeticaoGateway competicaoGateway = new CompeticaoGateway();
			
			ResultSet rs = competicaoGateway.buscar(request.getParameter("dataCompeticaoAtual"), 
					request.getParameter("endereco"));
			
			CompeticaoMT competicaoMT = new CompeticaoMT(rs);
			
			rs = competicaoMT.alterar(
					request.getParameter("dataCompeticao"), 
					request.getParameter("localCompeticao"),
					request.getParameter("dataCompeticaoAtual"),
					request.getParameter("endereco")
			);
			
			competicaoGateway.atualizar(rs);
			
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (CampoObrigatorioException | CompeticaoNaoEncontradaException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (ParseException | SQLException e) {
			response.getWriter().println(e.getMessage());
		}
	}

}
