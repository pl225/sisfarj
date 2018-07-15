package br.sisfarj.ccomp.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import br.sisfarj.ccomp.aplicacao.Constantes;
import br.sisfarj.ccomp.aplicacao.exceptions.CampoObrigatorioException;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAtleta;
import br.sisfarj.ccomp.dominio.adapter.ResultSetAtletaProva;
import br.sisfarj.ccomp.dominio.adapter.ResultSetBalizamento;
import br.sisfarj.ccomp.dominio.adapter.ResultSetProva;
import br.sisfarj.ccomp.dominio.exceptions.AtletaJaInscritoProvaException;
import br.sisfarj.ccomp.dominio.exceptions.AtletasJaTemporizadosException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaAtletaException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaPontuacaoException;
import br.sisfarj.ccomp.dominio.exceptions.NaoHaProvaException;

public class AtletaProvaMT {

	private ResultSet rs;

	public AtletaProvaMT(ResultSet rs) {
		this.rs = rs;
	}
	
	public ResultSetAdapter mostrarAtletasSemTempo() throws SQLException, AtletasJaTemporizadosException {
		if (!this.rs.next()) throw new AtletasJaTemporizadosException();
		this.rs.beforeFirst();
		return new ResultSetAtletaProva(this.rs);
	}

	public ResultSetAdapter listarTudo() throws NaoHaAtletaException, SQLException {
		if (!this.rs.next()) throw new NaoHaAtletaException();
		this.rs.beforeFirst();
		return new ResultSetAtletaProva(this.rs);
	}
	
	public ResultSetAdapter listarBalizamento() throws NaoHaAtletaException, SQLException {
		if (!this.rs.next()) throw new NaoHaAtletaException();
		this.rs.beforeFirst();
		return new ResultSetBalizamento(this.rs);
	}

	public ResultSetAdapter listarPontuacaoFinal() throws SQLException, NaoHaPontuacaoException {
		if (!this.rs.next()) throw new NaoHaPontuacaoException();
		this.rs.beforeFirst();
		return new ResultSetAtletaProva(this.rs);
	}
	
	public ResultSetAdapter listarPontuacao() throws SQLException, NaoHaPontuacaoException {
		if (!this.rs.next()) throw new NaoHaPontuacaoException();
		this.rs.beforeFirst();
		return new ResultSetAtletaProva(this.rs);
	}
	
	public ResultSet inserirAtleta(String numero, String nome, String endereco, String classe,
			String categoria, String dataCompeticao, String tipoPiscina) throws SQLException, AtletaJaInscritoProvaException, ParseException {
		
		if (this.rs.next()) {
			throw new AtletaJaInscritoProvaException();
		}
		
		this.rs.moveToInsertRow();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_DATA);
		
		this.rs.updateInt("MATRICULAATLETA", Integer.parseInt(numero));
		this.rs.updateString("NOMEPROVA", nome);
		this.rs.updateString("TIPOPISCINA", tipoPiscina);
		this.rs.updateString("CLASSE", classe);
		this.rs.updateString("CATEGORIA", categoria);
		this.rs.updateString("DATACOMPETICAO", new Timestamp(simpleDateFormat.parse(dataCompeticao).getTime()).toString());
		this.rs.updateString("ENDERECO", endereco);
		
		
		return rs;
		
	}
	
	public ResultSet atualizarTempo(String tempo) throws SQLException, ParseException, AtletasJaTemporizadosException, CampoObrigatorioException {
		
		if (!this.rs.next()) throw new AtletasJaTemporizadosException();
		
		validar(tempo);
		
		Time t = Time.valueOf(tempo);
		
		this.rs.updateTime("TEMPO", t);
		this.rs.updateInt("PONTUACAO", new Random().nextInt(10));		
		
		return rs;
		
	}

	private void validar(String tempo) throws CampoObrigatorioException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
		try {
			simpleDateFormat.parse(tempo);
		} catch (ParseException e) {
			throw new CampoObrigatorioException("Tempo em branco ou está no formato inválido.");
		}
		
	}
}
