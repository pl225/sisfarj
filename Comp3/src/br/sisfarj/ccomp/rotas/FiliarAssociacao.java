package br.sisfarj.ccomp.rotas;

import java.io.IOException;
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
import br.sisfarj.ccomp.gateways.AssociacaoGateway;
import br.sisfarj.ccomp.gateways.TecnicoAssociacaoGateway;

/**
 * Servlet implementation class FiliarAssociacao
 */
@WebServlet("/FiliarAssociacao")
public class FiliarAssociacao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FiliarAssociacao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			request.getRequestDispatcher("associacao/FiliarAssociacao.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			validarLancamentoInformacoes(request);
			
			AssociacaoGateway associacaoGateway = new AssociacaoGateway();
			int matriculaGerada = associacaoGateway.inserir(request.getParameter("numeroOficio"), 
					request.getParameter("dataOficio"), 
					request.getParameter("nome"),
					request.getParameter("sigla"),
					request.getParameter("endereco"),
					request.getParameter("telefone"),
					request.getParameter("numeroComprovantePagamento"));
			
			TecnicoAssociacaoGateway tecnicoAssociacaoGateway = new TecnicoAssociacaoGateway();
			tecnicoAssociacaoGateway.inserir(matriculaGerada);
			
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (CampoObrigatorioException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		}
		
	}

	private void validarLancamentoInformacoes(HttpServletRequest request) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos!";
		try {
			Integer.parseInt(request.getParameter("numeroOficio"));
			Integer.parseInt(request.getParameter("numeroComprovantePagamento"));
		} catch (NumberFormatException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(request.getParameter("dataOficio"));
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (request.getParameter("nome") == null) throw new CampoObrigatorioException(msg);
		if (request.getParameter("sigla") == null) throw new CampoObrigatorioException(msg);
		if (request.getParameter("endereco") == null) throw new CampoObrigatorioException(msg);
		if (request.getParameter("telefone") == null) throw new CampoObrigatorioException(msg);
		
	}

}
