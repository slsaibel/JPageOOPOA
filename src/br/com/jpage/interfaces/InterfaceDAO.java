package br.com.jpage.interfaces;

import java.util.ArrayList;

import br.com.jpage.CRUD.DadosDAO;

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
public interface InterfaceDAO {

	public String incluir();

	public String alterar();

	public String excluir();

	public ArrayList<DadosDAO> consultar();

	public void addWhere(DadosDAO dado);

	public String addWere(boolean isCampoCompleto);

	public int getProximoID(String campoID);
}
