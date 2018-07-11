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
import br.sisfarj.ccomp.aplicacao.ConstantesPiscina;
import br.sisfarj.ccomp.aplicacao.VerificarIdentificacaoUsuario;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.aplicacao.exceptions.UsuarioNaoIdentificadoException;
import br.sisfarj.ccomp.gateways.LocalCompeticaoGateway;

/**
 * Servlet implementation class IncluirLocalCompeticao
 */
@WebServlet("/IncluirLocalCompeticao")
public class IncluirLocalCompeticao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IncluirLocalCompeticao() {
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
			request.getRequestDispatcher("localCompeticao/IncluirLocalCompeticao.jsp").forward(request, response);
		} catch (UsuarioNaoIdentificadoException e) {
			request.setAttribute(Constantes.ERRO, "Usuário não identificado!");
			request.getRequestDispatcher("IdentificarUsuario").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			
			validarLancamentoInformacoes(request);
			LocalCompeticaoGateway lcg = new LocalCompeticaoGateway();
			char p25, p50;
			
			if(request.getParameter("piscina25") == null) p25 = ConstantesPiscina.FALSE.getValor(); else p25 = ConstantesPiscina.TRUE.getValor();
			if(request.getParameter("piscina50") == null) p50 = ConstantesPiscina.FALSE.getValor(); else p50 = ConstantesPiscina.TRUE.getValor(); 
			
			System.out.println(p25);
			System.out.println(p50);
			
			lcg.inserir(request.getParameter("nome"), request.getParameter("endereco"), p25, p50);
			
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
