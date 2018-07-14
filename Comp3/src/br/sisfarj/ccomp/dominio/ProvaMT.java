package br.sisfarj.ccomp.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAtleta;
import br.sisfarj.ccomp.dominio.adapter.ResultSetProva;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaProvaException;

public class ProvaMT {
	
	private ResultSet rs;

	public ProvaMT(ResultSet rs) {
		this.rs = rs;
	}

	public enum NomeProva {
		L_50, L_100, L_200, L_400, L_800, L_1500, C_50, C_100, C_200, P_50, P_100,
		P_200, B_50, B_100, B_200, M_200, M_400;

		@Override
		public String toString() {
			String s[] =  super.toString().split("_");
			return s[1] + s[0];
		}
				
	}
	
	public enum Categoria {
		MASCULINO, FEMININO
	}
	
	public enum Classe {
		MIRIM, MIRIM_I_II, PETIZ_I_II, 
		INFANTIL_I_II, JUVENIL_I_II, JUNIOR_I_II, 
		SENIOR, MASTER, ABSOLUTO, INCULADO
	}

	public ResultSet inserir(Timestamp timestampCompeticao, String endereco, String nomeProva, String classe,
			String categoria) throws SQLException {
		
		this.rs.updateTimestamp("dataCompeticao", timestampCompeticao);
		this.rs.updateString("nomeProva", nomeProva);
		this.rs.updateString("classe", classe);
		this.rs.updateString("categoria", categoria);
		this.rs.updateString("endereco", endereco);
		
		return this.rs;
	}

	public ResultSetAdapter listarTudo() throws NaoHaProvaException, SQLException {
		if (!this.rs.next()) throw new NaoHaProvaException();
		this.rs.beforeFirst();
		return new ResultSetProva(this.rs);
	}
		
}
