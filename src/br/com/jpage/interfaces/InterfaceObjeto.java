package br.com.jpage.interfaces;

import java.util.ArrayList;

import br.com.jpage.CRUD.DadosDAO;
import br.com.jpage.CRUD.ObjetoDAO;

/**
 * Interface de objeto.
 *
 * @author Saibel, Sergio Luis <slsaibel@gmail.com>
 * @date 26/09/2019
 *
 * @revision 001.20190926 reason* permitir que vários objetos que extendem esta
 *           interface possam operar funções comuns.
 * 
 * @category TYPE, DAO, DB, CRUD, INTERFACE
 * 
 */
public interface InterfaceObjeto {
	public abstract ObjetoDAO getObjetoDAO();

	public abstract void adicionarCampos();

	public void adicionarCampos(ArrayList<DadosDAO> lstDadosDAO);

	public void removerCampos();

	public void adicionarWhere(DadosDAO dadoDAO);

	public void adicionarWhere(ArrayList<DadosDAO> lstDadosDAO);

	public void removerWhere();

	public ArrayList<DadosDAO> getCamposChave();

	public ArrayList<DadosDAO> getCampos();

	public abstract DadosDAO getDadosCodigo();

	public abstract DadosDAO getDadosDescricao();
	
	public abstract InterfaceObjeto getObjeto();
}
