package br.com.jpage.CRUD;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.jpage.interfaces.InterfaceDAO;
import br.com.jpage.util.ConexoesDB;

/**
 * Classe de opera��es com o banco de dados.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 28/09/2019
 *
 * @revision 001.20190929 reason* Executar procedimentos de CRUD no banco.
 * 
 * @category DB, CRUD
 * 
 */
public class ObjetoDAO implements Serializable, InterfaceDAO {
	private static final long serialVersionUID = 1L;

	private String tabela;
	private ArrayList<DadosDAO> camposTabela;
	private ArrayList<DadosDAO> condicaoWhere;

	public ObjetoDAO(String tabela) {
		this.tabela = tabela;

		camposTabela = new ArrayList<DadosDAO>();
		condicaoWhere = new ArrayList<DadosDAO>();
	}

	/**
	 * Efetua os procedimentos de inclus�o em banco
	 * 
	 * @return String status da a��o com o banco.
	 */
	@Override
	public String incluir() {
		String result = null;
		String sql = "";
		String campos = "";
		String valores = "";
		Statement statement;
		DadosDAO dadoDAO;

		try {
			statement = ConexoesDB.getInstance().getConnection().createStatement();

			for (int i = 0; i < camposTabela.size(); i++) {
				dadoDAO = new DadosDAO(camposTabela.get(i));

				campos += dadoDAO.getCampo();

				if ((dadoDAO.getTipo() == DadosDAO.TIPO_INTEGER) || (dadoDAO.getTipo() == DadosDAO.TIPO_DOUBLE
						|| (dadoDAO.getTipo() == DadosDAO.TIPO_BOOLEAN))) {
					if (dadoDAO.getValor().equals("-1")) {
						valores += null;
					} else {
						valores += dadoDAO.getValor();
					}
				} else {
					if ((dadoDAO.getTipo() == DadosDAO.TIPO_STRING) || (dadoDAO.getTipo() == DadosDAO.TIPO_DATE)) {
						if (dadoDAO.getValor() == null) {
							valores += dadoDAO.getValor();
						} else {
							valores += "'" + dadoDAO.getValor() + "'";
						}
					}
				}

				if (i < (camposTabela.size() - 1)) {
					campos += ",";
					valores += ",";
				}
			}

			sql = " INSERT INTO " + tabela + " (" + campos + ")" + " VALUES (" + valores + ");";

			statement.executeUpdate(sql);
		} catch (SQLException SQLe) {
			result = "Erro salvar " + tabela + ": " + SQLe;
			JOptionPane.showMessageDialog(null, result, "INCLU�NDO", JOptionPane.ERROR_MESSAGE, null);
		}

		return result;
	}

	/**
	 * Efetua os procedimentos de altera��o do banco
	 * 
	 * @return String status da a��o com o banco.
	 */
	@Override
	public String alterar() {
		String result = null;
		Statement statement;
		String sql = "";
		DadosDAO dadoDAO;

		try {
			statement = ConexoesDB.getInstance().getConnection().createStatement();

			sql += " UPDATE " + tabela + " SET ";
			for (int i = 0; i < camposTabela.size(); i++) {
				dadoDAO = new DadosDAO(camposTabela.get(i));

				sql += dadoDAO.getCampo() + "=";

				if ((dadoDAO.getTipo() == DadosDAO.TIPO_INTEGER) || (dadoDAO.getTipo() == DadosDAO.TIPO_DOUBLE)
						|| (dadoDAO.getTipo() == DadosDAO.TIPO_BOOLEAN)) {
					if (dadoDAO.getValor().equals("-1")) {
						sql += null;
					} else {
						sql += dadoDAO.getValor();
					}
				} else {
					if ((dadoDAO.getTipo() == DadosDAO.TIPO_STRING) || (dadoDAO.getTipo() == DadosDAO.TIPO_DATE)) {
						if (dadoDAO.getValor() == null) {
							sql += dadoDAO.getValor();
						} else {
							sql += "'" + dadoDAO.getValor() + "'";
						}
					}
				}

				if (i < (camposTabela.size() - 1)) {
					sql += ",";
				}
			}

			sql += addWere(true) + ";";

			statement.executeUpdate(sql);
		} catch (SQLException SQLe) {
			result = "Erro alterar " + tabela + ": " + SQLe;
			JOptionPane.showMessageDialog(null, result, "ALTERANDO", JOptionPane.ERROR_MESSAGE, null);
		}

		return result;
	}

	/**
	 * Adicionar um objeto a um vetor respons�vel pela condi��o WHERE no banco.
	 * 
	 * Verifica os par�metros CAMPO e VALOR na entrada para evitar SQL Injection.
	 * Caso possua algum espa�o na descri��o do valor poder� se tratar de um ataque,
	 * ou tentativa de burla. O m�todo trata este erro substituindo os espa�os em
	 * branco por espa�os vazios.
	 *
	 * @param dado Objeto que contenha: o nome do campo, descri��o, tipo e valor.
	 */
	@Override
	public void addWhere(DadosDAO dado) {
		// Se os tamanhos n�o baterem ent�o mostra uma mensagem de erro
		if ((dado.getCampo().length() != dado.getCampo().replaceAll("\\s+", "").length())
				|| (dado.getValor().length() != dado.getValor().replaceAll("\\s+", "").length())) {
			// Verifica o tamanho do CAMPO e VALOR passados por par�metro
			dado.setCampo(dado.getCampo().replaceAll("\\s+", ""));
			dado.setValor(dado.getValor().replaceAll("\\s+", ""));

			JOptionPane.showMessageDialog(null, "Verifique a escrita dos par�metro CAMPO e VALOR.", "ERROR",
					JOptionPane.ERROR_MESSAGE, null);
		}

		condicaoWhere.add(dado);
	}

