package br.com.jpage.beans;

import java.io.Serializable;

import br.com.jpage.CRUD.DadosDAO;
import br.com.jpage.CRUD.ObjetoDAO;
import br.com.jpage.interfaces.InterfaceObjeto;

/**
 * Classe de objeto do tipo Linha.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* instanciar uma linha de transporte.
 * 
 * @category BEAN
 * 
 */
public class LinhasBean extends ObjetoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idLinha;
	private String nome;
	private String codigo;
	private String tipo;

	private ObjetoDAO objetoDAO;

	// Construtor
	public LinhasBean() {
		objetoDAO = new ObjetoDAO("linhas");

		adicionarCampos();
	}

	/**
	 * Adiciona os campos da tabela a uma lista para facilitar a manipulação
	 */
	@Override
	public void adicionarCampos() {
		removerCampos();

		getObjetoDAO().addCampo(getDadosCodigo());
		getObjetoDAO().addCampo(new DadosDAO("codigo", "Código", codigo, DadosDAO.TIPO_STRING, DadosDAO.NAO_CHAVE));
		getObjetoDAO().addCampo(getDadosDescricao());
		getObjetoDAO().addCampo(new DadosDAO("tipo", "Tipo", tipo, DadosDAO.TIPO_STRING, DadosDAO.NAO_CHAVE));
	}

	/**
	 * Serve para padronizar o campo que será reconhecido como o identificador
	 */
	@Override
	public DadosDAO getDadosCodigo() {
		return new DadosDAO("id_linha", "ID Linha", String.valueOf(idLinha), DadosDAO.TIPO_INTEGER, DadosDAO.IS_CHAVE);
	}

	/**
	 * Serve para definir o campo que será mostrado para o usuário em caso de
	 * pesquisa
	 */
	@Override
	public DadosDAO getDadosDescricao() {
		return new DadosDAO("nome", "Descrição Linha", nome, DadosDAO.TIPO_STRING, DadosDAO.NAO_CHAVE);
	}

	@Override
	public InterfaceObjeto getObjeto() {
		return this;
	}

	/**
	 * @return the id
	 */
	public int getIdLinha() {
		return idLinha;
	}

	/**
	 * @param id the id to set
	 */
	public void setIdLinha(int idLinha) {
		this.idLinha = idLinha;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the objetoDAO
	 */
	public ObjetoDAO getObjetoDAO() {
		return objetoDAO;
	}

	/**
	 * @param objetoDAO the objetoDAO to set
	 */
	public void setObjetoDAO(ObjetoDAO objetoDAO) {
		this.objetoDAO = objetoDAO;
	}

}
