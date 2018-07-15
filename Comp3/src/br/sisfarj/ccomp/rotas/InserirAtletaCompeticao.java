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
import javax.servlet.http.HttpSession;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.aplicacao.VerificarIdentificacaoUsuario;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.dominio.AtletaMT;
import br.sisfarj.ccomp.dominio.AtletaProvaMT;
import br.sisfarj.ccomp.dominio.CompeticaoMT;
import br.sisfarj.ccomp.dominio.ProvaMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.AtletaJaInscritoProvaException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaProvaException;
import br.sisfarj.ccomp.gateways.AtletaGateway;
import br.sisfarj.ccomp.gateways.AtletaProvaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;
import br.sisfarj.ccomp.gateways.CompeticaoProvaGateway;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;
import br.sisfarj.ccomp.gateways.exceptions.AssociacaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.AtletaNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.LocalNaoEncontradoException;
import br.sisfarj.ccomp.gateways.exceptions.ProvaSemAtletaException;

/**
 * Servlet implementation class ListarCompeticao
 */
@WebServlet("/InserirAtletaCompeticao")
public class InserirAtletaCompeticao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserirAtletaCompeticao() {
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
			
			if (request.getParameter("nomeProva") != null && request.getParameter("classe") != null && 
					request.getParameter("categoria") != null) {
				
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("nomeProva", request.getParameter("nomeProva"));
				httpSession.setAttribute("endereco", request.getParameter("endereco"));
				httpSession.setAttribute("classe", request.getParameter("classe"));
				httpSession.setAttribute("categoria", request.getParameter("categoria"));
				httpSession.setAttribute("dataCompeticao", request.getParameter("dataCompeticao"));
				httpSession.setAttribute("tipoPiscina", request.getParameter("tipoPiscina"));
				
				
				request.getRequestDispatcher("competicao/InserirAtletaCompeticao.jsp").forward(request, response);
				
			} else if (request.getParameter("dataCompeticao") != null && request.getParameter("endereco") != null) {
				
				rs = competicaoGateway.buscar(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				CompeticaoMT competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.getCompeticao(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				
				
				rsProvas = competicaoMT.getProvas(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				ProvaMT provaMT = new ProvaMT(rsProvas);
				ResultSetAdapter rsaProvas = provaMT.listarTudo();
				
				request.setAttribute("dados", rsaProvas);
				request.setAttribute("dadosCompeticao", rsa);
				request.getRequestDispatcher("competicao/ListarProvaCompeticaoAtleta.jsp").forward(request, response);
			
			} else {
				rs = competicaoGateway.listarTodas();
				CompeticaoMT competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.listarTodas();
				
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("competicao/ListarCompeticaoAtleta.jsp").forward(request, response);	
			}
			
			
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		} catch (CompeticaoNaoEncontradaException | NaoHaCompeticaoException | NaoHaProvaException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("Menu").forward(request, response);
		} 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(request.getParameter("botao").equals("Inscrever")) {
				
				AtletaGateway atletaGateway = new AtletaGateway();
				
				if(request.getParameter("numero") != null && !request.getParameter("numero").isEmpty()) {
					
					HttpSession httpSession = request.getSession();
					ResultSet rs = atletaGateway.buscarMatricula(request.getParameter("numero"));
					
					AtletaMT atletaMT = new AtletaMT(rs);
					atletaMT.getMatricula(Integer.parseInt(request.getParameter("numero")));
					
					AtletaProvaGateway atletaProvaGateway = new AtletaProvaGateway();
					
					rs = atletaProvaGateway.buscarAtletaProva(request.getParameter("numero"), (String) httpSession.getAttribute("nomeProva"),
							(String) httpSession.getAttribute("endereco"), (String) httpSession.getAttribute("classe"),
							(String) httpSession.getAttribute("categoria"), (String) httpSession.getAttribute("dataCompeticao"), 
							(String) httpSession.getAttribute("tipoPiscina"));
					
					AtletaProvaMT atletaProvaMT = new AtletaProvaMT(rs);
					
					rs = atletaProvaMT.inserirAtleta(request.getParameter("numero"), (String) httpSession.getAttribute("nomeProva"),
							(String) httpSession.getAttribute("endereco"), (String) httpSession.getAttribute("classe"),
							(String) httpSession.getAttribute("categoria"), (String) httpSession.getAttribute("dataCompeticao"), 
							(String) httpSession.getAttribute("tipoPiscina"));
					
					atletaProvaGateway.inserir(rs);
					request.getRequestDispatcher("competicao/InserirAtletaCompeticao.jsp").forward(request, response);
				}
				else {
					request.setAttribute(Constantes.ERRO, "Preencha os Campos em Branco!");
					request.getRequestDispatcher("competicao/InserirAtletaCompeticao.jsp").forward(request, response);
				}
			}
			else if (request.getParameter("botao").equals("Finalizar")) {
				request.getRequestDispatcher("Menu").forward(request, response);
			}
		} catch (SQLException | ParseException | NumberFormatException e) {
			e.printStackTrace(response.getWriter());
			//response.getWriter().println(e.getMessage()); 
		} catch (AtletaJaInscritoProvaException | AtletaNaoEncontradoException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("competicao/InserirAtletaCompeticao.jsp").forward(request, response);
		}
	}

}
