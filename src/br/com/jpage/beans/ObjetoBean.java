package br.com.jpage.beans;

import java.util.ArrayList;

import br.com.jpage.CRUD.DadosDAO;
import br.com.jpage.CRUD.ObjetoDAO;
import br.com.jpage.interfaces.InterfaceObjeto;

public abstract class ObjetoBean implements InterfaceObjeto {
	@Override
	public abstract ObjetoDAO getObjetoDAO();

	@Override
	public abstract void adicionarCampos();

	@Override
	public void adicionarCampos(ArrayList<DadosDAO> lstDadosDAO) {
		for (DadosDAO dadoDAO : lstDadosDAO) {
			getObjetoDAO().addCampo(dadoDAO);
		}
	}

	@Override
	public void removerCampos() {
		getObjetoDAO().getCamposTabela().clear();
	}

	@Override
	public ArrayList<DadosDAO> getCamposChave() {
		ArrayList<DadosDAO> result = new ArrayList<DadosDAO>();

		for (DadosDAO dadoDAO : getObjetoDAO().getCamposTabela()) {
			if (dadoDAO.isChave()) {
				result.add(dadoDAO);
			}
		}

		return result;
	}

	@Override
	public ArrayList<DadosDAO> getCampos() {
		ArrayList<DadosDAO> result = new ArrayList<>();

		for (DadosDAO dadoDAO : getObjetoDAO().getCamposTabela()) {
			result.add(dadoDAO);
		}

		return result;
	}

	@Override
	public void adicionarWhere(DadosDAO dadoDAO) {
		getObjetoDAO().addWhere(dadoDAO);
	}

	@Override
	public void adicionarWhere(ArrayList<DadosDAO> lstDadosDAO) {
		for (DadosDAO dadoDAO : lstDadosDAO) {
			getObjetoDAO().addWhere(dadoDAO);
		}
	}

	@Override
	public void removerWhere() {
		getObjetoDAO().getCondicaoWhere().clear();
	}

	/**
	 * @return the dadosCodigo
	 */
	@Override
	public abstract DadosDAO getDadosCodigo();

	/**
	 * @return the dadosDescricao
	 */
	@Override
	public abstract DadosDAO getDadosDescricao();
	
	/**
	 * @return the objeto
	 */
	@Override
	public abstract InterfaceObjeto getObjeto();

}
