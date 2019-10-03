package br.com.jpage.CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.jpage.util.ConexoesDB;

/**
 * Classe de operações com o banco de dados.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 29/09/2019
 *
 * @revision 001.20190929 reason* Executar procedimentos de CRUD específicos
 *           para o objero de linhas.
 * 
 * @category DB, CRUD
 * 
 */
public class LinhasDAO extends ObjetoDAO {
	private static final long serialVersionUID = 1L;

	// Construtor
	public LinhasDAO() {
		super("linhas");
	}

	/**
	 * Retorna uma lista de linhas contidas no raio informado. Este método utiliza
	 * como base uma latitude e uma longitude para marcar o centro da
	 * circunferência.
	 * 
	 * @see https://pt.stackoverflow.com/questions/55669/identificar-se-conjunto-de-coordenadas-est%C3%A1-dentro-de-um-raio-em-android
	 * @param latitude
	 * @param longitude
	 * @param raio      perímetro que se deseja executar a busca por linhas.
	 * @return ArrayList<DadosDAO> lista com as descrições das linhas dentro do raio
	 *         informado.
	 */
	public ArrayList<DadosDAO> getLunhasRaioDB(String latitude, String longitude, int raio) {
		ArrayList<DadosDAO> result = new ArrayList<DadosDAO>();
		ResultSet resultSet;
		DadosDAO dadoDAO;
		String sql = "";
		String valor = "";
		// raio terestre é de aproximadamente 6371Km.
		int raioTerrestre = 6371;

		try {
			sql = "SELECT *,(" + String.valueOf(raioTerrestre) + " * acos(cos(radians(" + latitude
					+ ")) * cos(radians(coordenadas.latitude)) * cos(radians(" + longitude
					+ ") - radians(coordenadas.longitude)) + sin(radians(" + latitude
					+ ")) * sin(radians(coordenadas.latitude)))) AS CAMPOLATITUDEF FROM linhas "
					+ "INNER JOIN itinerarios ON(id_lina=cod_linha) "
					+ "INNER JOIN coordenadas ON (id_coordenada=cod_coordenada) HAVING lcoordenadas.latitude<=10;";

			resultSet = ConexoesDB.getInstance().getConnection().createStatement().executeQuery(sql);

			// Imprime o resultado da pesquisa
			while (resultSet.next()) {
				for (int i = 0; i < this.getCamposTabela().size(); i++) {
					dadoDAO = new DadosDAO(this.getCamposTabela().get(i));

					// Verifica o tipo de retorno do banco
					if (dadoDAO.getTipo() == DadosDAO.TIPO_INTEGER) {
						valor = String.valueOf(resultSet.getInt(dadoDAO.getCampo()));
					} else {
						if (dadoDAO.getTipo() == DadosDAO.TIPO_STRING) {
							valor = resultSet.getString(dadoDAO.getCampo());
						}
					}

					dadoDAO.setValor(valor);

					result.add(dadoDAO);
				}
			}

		} catch (SQLException SQLe) {
			JOptionPane.showMessageDialog(null, "Erro consultar LINHAS em um raio: " + SQLe, "BUSCANDO",
					JOptionPane.ERROR_MESSAGE, null);
		}

		return result;
	}
}
