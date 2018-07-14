package br.sisfarj.ccomp.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAtleta;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAtletaProva;
import br.sisfarj.ccomp.dominio.adapter.ResultSetProva;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaProvaException;

public class AtletaProvaMT {

	private ResultSet rs;

	public AtletaProvaMT(ResultSet rs) {
		this.rs = rs;
	}

	public ResultSetAdapter listarTudo() throws NaoHaAtletaException, SQLException {
		if (!this.rs.next()) throw new NaoHaAtletaException();
		this.rs.beforeFirst();
		return new ResultSetAtletaProva(this.rs);
	}
}
