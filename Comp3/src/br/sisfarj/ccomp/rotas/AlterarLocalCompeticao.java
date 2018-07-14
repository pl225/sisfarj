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
import br.sisfarj.ccomp.aplicacao.ConstantesPiscina;
import br.sisfarj.ccomp.aplicacao.VerificarIdentificacaoUsuario;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.dominio.LocalCompeticaoMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.InformacoesInvalidasException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

/**
 * Servlet implementation class AlterarLocalCompeticao
 */
@WebServlet("/AlterarLocalCompeticao")
public class AlterarLocalCompeticao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarLocalCompeticao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			
			LocalCompeticaoGateway lcg = new LocalCompeticaoGateway();
			LocalCompeticaoMT localCompeticaoMT;
			ResultSet rs;
			
			if (request.getParameter("endereco") != null) {
				rs = lcg.buscar(request.getParameter("endereco"));
				localCompeticaoMT = new LocalCompeticaoMT(rs);
				ResultSetAdapter rsa = localCompeticaoMT.getLocalCompeticao(request.getParameter("endereco"));
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("localCompeticao/EditarLocalCompeticao.jsp").forward(request, response);
			} else {			
				rs = lcg.listarTudo();
				localCompeticaoMT = new LocalCompeticaoMT(rs);
				ResultSetAdapter rsa = localCompeticaoMT.listarTudo();
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("localCompeticao/AlterarLocalCompeticao.jsp").forward(request, response);
			}
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usu·rio n„o identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException | LocalNaoEncontradoException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("Menu").forward(request, response);
			e.printStackTrace();
		}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			
			LocalCompeticaoGateway lcg = new LocalCompeticaoGateway();
			ResultSet rs = lcg.buscar(request.getParameter("endereco"));
			LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(rs);
			
			rs = localCompeticaoMT.alterar(request.getParameter("nome"),
					                         request.getParameter("endereco"),
					                         request.getParameter("piscina25"),
					                         request.getParameter("piscina50"));
			
			lcg.atualizar(rs);
			
			request.getRequestDispatcher("Menu").forward(request, response);
			
		} catch (SQLException e) {
			response.getWriter().println(e.getMessage());
		} catch (InformacoesInvalidasException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (CampoObrigatorioException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usu√°rio n√£o identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (LocalNaoEncontradoException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		}
	}
	

}
