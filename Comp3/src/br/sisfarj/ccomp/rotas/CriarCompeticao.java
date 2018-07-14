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
import br.sisfarj.ccomp.dominio.CompeticaoMT;
import br.sisfarj.ccomp.dominio.LocalCompeticaoMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.CompeticaoJaExisteException;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;

/**
 * Servlet implementation class CriarCompeticao
 */
@WebServlet("/CriarCompeticao")
public class CriarCompeticao extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NOME_COMPETICAO = "nomeCompeticao";
	private static final String DATA_COMPETICAO = "dataCompeticao";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CriarCompeticao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int matricula = VerificarIdentificacaoUsuario.verificarAutenticacao(request);
			request.getRequestDispatcher("competicao/CriarCompeticao_passo3.jsp").forward(request, response);
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
			
			if (request.getParameter("passo3") != null  || request.getAttribute("passo3") != null) {
				try {
					validarPasso3(request);
					
					LocalCompeticaoGateway lcg = new LocalCompeticaoGateway();
					ResultSet rs = lcg.listarTudo();
					LocalCompeticaoMT localCompeticaoMT = new LocalCompeticaoMT(rs);
					ResultSetAdapter rsa = localCompeticaoMT.listarTudo();
					
					request.setAttribute("dados", rsa);
					request.getSession().setAttribute(NOME_COMPETICAO, request.getParameter("nome"));
					request.getSession().setAttribute(DATA_COMPETICAO, request.getParameter("dataCompeticao"));
					
					request.getRequestDispatcher("competicao/CriarCompeticao_passo5.jsp").forward(request, response);
				} catch (CampoObrigatorioException e) {
					request.setAttribute(Constantes.ERRO, e.getMessage());
					doGet(request, response);
				} catch (LocalNaoEncontradoException e) {
					request.setAttribute(Constantes.ERRO, e.getMessage());
					request.getRequestDispatcher("Menu").forward(request, response);
				}
			} else if (request.getParameter("passo5") != null) {
				try {
					
					String infoLocal [] = request.getParameter("LocaisDeProva") != null 
							&& !"".equals(request.getParameter("LocaisDeProva")) ? 
							request.getParameter("LocaisDeProva").split("&") : null;
					String local = infoLocal != null ? infoLocal[0] : null;
					String tipoPiscina = infoLocal != null ? infoLocal[1] : null;
					
					CompeticaoGateway competicaoGateway = new CompeticaoGateway();
					ResultSet rs = competicaoGateway.buscar(
							(String) request.getSession().getAttribute(DATA_COMPETICAO),
							local
					);
					CompeticaoMT competicaoMT = new CompeticaoMT(rs);
					competicaoMT.inserir(
							(String) request.getSession().getAttribute(NOME_COMPETICAO),
							(String) request.getSession().getAttribute(DATA_COMPETICAO),
							local,
							tipoPiscina,
							request.getParameterValues("nomesProva"),
							request.getParameterValues("classes"),
							request.getParameterValues("categorias")
					);
					
					request.getRequestDispatcher("Menu").forward(request, response);
					
				} catch (CampoObrigatorioException e) {
					request.setAttribute(Constantes.ERRO, e.getMessage());
					request.setAttribute("passo3", "passo3");
					doPost(request, response);
				} catch (CompeticaoJaExisteException e) {
					request.setAttribute(Constantes.ERRO, e.getMessage());
					request.getRequestDispatcher("Menu").forward(request, response);
				}
			}
			
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException | ParseException e) {
			e.printStackTrace(response.getWriter());
			response.getWriter().println(e.getMessage());
		}
	}

	private void validarPasso3(HttpServletRequest request) throws CampoObrigatorioException {
		String msg = "Preencha todos os campos.";
		try {
			new SimpleDateFormat(Constantes.FORMATO_DATA).parse(request.getParameter("dataCompeticao"));
		} catch (ParseException | NullPointerException e) {
			throw new CampoObrigatorioException(msg);
		}
		
		if (request.getParameter("nome") == null) throw new CampoObrigatorioException(msg);
		
	}

}
