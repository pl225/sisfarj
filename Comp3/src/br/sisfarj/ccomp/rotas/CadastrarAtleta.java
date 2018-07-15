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
import br.sisfarj.ccomp.dominio.AtletaMT;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;


/**
 * Servlet implementation class FiliarAssociacao
 */
@WebServlet("/CadastrarAtleta")
public class CadastrarAtleta extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastrarAtleta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			request.getRequestDispatcher("atleta/CadastrarAtleta.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		}
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
						
			AtletaGateway atletaGateway = new AtletaGateway();
			ResultSet rs = atletaGateway.buscar();
			AtletaMT atletaMT = new AtletaMT(rs);
			
			rs = atletaMT.inserir(request.getParameter("numero"), 
					request.getParameter("oficio"), 
					request.getParameter("nome"),
					request.getParameter("nascimento"),
					request.getParameter("entrada"),
					request.getParameter("associacao"),
					request.getParameter("comprovante"));
			
			atletaGateway.inserir(rs);
					
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (CampoObrigatorioException | AssociacaoNaoEncontradaException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		}
	
	}
	
}
