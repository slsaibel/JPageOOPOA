package br.com.jpage.controller;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.jpage.CRUD.DadosDAO;
import br.com.jpage.beans.LinhasBean;

/**
 * Classe de operações com banco de dados.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 29/09/2019
 *
 * @revision 001.20190929 reason* permitir operações com o banco de dados.
 * 
 *           Esta classe é responsável pelas operações de inclusão, alteração,
 *           exclusão e consulta em bancos de dados POSTGRES.
 * 
 * @category CRUD, DB
 * 
 */
public class CRUDLinhasTransPOA implements Serializable {
	private static final long serialVersionUID = 1L;

	LinhasBean linhasBean = new LinhasBean();

	public CRUDLinhasTransPOA() {
	}

	public void addSearch(String campo, String valor, int tipo) {
		linhasBean.adicionarWhere(new DadosDAO(campo, "", valor, tipo, false));
	}

	/**
	 * Executa uma consulta no banco
	 * 
	 * @return String no formato de dado JSOM
	 */
	public String executeSearch() {
		ArrayList<DadosDAO> lista = new ArrayList<DadosDAO>();
		String result = "";

		linhasBean.adicionarCampos();

		lista = linhasBean.getObjetoDAO().consultar();

		// Varre a lista para apresentar os dados em forma de informação.
		for (int i = 0; i < lista.size(); i++) {
			// Inclui um separador de registro
			if (lista.get(i).isChave()) {
				result += "},{";
			}

			if (i > 1) {
				result += ",";
			}

			result += lista.get(i).getDescricao().toString() + ":" + lista.get(i).getValor().toString();
		}

		result = result.substring(2, result.length()) + "}";

		return result;
	}

	/**
	 * Executa ações de CRUD com o banco
	 */
	public void executeCRUD() {
		// Excluindo uma linha
		linhasBean.setIdLinha(1);
		linhasBean.setCodigo("teste1");
		linhasBean.setNome("TESTE DE NOME 1");
		linhasBean.setTipo("o");

		// Adiciona todos os campos (acima definidos) ao objeto
		linhasBean.adicionarCampos();
		// Adiciona ao where todos os campos que são chave no banco.
		linhasBean.adicionarWhere(linhasBean.getCamposChave());
		// Executa comando
		linhasBean.getObjetoDAO().excluir();

		// Incluindo uma linha
		linhasBean.setIdLinha(1);
		linhasBean.setCodigo("teste1");
		linhasBean.setNome("TESTE DE NOME 11");
		linhasBean.setTipo("o");

		linhasBean.adicionarCampos();

		linhasBean.getObjetoDAO().incluir();

		// Incluindo outra linha
		linhasBean.setIdLinha(2);
		linhasBean.setCodigo("teste2");
		linhasBean.setNome("TESTE DE NOME 21");
		linhasBean.setTipo("o");

		linhasBean.adicionarCampos();

		linhasBean.getObjetoDAO().incluir();

		// Incluindo outra linha
		linhasBean.setIdLinha(3);
		linhasBean.setCodigo("teste3");
		linhasBean.setNome("TESTE DE NOME 32");
		linhasBean.setTipo("o");

		linhasBean.adicionarCampos();

		linhasBean.getObjetoDAO().incluir();

		// Alterando uma linha
		linhasBean.setIdLinha(3);
		linhasBean.setCodigo("teste33");
		linhasBean.setNome("TESTE DE NOME 39");
		linhasBean.setTipo("o");

		linhasBean.adicionarCampos();

		linhasBean.getObjetoDAO().alterar();
	}

	/**
	 * Retorna uma lista de linhas contidas no raio informado. Este método utiliza
	 * como base uma latitude e uma longitude para marcar o centro da
	 * circunferência.
	 * 
	 * @param latitude
	 * @param longitude
	 * @param raio      perímetro que se deseja executar a busca por linhas.
	 * @return ArrayList<LinhasBean> lista com as linhas dentro do raio informado.
	 */
	public ArrayList<LinhasBean> getLunhasRaioDB(String latitude, String longitude, int raio) {
		// NÃO IMPLEMENTADO POIS O SITE NÃO ESTAVA MAIS DISPONÍVEL PARA TESTES.
		
		return null;
	}

}
