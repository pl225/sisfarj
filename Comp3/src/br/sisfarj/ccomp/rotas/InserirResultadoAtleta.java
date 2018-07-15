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
import br.sisfarj.ccomp.dominio.exceptions.AtletasJaTemporizadosException;
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
@WebServlet("/InserirResultadoAtleta")
public class InserirResultadoAtleta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserirResultadoAtleta() {
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
			
			if (request.getParameter("matriculaAtleta") != null) {
				
				request.setAttribute("matriculaAtleta", request.getParameter("matriculaAtleta"));
				request.getRequestDispatcher("inserirResultadoAtleta/InserirResultadoAtleta.jsp").forward(request, response);
				
			} else if (request.getParameter("nomeProva") != null && request.getParameter("classe") != null && 
					request.getParameter("categoria") != null) {
				
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("nomeProva", request.getParameter("nomeProva"));
				httpSession.setAttribute("endereco", request.getParameter("endereco"));
				httpSession.setAttribute("classe", request.getParameter("classe"));
				httpSession.setAttribute("categoria", request.getParameter("categoria"));
				httpSession.setAttribute("dataCompeticao", request.getParameter("dataCompeticao"));
				httpSession.setAttribute("tipoPiscina", request.getParameter("tipoPiscina"));
				
				rs = new AtletaProvaGateway().buscarAtletasProvaSemTempo(request.getParameter("nomeProva"),
               		  request.getParameter("classe"), 
               		  request.getParameter("categoria"), 
               		  request.getParameter("dataCompeticao"), 
               		  request.getParameter("endereco"));

				AtletaProvaMT atletaProvaMT= new AtletaProvaMT(rs);
				ResultSetAdapter rsA = atletaProvaMT.mostrarAtletasSemTempo();

				request.setAttribute("dados", rsA);			
				request.getRequestDispatcher("inserirResultadoAtleta/ListarAtletasProva.jsp").forward(request, response);
				
			} else if (request.getParameter("dataCompeticao") != null && request.getParameter("endereco") != null) {
				
				rs = competicaoGateway.buscar(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				CompeticaoMT competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.getCompeticao(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				
				
				rsProvas = competicaoMT.getProvas(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				ProvaMT provaMT = new ProvaMT(rsProvas);
				ResultSetAdapter rsaProvas = provaMT.listarTudo();
				
				request.setAttribute("dados", rsaProvas);
				request.setAttribute("dadosCompeticao", rsa);
				request.getRequestDispatcher("inserirResultadoAtleta/ListarProvaCompeticao.jsp").forward(request, response);
			
			} else {
				rs = competicaoGateway.listarTodas();
				CompeticaoMT competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.listarTodas();
				
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("inserirResultadoAtleta/ListarCompeticao.jsp").forward(request, response);	
			}
			
			
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());
		} catch (CompeticaoNaoEncontradaException | NaoHaCompeticaoException | NaoHaProvaException | AtletasJaTemporizadosException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("Menu").forward(request, response);
		} 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(request.getParameter("botao").equals("Adicionar")) {
				
				AtletaGateway atletaGateway = new AtletaGateway();
				
				System.out.println(request.getParameter("tempo"));
				
				if(request.getParameter("tempo") != null && !request.getParameter("tempo").isEmpty()) {
					
					HttpSession httpSession = request.getSession();
					
					AtletaProvaGateway atletaProvaGateway = new AtletaProvaGateway();
					
					ResultSet rs = atletaProvaGateway.buscarAtletaProvaSemTempo(request.getParameter("matriculaAtleta"), (String) httpSession.getAttribute("nomeProva"),
							(String) httpSession.getAttribute("endereco"), (String) httpSession.getAttribute("classe"),
							(String) httpSession.getAttribute("categoria"), (String) httpSession.getAttribute("dataCompeticao"), 
							(String) httpSession.getAttribute("tipoPiscina"));
					
					AtletaProvaMT atletaProvaMT = new AtletaProvaMT(rs);
					
					rs = atletaProvaMT.atualizarTempo(request.getParameter("tempo"));
					atletaProvaGateway.atualizar(rs);
					
					verificarFimAtualizacaoTempo(httpSession, request, response, false);

				}
				else {
					request.setAttribute(Constantes.ERRO, "Preencha os Campos em Branco!");
					request.setAttribute("matriculaAtleta", request.getParameter("matriculaAtleta"));
					request.getRequestDispatcher("inserirResultadoAtleta/InserirResultadoAtleta.jsp").forward(request, response);
				}
			}
			else if (request.getParameter("botao").equals("Finalizar")) {
				verificarFimAtualizacaoTempo(request.getSession(), request, response, true);
			}
		} catch (SQLException | ParseException | NumberFormatException e) {
			e.printStackTrace(response.getWriter()); 
		} catch (AtletasJaTemporizadosException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (CampoObrigatorioException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			request.setAttribute("matriculaAtleta", request.getParameter("matriculaAtleta"));
			request.getRequestDispatcher("inserirResultadoAtleta/InserirResultadoAtleta.jsp").forward(request, response);
		} 
	}
    
    private void verificarFimAtualizacaoTempo (HttpSession httpSession, HttpServletRequest request, HttpServletResponse response, boolean finalizar) throws SQLException, ParseException, ServletException, IOException {
    	AtletaProvaMT atletaProvaMT;
    	AtletaProvaGateway atletaProvaGateway = new AtletaProvaGateway();
    	ResultSet rs;
    	
    	rs = atletaProvaGateway.buscarAtletasProvaSemTempo(
				(String) httpSession.getAttribute("nomeProva"),
				(String) httpSession.getAttribute("classe"), 
				(String) httpSession.getAttribute("categoria"), 
				(String) httpSession.getAttribute("dataCompeticao"),
				(String) httpSession.getAttribute("endereco"));
		
		atletaProvaMT = new AtletaProvaMT(rs);
		try {
			ResultSetAdapter rsA = atletaProvaMT.mostrarAtletasSemTempo();
			if (finalizar == true) request.setAttribute(Constantes.ERRO, "Ainda h� atletas a serem temporizados.");
			request.setAttribute("dados", rsA);			
			request.getRequestDispatcher("inserirResultadoAtleta/ListarAtletasProva.jsp").forward(request, response);
		} catch (AtletasJaTemporizadosException e) {
			request.getRequestDispatcher("Menu").forward(request, response);
		}
    }
}