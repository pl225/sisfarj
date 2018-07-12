package br.sisfarj.ccomp.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.sisfarj.ccomp.gateways.CompeticaoProvaGateway;
import br.sisfarj.ccomp.gateways.exceptions.CompeticaoNaoEncontradaException;

public class CompeticaoMT {

	private ResultSet rs;

	public CompeticaoMT(ResultSet rs) {
		this.rs = rs;
	}
	
	public ResultSet getProvas(String dataCompeticao, String endereco) throws SQLException, CompeticaoNaoEncontradaException {
		
		this.rs.beforeFirst();
		while (rs.next()) {
			if (rs.getString("dataCompeticao").equals(dataCompeticao) && rs.getString(endereco).equals(endereco)) {
				CompeticaoProvaGateway competicaoProvaGateway = new CompeticaoProvaGateway();
				ResultSet rs = competicaoProvaGateway.getProvasPelaCompeticao(dataCompeticao, endereco);
				//CompeticaoProvaMT competicaoProvaMT = new CompeticaoProvaMT(rs);
				//return competicaoProvaMT.getProvasDaCompeticao(dataCompeticao, endereco);
				return rs;
			}
		}
		throw new CompeticaoNaoEncontradaException();
		
		
		
	}

	private ResultSet competicaoProvaMT(String dataCompeticao, String endereco) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
