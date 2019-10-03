package br.com.jpage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import br.com.jpage.beans.CoordenadasBean;
import br.com.jpage.beans.ItinerariosBean;
import br.com.jpage.util.HTTPConnect;

/**
 * Classe de requisição de informação oriunda de um Site.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* permitir a utilização de dados.
 * 
 *           Esta classe é responsável pela obtenção e disponibilização de
 *           informações oriundas do site de transportes da cidade de Porto
 *           Alegre.
 * 
 *           Os dados obtidos correspondem a informações sobre os itinerários de
 *           linhas circulação de transporte na cidade de Porto Alegre extraidos
 *           via requisição do tipo GET no seguinte endereço:
 *           http://www.poatransporte.com.br/php/facades/process.php?a=il&p=5566
 * 
 * @category HTTP, REQUEST, JSOM
 * 
 */
public class HTTPReqItinerarioTransPOA implements Serializable {
	private static final long serialVersionUID = 1L;

	private BufferedReader bufferedReader = null;
	private StringBuilder stringBuilder = null;
	private ArrayList<ItinerariosBean> itinerariosTransPOA;
	private ItinerariosBean itinerarioBean;

	private String URL;

	// Construtor
	public HTTPReqItinerarioTransPOA(String a, String p) {
		/**
		 * Seta o endereço para a requisição de lista de linhas do site:
		 * http://www.poatransporte.com.br
		 */
		this.URL = "http://www.poatransporte.com.br/php/facades/process.php?a=" + a + "&p=" + p;

		// Instancia objetos
		itinerariosTransPOA = new ArrayList<ItinerariosBean>();
		itinerarioBean = new ItinerariosBean();
	}

	/**
	 * Método responsável por obter informações via requisição do tipo GET da URL
	 * informada. Para que seja possível esta conexão deve se ter acesso a WEB.
	 * 
	 * @return Objeto ArrayList<LinhasBean> contenndo informações dos itinerários
	 *         das linhas da malha viárias da cidade de Porto Alegre.
	 * @throws IOException
	 */
	public ArrayList<ItinerariosBean> getURLItinerariosPOA() throws IOException {
		CoordenadasBean coordenadasBean;
		String strRequest;
		String strItinerarios;
		String[] itinerariosPOA;
		String[] itinerarioPOA;

		// Executa a leitura dos dados da URL
		bufferedReader = new BufferedReader(
				new InputStreamReader(HTTPConnect.getInstance().getConnection(URL).getInputStream()));
		stringBuilder = new StringBuilder();

		// Gera a lista do resultado da requisição
		itinerariosTransPOA = new ArrayList<ItinerariosBean>();

		while ((strRequest = bufferedReader.readLine()) != null) {
			stringBuilder.append(strRequest + '\n');
		}

		// Remove caracteres desnecessários
		strItinerarios = stringBuilder.toString().trim();

		strItinerarios = strItinerarios.replace("idlinha", "");
		strItinerarios = strItinerarios.replace("nome", "");
		strItinerarios = strItinerarios.replace("codigo", "");
		strItinerarios = strItinerarios.replace("lat", "");
		strItinerarios = strItinerarios.replace("lng", "");
		strItinerarios = strItinerarios.replace(":", "");
		strItinerarios = strItinerarios.replace("\"", "");
		strItinerarios = strItinerarios.replace("}", "");

		// Desmembra uma String e a converte em objeto do tipo JSOM
		itinerariosPOA = strItinerarios.split("\\{");

		// Criação de objeto de mainpulação
		itinerarioBean = new ItinerariosBean();

		for (int i = 1; i < itinerariosPOA.length; i++) {
			itinerarioPOA = itinerariosPOA[i].toString().split("\\,");

			// define a qual linha o itinerário pertence
			if (i == 1) {
				itinerarioBean.getLinhaBean().setIdLinha(Integer.parseInt(itinerarioPOA[0]));
				itinerarioBean.getLinhaBean().setCodigo(itinerarioPOA[1]);
				itinerarioBean.getLinhaBean().setNome(itinerarioPOA[2]);
			} else {
				coordenadasBean = new CoordenadasBean();

				coordenadasBean.setIdCordenada(i - 2);
				coordenadasBean.setLatitude(itinerarioPOA[0]);
				coordenadasBean.setLongitude(itinerarioPOA[1]);

				itinerarioBean.getCoordenadas().add(coordenadasBean);
			}
		}

		// Atribuição do objeto LinhasBean
		itinerariosTransPOA.add(itinerarioBean);

		return itinerariosTransPOA;
	}

}
