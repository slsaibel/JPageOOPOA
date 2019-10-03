package br.com.jpage.CRUD;

import java.io.Serializable;

/**
 * Classe de tipagem de dados para banco.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* permitir a padronização de dados antes da
 *           inserção no banco.
 * 
 *           Esta classe é responsável pela definição de como o dado será
 *           manipulado para iterações com o banco.
 * 
 * @category TYPE, DAO, DB, CRUD
 * 
 */
public class DadosDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String campo;
	private String descricao;
	private String valor;
	private int tipo;
	private boolean isChave;

	// Constantes para tipo de dado
	public static int TIPO_INTEGER = 0;
	public static int TIPO_STRING = 1;
	public static int TIPO_DATE = 2;
	public static int TIPO_DOUBLE = 3;
	public static int TIPO_BOOLEAN = 4;

	// Constantes para tipo de campo
	public static boolean IS_CHAVE = true;
	public static boolean NAO_CHAVE = false;

	// Construtor
	public DadosDAO() {
	}

	// Construtor
	public DadosDAO(DadosDAO dadoDAO) {
		campo = dadoDAO.campo;
		descricao = dadoDAO.descricao;
		tipo = dadoDAO.tipo;
		valor = dadoDAO.valor;
		isChave = dadoDAO.isChave;
	}

	public DadosDAO(String campo, String descricao, String valor, int tipo, boolean isChave) {
		this.campo = campo;
		this.descricao = descricao;
		this.tipo = tipo;
		this.valor = valor;
		this.isChave = isChave;
	}

	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * @param campo the campo to set
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the isChave
	 */
	public boolean isChave() {
		return isChave;
	}

	/**
	 * @param isChave the isChave to set
	 */
	public void setChave(boolean isChave) {
		this.isChave = isChave;
	}

}
