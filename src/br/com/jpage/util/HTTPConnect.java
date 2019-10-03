package br.com.jpage.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe de conexão e requisição de informação oriunda de um Site.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* permitir a utilização de dados.
 * 
 *           Esta classe é responsável pela conexão, obtenção e disponibilização
 *           de informações oriundas do site de um site.
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
	 * Método responsável por obter uma conexão com a URL informada.
	 * 
	 * @return Objeto HttpURLConnection configurada para acessar informações de uma
	 *         URL por meio de requisição do tipo GET.
	 * @throws IOException
	 */
	public HttpURLConnection getConnection(String strURL) throws IOException {
		serverAddress = new URL(strURL);

		// Configurando a conexão com a URL
		httpURLConnection = null;

		// Estabelecendo a conexão com o site
		httpURLConnection = (HttpURLConnection) serverAddress.openConnection();

		// Setando o tipo de requisição para o servidor
		httpURLConnection.setRequestMethod("GET");
		// Habilita a solicitação de informações para o site
		httpURLConnection.setDoOutput(true);

		// Aguarda 5 segundos para conectar e 10 para obret o resultado
		httpURLConnection.setConnectTimeout(5000);
		httpURLConnection.setReadTimeout(10000);

		// Efetua a conexão
		httpURLConnection.connect();

		return httpURLConnection;
	}

	/**
	 * Retorna instância de conexão com uma URL
	 * 
	 * @param URL endereço de requisição de URL
	 * @return Retorna HTTPConnect uma insrtância de conexão com a URL.
	 */
	public static HTTPConnect getInstance() {
		if (instancia == null) {
			instancia = new HTTPConnect();
		}
		return instancia;
	}

}
