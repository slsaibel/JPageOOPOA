package br.com.jpage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import br.com.jpage.beans.LinhasBean;
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
 *           Os dados obtidos correspondem a informações sobre as linhas de
 *           circulação de transporte na cidade de Porto Alegre extraidos via
 *           requisição do tipo GET no seguinte endereço:
 *           http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o
 * 
 * @category HTTP, REQUEST, JSOM
 * 
 */
public class HTTPReqLinhasTransPOA implements Serializable {
	private static final long serialVersionUID = 1L;

	private BufferedReader bufferedReader = null;
	private StringBuilder stringBuilder = null;
	private ArrayList<LinhasBean> linhasTransPOA;
	private LinhasBean linhaBean;

	private String URL;

	// Construtor
	public HTTPReqLinhasTransPOA(String a, String t) {
		/**
		 * Se o parâmetro a for a=nc e o parâmetro t for t="" então seta para "O"
		 */
		if (a.equals("nc")) {
			if (t.equals("")) {
				t = "o";
			}
		}

		/**
		 * Seta o endereço para a requisição de lista de linhas do site:
		 * http://www.poatransporte.com.br
		 */
		this.URL = "http://www.poatransporte.com.br/php/facades/process.php?a=" + a + "&p=%&t=" + t;

		// Insancia objetos
		linhasTransPOA = new ArrayList<LinhasBean>();
		linhaBean = new LinhasBean();
	}

	// Construtor
	public HTTPReqLinhasTransPOA(String a, String p, String t) {
		/**
		 * Se o parâmetro a for a=nc e o parâmetro t for t="" então seta para "O"
		 */
		if (a.equals("nc")) {
			if (t.equals("")) {
				t = "o";
			}
		}

		/**
		 * Seta o endereço para a requisição de lista de linhas do site:
		 * http://www.poatransporte.com.br
		 */
		this.URL = "http://www.poatransporte.com.br/php/facades/process.php?a=" + a + "&p=" + p + "&t=" + t;

		// Insancia objetos
		linhasTransPOA = new ArrayList<LinhasBean>();
		linhaBean = new LinhasBean();
	}

	/**
	 * Método responsável por obter informações via requisição do tipo GET da URL
	 * informada. Para que seja possível esta conexão deve se ter acesso a WEB.
	 * 
	 * @return Objeto ArrayList<LinhasBean> contenndo informações das linhas da
	 *         malha viárias da cidade de Porto Alegre.
	 * @throws IOException
	 */
	public ArrayList<LinhasBean> getURLLinhasPOA() throws IOException {
		String strRequest;
		String strLinhas;
		String[] linhasPOA;
		String[] linhaPOA;

		// Executa a leitura dos dados da URL
		bufferedReader = new BufferedReader(
				new InputStreamReader(HTTPConnect.getInstance().getConnection(URL).getInputStream()));
		stringBuilder = new StringBuilder();

		// Gera a lista do resultado da requisição
		linhasTransPOA = new ArrayList<LinhasBean>();

		while ((strRequest = bufferedReader.readLine()) != null) {
			stringBuilder.append(strRequest + '\n');
		}

		// Remove caracteres desnecessários
		strLinhas = stringBuilder.toString().trim();
		strLinhas = strLinhas.replace("[{", "");
		strLinhas = strLinhas.replace(",{", "");
		strLinhas = strLinhas.replace("]", "");
		strLinhas = strLinhas.replace("id", "");
		strLinhas = strLinhas.replace("codigo", "");
		strLinhas = strLinhas.replace("nome", "");
		strLinhas = strLinhas.replace(":", "");
		strLinhas = strLinhas.replace("\"", "");

		// Desmembra uma String e a converte em objeto do tipo JSOM
		linhasPOA = strLinhas.split("\\}");

		for (int i = 0; i < linhasPOA.length; i++) {
			linhaPOA = linhasPOA[i].toString().split("\\,");

			// Criação de objeto de mainpulação
			linhaBean = new LinhasBean();

			linhaBean.setIdLinha(Integer.parseInt(linhaPOA[0]));
			linhaBean.setCodigo(linhaPOA[1]);
			linhaBean.setNome(linhaPOA[2]);

			// Atribuição do objeto LinhasBean
			linhasTransPOA.add(linhaBean);
		}

		return linhasTransPOA;
	}

	/**
	 * Método para selecionar uma linha a partir de um nome fornecido pelo usuário.
	 * Caso o parâmetro passado apareça em uma parte do nome també retorna o objeto
	 * linha.
	 * 
	 * @param String nome da linha ou parte do mesmo.
	 * @return ArrayList<LinhasBean> lista com as linhas filtradas por nome e
	 *         associações.
	 */
	public ArrayList<LinhasBean> getLinhaByNome(String nome) {
		ArrayList<LinhasBean> result = new ArrayList<LinhasBean>();

		// Busca pela linha solicitada
		for (int i = 1; i < linhasTransPOA.size(); i++) {
			linhaBean = linhasTransPOA.get(i);

			// Verifica se o nome passado esta contido no nome da linha.
			if (linhaBean.getNome().contains(nome)) {
				result.add(linhasTransPOA.get(i));
			}
		}

		return result;
	}

}
