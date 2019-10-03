package br.com.jpage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import br.com.jpage.beans.LinhasBean;
import br.com.jpage.util.HTTPConnect;

/**
 * Classe de requisi��o de informa��o oriunda de um Site.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* permitir a utiliza��o de dados.
 * 
 *           Esta classe � respons�vel pela obten��o e disponibiliza��o de
 *           informa��es oriundas do site de transportes da cidade de Porto
 *           Alegre.
 * 
 *           Os dados obtidos correspondem a informa��es sobre as linhas de
 *           circula��o de transporte na cidade de Porto Alegre extraidos via
 *           requisi��o do tipo GET no seguinte endere�o:
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
		 * Se o par�metro a for a=nc e o par�metro t for t="" ent�o seta para "O"
		 */
		if (a.equals("nc")) {
			if (t.equals("")) {
				t = "o";
			}
		}

		/**
		 * Seta o endere�o para a requisi��o de lista de linhas do site:
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
		 * Se o par�metro a for a=nc e o par�metro t for t="" ent�o seta para "O"
		 */
		if (a.equals("nc")) {
			if (t.equals("")) {
				t = "o";
			}
		}

		/**
		 * Seta o endere�o para a requisi��o de lista de linhas do site:
		 * http://www.poatransporte.com.br
		 */
		this.URL = "http://www.poatransporte.com.br/php/facades/process.php?a=" + a + "&p=" + p + "&t=" + t;

		// Insancia objetos
		linhasTransPOA = new ArrayList<LinhasBean>();
		linhaBean = new LinhasBean();
	}

	/**
	 * M�todo respons�vel por obter informa��es via requisi��o do tipo GET da URL
	 * informada. Para que seja poss�vel esta conex�o deve se ter acesso a WEB.
	 * 
	 * @return Objeto ArrayList<LinhasBean> contenndo informa��es das linhas da
	 *         malha vi�rias da cidade de Porto Alegre.
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

		// Gera a lista do resultado da requisi��o
		linhasTransPOA = new ArrayList<LinhasBean>();

		while ((strRequest = bufferedReader.readLine()) != null) {
			stringBuilder.append(strRequest + '\n');
		}

		// Remove caracteres desnecess�rios
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

			// Cria��o de objeto de mainpula��o
			linhaBean = new LinhasBean();

			linhaBean.setIdLinha(Integer.parseInt(linhaPOA[0]));
			linhaBean.setCodigo(linhaPOA[1]);
			linhaBean.setNome(linhaPOA[2]);

			// Atribui��o do objeto LinhasBean
			linhasTransPOA.add(linhaBean);
		}

		return linhasTransPOA;
	}

	/**
	 * M�todo para selecionar uma linha a partir de um nome fornecido pelo usu�rio.
	 * Caso o par�metro passado apare�a em uma parte do nome tamb� retorna o objeto
	 * linha.
	 * 
	 * @param String nome da linha ou parte do mesmo.
	 * @return ArrayList<LinhasBean> lista com as linhas filtradas por nome e
	 *         associa��es.
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