	/**
	 * Cria��o de condi��o para buscas no banco de dados.
	 * 
	 * @param isCampoCompleto refere-se a informa��o que est� sendo solicitada ao
	 *                        banco. Se este par�metro for true significa que a
	 *                        condi��o deve conter todo o conte�do do par�metro.
	 * @return String contendo a condi��o de busca.
	 */
	@Override
	public String addWere(boolean isCampoCompleto) {
		String result = "";
		DadosDAO dadoDAO;

		// Se ja tiver condi��o ent�o deve incluir a condi��o ao final.
		if (condicaoWhere.size() > 0) {
			result += " WHERE ";

			// Verifica as condi��es de pesquisa
			for (int i = 0; i < condicaoWhere.size(); i++) {
				dadoDAO = new DadosDAO(condicaoWhere.get(i));

				result += dadoDAO.getCampo();

				if (dadoDAO.getTipo() == DadosDAO.TIPO_INTEGER) {
					result += " = " + dadoDAO.getValor();
				} else {
					if (dadoDAO.getTipo() == DadosDAO.TIPO_STRING) {
						if (isCampoCompleto) {
							result += " = '" + dadoDAO.getValor() + "'";
						} else {
							result += " iLIKE '%" + dadoDAO.getValor() + "%'";
						}
					}
				}

				if (i < (condicaoWhere.size() - 1)) {
					result += " AND ";
				}
			}
		}

		return result;
	}

	/**
	 * Efetua os procedimentos de exclus�o do banco
	 * 
	 * @return String status da a��o com o banco.
	 */
	@Override
	public String excluir() {
		String result = null;
		Statement statement;
		String sql = "";

		try {
			statement = ConexoesDB.getInstance().getConnection().createStatement();

			sql += " DELETE FROM " + tabela + addWere(true) + ";";

			statement.executeUpdate(sql);
		} catch (SQLException SQLe) {
			result = "Erro excluir " + tabela + ": " + SQLe;
			JOptionPane.showMessageDialog(null, result, "EXCLU�NDO", JOptionPane.ERROR_MESSAGE, null);
		}

		return result;
	}

	@Override
	public ArrayList<DadosDAO> consultar() {
		ArrayList<DadosDAO> result = new ArrayList<DadosDAO>();
		ResultSet resultSet;
		DadosDAO dadoDAO;
		String sql = "";
		String valor = "";

		try {
			sql = "SELECT * FROM " + tabela + addWere(false) + " ORDER BY " + camposTabela.get(0).getCampo() + ";";

			resultSet = ConexoesDB.getInstance().getConnection().createStatement().executeQuery(sql);

			// Imprime o resultado da pesquisa
			while (resultSet.next()) {
				for (int i = 0; i < camposTabela.size(); i++) {
					dadoDAO = new DadosDAO(camposTabela.get(i));

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
			JOptionPane.showMessageDialog(null, "Erro consultar " + tabela + ": " + SQLe, "BUSCANDO",
					JOptionPane.ERROR_MESSAGE, null);
		}

		return result;
	}

	/**
	 * Busca o pr�ximo indice vazio no banco caso n�o seja poss�vel implementar o
	 * auto incremento.
	 * 
	 * @param campoID identificador da tabela - �ndice.
	 * @return int pr�ximo �ndice livre. Se retornar -1 ent�o ocorreu algum erro.
	 */
	@Override
	public int getProximoID(String campoID) {
		int result = -1;
		ResultSet resultSet;
		Statement statement;
		String sql;

		try {
			statement = ConexoesDB.getInstance().getConnection().createStatement();

			sql = "SELECT MAX(" + campoID + ") FROM " + tabela;

			resultSet = statement.executeQuery(sql);
			resultSet.next();
			result = resultSet.getInt(1) + 1;
		} catch (SQLException SQLe) {
			System.out.println(SQLe);
		}

		return result;
	}

	/**
	 * Adiciona um objeto a uma lista
	 * 
	 * @param dado
	 */
	public void addCampo(DadosDAO dado) {
		camposTabela.add(dado);
	}

	/**
	 * @return the tabela
	 */
	public String getTabela() {
		return tabela;
	}

	/**
	 * @param tabela the tabela to set
	 */
	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	/**
	 * @return the camposTabela
	 */
	public ArrayList<DadosDAO> getCamposTabela() {
		return camposTabela;
	}

	/**
	 * @param camposTabela the camposTabela to set
	 */
	public void setCamposTabela(ArrayList<DadosDAO> camposTabela) {
		this.camposTabela = camposTabela;
	}

	/**
	 * @return the condicaoWhere
	 */
	public ArrayList<DadosDAO> getCondicaoWhere() {
		return condicaoWhere;
	}

	/**
	 * @param condicaoWhere the condicaoWhere to set
	 */
	public void setCondicaoWhere(ArrayList<DadosDAO> condicaoWhere) {
		this.condicaoWhere = condicaoWhere;
	}

}
