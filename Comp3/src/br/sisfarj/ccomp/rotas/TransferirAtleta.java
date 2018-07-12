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
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAssociacaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.AtletaNaoEncontradoException;

/**
 * Servlet implementation class TransferirAtleta
 */
@WebServlet("/TransferirAtleta")
public class TransferirAtleta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferirAtleta() {
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
			ResultSet rs;
			
			if (request.getParameter("matriculaAtleta") != null) {
				rs = atletaGateway.buscar(request.getParameter("matriculaAtleta"));
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("atleta/TransferirAtleta.jsp").forward(request, response);
			} else {			
				rs = atletaGateway.listarTodos();
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("atleta/ListarTransferirAtleta.jsp").forward(request, response);
			}
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usu√°rio n√£o identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (SQLException e) {
			System.out.println("N aguento mais");
			response.getWriter().println(e.getMessage());
			
		} catch (AtletaNaoEncontradoException | NaoHaAtletaException e) {
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
			
			AtletaGateway atletaGateway = new AtletaGateway();
			
			atletaGateway.transferirAtleta(request.getParameter("matriculaAtleta"), 
					request.getParameter("associacao"),
					request.getParameter("numero"),
					request.getParameter("oficio"),
					request.getParameter("comprovante"),
					request.getParameter("entrada"));
		
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usu·rio n„o identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (CampoObrigatorioException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		} catch (AssociacaoNaoEncontradaException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		}
	}
	
	private void validarLancamentoInformacoes(HttpServletRequest request) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos!";
		try {
			System.out.println("AQUI");
			Integer.parseInt(request.getParameter("numero"));
			Integer.parseInt(request.getParameter("comprovante"));
			Integer.parseInt(request.getParameter("associacao"));
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(request.getParameter("oficio"));
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(request.getParameter("entrada"));
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
				
	}

}
