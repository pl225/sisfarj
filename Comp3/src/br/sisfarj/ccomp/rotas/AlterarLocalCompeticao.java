package br.sisfarj.ccomp.rotas;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			ResultSet rs;
			
			if (request.getParameter("endereco") != null) {
				rs = lcg.buscar(request.getParameter("endereco"));
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("localCompeticao/EditarLocalCompeticao.jsp").forward(request, response);
			} else {			
				rs = lcg.listarTudo();
				request.setAttribute("dados", rs);
				request.getRequestDispatcher("localCompeticao/AlterarLocalCompeticao.jsp").forward(request, response);
			}
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		} catch (SQLException | LocalNaoEncontradoException e) {
			response.getWriter().println(e.getMessage());
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
			e.printStackTrace();
		}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			validarLancamentoInformacoes(request);
			LocalCompeticaoGateway lcg = new LocalCompeticaoGateway();
			char p25, p50;
			
			if(request.getParameter("piscina25") == null) p25 = ConstantesPiscina.FALSE.getValor(); else p25 = ConstantesPiscina.TRUE.getValor();
			if(request.getParameter("piscina50") == null) p50 = ConstantesPiscina.FALSE.getValor(); else p50 = ConstantesPiscina.TRUE.getValor(); 
			
			lcg.atualizar(request.getParameter("endereco"),request.getParameter("nome"), request.getParameter("novoEndereco"), p25, p50);
			
			request.getRequestDispatcher("WEB-INF/Menu.jsp").forward(request, response);
			
		} catch (CampoObrigatorioException e) {
			// TODO Auto-generated catch block
			request.setAttribute(Constantes.ERRO, e.getMessage());
			doGet(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void validarLancamentoInformacoes(HttpServletRequest request) throws CampoObrigatorioException {
		// TODO Auto-generated method stub
		String msg = "Preencha todos os campos!";
		String msgPiscina = "Escolha pelo menos um tipo de piscina!";
		
		if (request.getParameter("nome") == null) throw new CampoObrigatorioException(msg);
		if (request.getParameter("endereco") == null) throw new CampoObrigatorioException(msg);
		if (request.getParameter("piscina25") == null && request.getParameter("piscina50") == null) throw new CampoObrigatorioException(msgPiscina);
		
		
	}

}
