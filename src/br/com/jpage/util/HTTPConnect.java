package br.com.jpage.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe de conex�o e requisi��o de informa��o oriunda de um Site.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* permitir a utiliza��o de dados.
 * 
 *           Esta classe � respons�vel pela conex�o, obten��o e disponibiliza��o
 *           de informa��es oriundas do site de um site.
 * 
 * @category HTTP, CONNECTION, REQUEST
 * 
 */
public class HTTPConnect implements Serializable {
	private static final long serialVersionUID = 1L;

	private HttpURLConnection httpURLConnection = null;
	private static HTTPConnect instancia = null;

	URL serverAddress = null;

	// Constructor
	public HTTPConnect() {
	}

	/**
	 * M�todo respons�vel por obter uma conex�o com a URL informada.
	 * 
	 * @return Objeto HttpURLConnection configurada para acessar informa��es de uma
	 *         URL por meio de requisi��o do tipo GET.
	 * @throws IOException
	 */
	public HttpURLConnection getConnection(String strURL) throws IOException {
		serverAddress = new URL(strURL);

		// Configurando a conex�o com a URL
		httpURLConnection = null;

		// Estabelecendo a conex�o com o site
		httpURLConnection = (HttpURLConnection) serverAddress.openConnection();

		// Setando o tipo de requisi��o para o servidor
		httpURLConnection.setRequestMethod("GET");
		// Habilita a solicita��o de informa��es para o site
		httpURLConnection.setDoOutput(true);

		// Aguarda 5 segundos para conectar e 10 para obret o resultado
		httpURLConnection.setConnectTimeout(5000);
		httpURLConnection.setReadTimeout(10000);

		// Efetua a conex�o
		httpURLConnection.connect();

		return httpURLConnection;
	}

	/**
	 * Retorna inst�ncia de conex�o com uma URL
	 * 
	 * @param URL endere�o de requisi��o de URL
	 * @return Retorna HTTPConnect uma insrt�ncia de conex�o com a URL.
	 */
	public static HTTPConnect getInstance() {
		if (instancia == null) {
			instancia = new HTTPConnect();
		}
		return instancia;
	}

}
