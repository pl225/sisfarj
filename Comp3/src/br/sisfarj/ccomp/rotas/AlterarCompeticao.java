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
			ResultSet rs;
			
			if (request.getParameter("dataCompeticao") != null && request.getParameter("endereco") != null) {
				rs = competicaoGateway.buscar(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				request.setAttribute("dados", rs);
				ResultSet locaisCompeticaoRs = new LocalCompeticaoGateway().listarTudo();
				request.setAttribute("locaisCompeticao", locaisCompeticaoRs);
				request.getRequestDispatcher("competicao/EditarCompeticao.jsp").forward(request, response);
			} else {
				rs = competicaoGateway.listarTodas();
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("competicao/AlterarCompeticao.jsp").forward(request, response);	
			}
			
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
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
			validarLancamentoInformacoes(request);
			
			CompeticaoGateway competicaoGateway = new CompeticaoGateway();
			competicaoGateway.alterar(
					request.getParameter("dataCompeticao"), 
					request.getParameter("localCompeticao"),
					request.getParameter("dataCompeticaoAtual"),
					request.getParameter("endereco")
			);
			
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (CampoObrigatorioException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (ParseException | SQLException e) {
			response.getWriter().println(e.getMessage());
		}
	}

	private void validarLancamentoInformacoes(HttpServletRequest request) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos!";
		
		if (request.getParameter("dataCompeticao") == null) throw new CampoObrigatorioException(msg);
		if (request.getParameter("localCompeticao") == null || "".equals(request.getParameter("localCompeticao"))) throw new CampoObrigatorioException(msg);
		
	}

}
