package br.com.jpage.beans;

import java.io.Serializable;

import br.com.jpage.CRUD.DadosDAO;
import br.com.jpage.CRUD.ObjetoDAO;
import br.com.jpage.interfaces.InterfaceObjeto;

/**
 * Classe de objeto do tipo Coordenadas.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* instanciar uma coordenada através da latitude
 *           e longitude.
 * 
 * @category BEAN
 * 
 */
public class CoordenadasBean extends ObjetoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idCoordenada;
	private String latitude;
	private String longitude;

	private ObjetoDAO objetoDAO;

	// Construtor
	public CoordenadasBean() {
		objetoDAO = new ObjetoDAO("coordenadas");

		adicionarCampos();
	}

	/**
	 * Adiciona os campos da tabela a uma lista para facilitar a manipulação
	 */
	@Override
	public void adicionarCampos() {
		removerCampos();

		getObjetoDAO().addCampo(getDadosCodigo());
		getObjetoDAO()
				.addCampo(new DadosDAO("latituda", "Latituda", latitude, DadosDAO.TIPO_STRING, DadosDAO.NAO_CHAVE));
		getObjetoDAO()
				.addCampo(new DadosDAO("longitude", "Longitude", longitude, DadosDAO.TIPO_STRING, DadosDAO.NAO_CHAVE));
	}

	/**
	 * Serve para padronizar o campo que será reconhecido como o identificador
	 */
	@Override
	public DadosDAO getDadosCodigo() {
		return new DadosDAO("id_coordenada", "ID Coordenada", String.valueOf(idCoordenada), DadosDAO.TIPO_INTEGER,
				DadosDAO.IS_CHAVE);
	}

	/**
	 * Serve para definir o campo que será mostrado para o usuário em caso de
	 * pesquisa
	 */
	@Override
	public DadosDAO getDadosDescricao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InterfaceObjeto getObjeto() {
		return this;
	}

	/**
	 * @return the id
	 */
	public int getIdcordenada() {
		return idCoordenada;
	}

	/**
	 * @param id the id to set
	 */
	public void setIdCordenada(int idCoordenada) {
		this.idCoordenada = idCoordenada;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param lat the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param lng the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
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
