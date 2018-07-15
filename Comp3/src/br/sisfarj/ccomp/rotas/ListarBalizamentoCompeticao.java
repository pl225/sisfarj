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
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.dominio.AtletaProvaMT;
import br.sisfarj.ccomp.dominio.CompeticaoMT;
import br.sisfarj.ccomp.dominio.ProvaMT;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaCompeticaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaProvaException;
import br.sisfarj.ccomp.gateways.AtletaProvaGateway;
import br.sisfarj.ccomp.gateways.CompeticaoGateway;
import br.sisfarj.ccomp.gateways.CompeticaoProvaGateway;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;
import br.sisfarj.ccomp.gateways.exceptions.ProvaSemAtletaException;

/**
 * Servlet implementation class ListarBalizamentoCompeticao
 */
@WebServlet("/ListarBalizamentoCompeticao")
public class ListarBalizamentoCompeticao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarBalizamentoCompeticao() {
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
			
			if (request.getParameter("nome") != null && request.getParameter("classe") != null && 
					request.getParameter("categoria") != null) {
				
				
				rs = new AtletaProvaGateway().buscarAtletasProvaTempo(request.getParameter("nome"),
						                                         request.getParameter("classe"), 
						                                         request.getParameter("categoria"), 
						                                         request.getParameter("dataCompeticao"), 
						                                         request.getParameter("endereco"));
				
				AtletaProvaMT atletaProvaMT= new AtletaProvaMT(rs);
				ResultSetAdapter rsA = atletaProvaMT.listarBalizamento();
				
				request.setAttribute("dados", rsA);
				request.getRequestDispatcher("balizamento/ListarPontuacaoCompeticaoProva.jsp").forward(request, response);
				
			} else if (request.getParameter("dataCompeticao") != null && request.getParameter("endereco") != null) {
				
				rs = competicaoGateway.buscar(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				CompeticaoMT competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.getCompeticao(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				
				//rsProvas = cpg.getProvasPelaCompeticao(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				
				rsProvas = competicaoMT.getProvas(request.getParameter("dataCompeticao"), request.getParameter("endereco"));
				ProvaMT provaMT = new ProvaMT(rsProvas);
				ResultSetAdapter rsaProvas = provaMT.listarTudo();
				
				request.setAttribute("dados", rsaProvas);
				request.setAttribute("dadosCompeticao", rsa);
				request.getRequestDispatcher("balizamento/ListarCompeticao.jsp").forward(request, response);
			
			} else {
				rs = competicaoGateway.listarTodas();
				CompeticaoMT competicaoMT = new CompeticaoMT(rs);
				ResultSetAdapter rsa = competicaoMT.listarTodas();
				
				request.setAttribute("dados", rsa);
				request.getRequestDispatcher("balizamento/ListarBalizamentoCompeticao.jsp").forward(request, response);	
			}
			
			
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("Menu").forward(request, response);
		} catch (SQLException | ParseException e) {
			response.getWriter().println(e.getMessage());

		} catch (CompeticaoNaoEncontradaException | ProvaSemAtletaException | NaoHaCompeticaoException | NaoHaProvaException | NaoHaAtletaException e) {
			request.setAttribute(Constantes.ERRO, e.getMessage());
			e.printStackTrace();
			request.getRequestDispatcher("Menu").forward(request, response);
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
