package br.com.jpage.beans;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.jpage.CRUD.DadosDAO;
import br.com.jpage.CRUD.ObjetoDAO;
import br.com.jpage.interfaces.InterfaceObjeto;

/**
 * Classe de objeto do tipo Itinerário.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* instanciar um itinerário de transporte.
 * 
 * @category BEAN
 * 
 */
public class ItinerariosBean extends ObjetoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idItinerario;
	private LinhasBean linhaBean;
	private ArrayList<CoordenadasBean> coordenadas;

	private ObjetoDAO objetoDAO;

	// Construtor
	public ItinerariosBean() {
		linhaBean = new LinhasBean();
		coordenadas = new ArrayList<CoordenadasBean>();

		objetoDAO = new ObjetoDAO("itinerarios");

		adicionarCampos();
	}

	/**
	 * Adiciona os campos da tabela a uma lista para facilitar a manipulação
	 */
	@Override
	public void adicionarCampos() {
		removerCampos();

		getObjetoDAO().addCampo(getDadosCodigo());
	}

	/**
	 * Serve para padronizar o campo que será reconhecido como o identificador
	 */
	@Override
	public DadosDAO getDadosCodigo() {
		return new DadosDAO("id_itinerario", "ID Itinerário", String.valueOf(idItinerario), DadosDAO.TIPO_INTEGER,
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
	 * @return the idItinerario
	 */
	public int getIdItinerario() {
		return idItinerario;
	}

	/**
	 * @param id_itinerario the id_itinerario to set
	 */
	public void setIdItinerario(int idItinerario) {
		this.idItinerario = idItinerario;
	}

	/**
	 * @return the linhaBean
	 */
	public LinhasBean getLinhaBean() {
		return linhaBean;
	}

	/**
	 * @param linhaBean the linhaBean to set
	 */
	public void setLinhaBean(LinhasBean linhaBean) {
		this.linhaBean = linhaBean;
	}

	/**
	 * @return the coordenadas
	 */
	public ArrayList<CoordenadasBean> getCoordenadas() {
		return coordenadas;
	}

	/**
	 * @param coordenadas the coordenadas to set
	 */
	public void setCoordenadas(ArrayList<CoordenadasBean> coordenadas) {
		this.coordenadas = coordenadas;
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
