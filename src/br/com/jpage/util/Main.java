package br.com.jpage.util;

import java.io.IOException;

import br.com.jpage.CRUD.DadosDAO;
import br.com.jpage.controller.CRUDLinhasTransPOA;
import br.com.jpage.controller.HTTPReqItinerarioTransPOA;
import br.com.jpage.controller.HTTPReqLinhasTransPOA;

public class Main {

	public Main() {
	}

	/**
	 * Solicita��es a este servi�o devem ser efetuadas usando uma requisi��o GET,
	 * com os seguintes par�metros:
	 * 
	 * a (obrigat�rio) - Identificador da opera��o. Poss�veis valores: � nc � nome e
	 * c�digo � il - itiner�rios (rotas) � tp - pontos de �nibus � tx - pontos de
	 * taxi
	 * 
	 * p (obrigat�rio) ID relacionado a opera��o descrita em a. Pode ser o ID da
	 * unidade de transporte ou um range latitude x longitude para extra��o dos
	 * pontos de taxi/�nibus
	 * 
	 * t - Tipo da requisi��o. Obrigat�rio apenas se a=nc. Poss�veis valores: � o -
	 * �nibus � l - Lota��o
	 * 
	 */
	public static void main(String[] args) {
		HTTPReqLinhasTransPOA hTTPReqLinhasTransPOA = new HTTPReqLinhasTransPOA("nc", "%", "");
		HTTPReqItinerarioTransPOA hTTPReqItinerarioTransPOA = new HTTPReqItinerarioTransPOA("il", "5566");
		CRUDLinhasTransPOA cRUDLinhasTransPOA = new CRUDLinhasTransPOA();

		try {
			hTTPReqLinhasTransPOA.getURLLinhasPOA();
			hTTPReqLinhasTransPOA.getLinhaByNome("JARDIM");
			hTTPReqItinerarioTransPOA.getURLItinerariosPOA();

			// cRUDLinhasTransPOA.executeCRUD();
			cRUDLinhasTransPOA.addSearch("id_linha", "3", DadosDAO.TIPO_INTEGER);
			System.out.println(cRUDLinhasTransPOA.executeSearch());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
