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
import br.sisfarj.ccomp.dominio.AtletaMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.AtletaNaoEncontradoException;

/**
 * Servlet implementation class AlterarAssociacao
 */
@WebServlet("/AlterarAtleta")
public class AlterarAtleta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarAtleta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			AtletaGateway atletaGateway = new AtletaGateway();
			AtletaMT atletaMT;
			ResultSet rs;
			
			if (request.getParameter("matriculaAtleta") != null) {
				
				rs = atletaGateway.buscar(request.getParameter("matriculaAtleta"));
				atletaMT = new AtletaMT(rs);
				ResultSetAdapter rsa = atletaMT.getAtleta(Integer.parseInt(request.getParameter("matriculaAtleta")));
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("atleta/EditarAtleta.jsp").forward(request, response);
				
			} else {			
				rs = atletaGateway.listarTodos(); 
				atletaMT = new AtletaMT(rs);
				ResultSetAdapter rsa = atletaMT.listarTodos();
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("atleta/AlterarAtleta.jsp").forward(request, response);
				
			}
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usu·rio n„o identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException e) {
			response.getWriter().println(e.getMessage());
		} catch (AtletaNaoEncontradoException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		} catch (NaoHaAtletaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			
			AtletaGateway atletaGateway = new AtletaGateway();
			ResultSet rs = atletaGateway.buscar(request.getParameter("matriculaAtleta"));
			AtletaMT atletaMT = new AtletaMT(rs);
			
			rs = atletaMT.atualizar(
				request.getParameter("matriculaAtleta"),
				request.getParameter("nome"),
				request.getParameter("entrada"), 
				request.getParameter("numero"), 
				request.getParameter("oficio")
			);
			
			atletaGateway.atualizar(rs);
			
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usu√°rio n√£o identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (CampoObrigatorioException | AtletaNaoEncontradoException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		}
	}
	
}