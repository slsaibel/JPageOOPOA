package br.com.jpage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe de Conexão com o banco de dados.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 28/09/2019
 *
 * @revision 001.20190928 reason* Executar procedimentos de conexão e acesso ao
 *           banco.
 * 
 * @category DB
 * 
 */
public class ConexoesDB implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nomeBanco;
	private String driverDB;
	private String jdbcDB;
	private String nomeServidor;
	private String numPorta;
	private String usuario;
	private String senha;

	private Properties properties;

	private static ConexoesDB instancia = null;
	private Connection conexao = null;

	// Construtor
	public ConexoesDB() {
		properties = new Properties();

		conectarDB();
	}

	/**
	 * Carrega as propriedade do banco a partir de um arquivo de propriedades.
	 * 
	 * @return String caso não consiga encontrar/carregar o arquivo
	 */
	public String carregarPropriedades() {
		String result = "";

		try {
			File file = new File("banco.properties");
			try (FileInputStream fis = new FileInputStream(file)) {
				properties.load(fis);
			}
		} catch (IOException ex) {
			result = "Não foi possível carregar as propriedades do banco.\n" + "Motivo: " + ex.getMessage();
		}

		nomeBanco = properties.getProperty("banco.nomeBanco");
		driverDB = properties.getProperty("banco.driverDB");
		jdbcDB = properties.getProperty("banco.jdbcDB");
		nomeServidor = properties.getProperty("banco.nomeServidor");
		numPorta = properties.getProperty("banco.numPorta");
		usuario = properties.getProperty("banco.usuario");
		senha = properties.getProperty("banco.senha");

		return result;
	}

	/**
	 * Salva as propriedade do banco em um arquivo de propriedades.
	 * 
	 * @return String caso não consiga encontrar/carregar o arquivo
	 */
	public String descarregarPropriedades() {
		String retorno = "";

		properties.setProperty("banco.nomeBanco", nomeBanco);
		properties.setProperty("banco.driverDB", driverDB);
		properties.setProperty("banco.jdbcDB", jdbcDB);
		properties.setProperty("banco.nomeServidor", nomeServidor);
		properties.setProperty("banco.numPorta", numPorta);
		properties.setProperty("banco.usuario", usuario);
		properties.setProperty("banco.senha", senha);

		try {
			File file = new File("banco.properties");
			try (FileOutputStream fos = new FileOutputStream(file)) {
				properties.store(fos, "Configurações para banco de dados");
			}
		} catch (IOException ex) {
			retorno = "Não foi possível descarregar as propriedades do banco.\n" + "Motivo: " + ex.getMessage();
		}

		return retorno;
	}

	/**
	 * Efetua a conexão com o banco.
	 * 
	 * @return String caso não consiga conectar o banco de dados com as definições
	 *         do arquivo de propriedades.
	 */
	public String conectarDB() {
		String retorno = "";
		try {
			// Carrega as propriedades do banco
			carregarPropriedades();

			// Carrega Driver do Banco de Dados
			Class.forName(driverDB);

			if (usuario.length() != 0) {
				// conexão COM usuário e senha
				conexao = DriverManager.getConnection(
						"jdbc:" + jdbcDB + "://" + nomeServidor + ":" + numPorta + "/" + nomeBanco, usuario, senha);
			} else {
				// conexão SEM usuário e senha
				conexao = DriverManager.getConnection(jdbcDB + "//" + nomeServidor + ":" + numPorta + "/" + nomeBanco);
			}
		} catch (ClassNotFoundException | SQLException ex) {
			retorno = "Não foi possível conectar ao banco.\nMotivo: " + ex;
		}

		return retorno;
	}

	/**
	 * Instância um objeto de conexão com o bamco.
	 * 
	 * @return ConexoesDB caso não exista uma conexão então tenta criar uma nova
	 *         intância.
	 */
	public static ConexoesDB getInstance() {
		if (instancia == null) {
			instancia = new ConexoesDB();
		}
		return instancia;
	}

	/**
	 * Retorna conexão com o banco.
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		if (conexao == null) {
			throw new RuntimeException("conexao==null");
		}
		return conexao;
	}

	/**
	 * Encerra uma conexão com o banco
	 */
	public void shutDown() {
		try {
			conexao.close();
			instancia = null;
			conexao = null;
		} catch (SQLException eSQL) {
			System.err.println(eSQL);
		}
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
